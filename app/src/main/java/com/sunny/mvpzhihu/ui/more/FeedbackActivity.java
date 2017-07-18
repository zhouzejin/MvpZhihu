package com.sunny.mvpzhihu.ui.more;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

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
    }

}
