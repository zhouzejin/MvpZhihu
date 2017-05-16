package com.sunny.mvpzhihu.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * The type Auto load on scroll listener.
 * Created by Zhou Zejin on 2017/4/27.
 */
public abstract class AutoLoadOnScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;

    private boolean mIsLoading = false;
    private int mCurrentPage = 1;

    public AutoLoadOnScrollListener(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = mLinearLayoutManager.getItemCount();
        int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

        if (!mIsLoading && (lastVisibleItem > totalItemCount - 3) && dy > 0) {
            mIsLoading = true;
            mCurrentPage++;
            onLoadMore(mCurrentPage);
        }
    }

    public abstract void onLoadMore(int currentPage);

    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
    }

}
