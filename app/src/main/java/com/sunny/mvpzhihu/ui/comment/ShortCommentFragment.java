package com.sunny.mvpzhihu.ui.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.bean.Comment;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.ui.base.BaseFragment;
import com.sunny.mvpzhihu.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The type Short comment fragment.
 * Created by Zhou Zejin on 2017/6/2.
 */
public class ShortCommentFragment extends BaseFragment {

    private static final String EXTRA_COMMENT_ID = "comment_id";

    @BindView(R.id.recycler_comment)
    RecyclerView mRecyclerComment;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;

    @Inject
    @FragmentContext
    Context mContext;
    @Inject
    DataManager mDataManager;
    @Inject
    CommentAdapter mCommentAdapter;

    public ShortCommentFragment() {
        // Requires empty public constructor
    }

    public static ShortCommentFragment newInstance(int commentId) {
        ShortCommentFragment fragment = new ShortCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_COMMENT_ID, commentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject instance for fragment
        fragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mRecyclerComment.setHasFixedSize(true);
        mRecyclerComment.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerComment.setAdapter(mCommentAdapter);

        int commentId = getArguments().getInt(EXTRA_COMMENT_ID);
        loadShortComment(commentId);
    }

    private void loadShortComment(int commentId) {
        mDataManager.getShortComments(commentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载ShortComment数据出错。");
                        showShortCommentEmpty();
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        if (comments == null || comments.isEmpty()) {
                            showShortCommentEmpty();
                            return;
                        }
                        showShortComment(comments);
                    }
                });
    }

    private void showShortCommentEmpty() {
        mRecyclerComment.setVisibility(View.GONE);
        mTvEmpty.setVisibility(View.VISIBLE);
        mCommentAdapter.clearData();
        mCommentAdapter.notifyDataSetChanged();
    }

    private void showShortComment(List<Comment> comments) {
        mRecyclerComment.setVisibility(View.VISIBLE);
        mTvEmpty.setVisibility(View.GONE);
        mCommentAdapter.addData(comments);
        mCommentAdapter.notifyDataSetChanged();
    }

}
