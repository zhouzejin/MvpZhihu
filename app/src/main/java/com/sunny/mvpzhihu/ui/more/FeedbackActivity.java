package com.sunny.mvpzhihu.ui.more;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sunny.mvpzhihu.BuildConfig;
import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * The type My Info activity.
 * Created by Zhou Zejin on 2017/7/17.
 */
public class FeedbackActivity extends BaseActivity {

    private final static int FEEDBACK_LENGTH = 160;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_feedback)
    EditText mEdtFeedback;
    @BindView(R.id.tv_tip)
    TextView mTvTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化Bmob
        Bmob.initialize(this, BuildConfig.BMOB_APP_ID);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle(getString(R.string.feedback));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setSwipeBackEnable(true);

        mEdtFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvTip.setText(String.valueOf(FEEDBACK_LENGTH - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String feedback = mEdtFeedback.getText().toString().trim();
        if (TextUtils.isEmpty(feedback)) {
            Toast.makeText(this, R.string.content_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        sendFeedback(feedback);
    }

    private void sendFeedback(String feedback) {
        FeedbackModel feedbackModel = new FeedbackModel();
        feedbackModel.setContent(feedback);
        feedbackModel.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Activity context = FeedbackActivity.this;
                if (e == null) {
                    Toast.makeText(context, R.string.submit_success, Toast.LENGTH_SHORT).show();
                    context.finish();
                } else {
                    Toast.makeText(context, R.string.submit_fail, Toast.LENGTH_SHORT).show();
                    LogUtil.e(e, "反馈意见提交失败。", s);
                }
            }
        });
    }

}
