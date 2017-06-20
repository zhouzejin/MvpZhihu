package com.sunny.mvpzhihu.ui.main.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.bean.HotNews;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.ui.base.BaseFragment;
import com.sunny.mvpzhihu.ui.dailydetail.DailyDetailActivity;
import com.sunny.mvpzhihu.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * The type News fragment.
 * Created by Zhou Zejin on 2017/6/16.
 */
public class NewsFragment extends BaseFragment {

    @BindView(R.id.recycler_news)
    RecyclerView mRecyclerNews;
    @BindView(R.id.swipe_refresh_news)
    SwipeRefreshLayout mSwipeRefreshNews;

    @Inject
    @FragmentContext
    Context mContext;
    @Inject
    DataManager mDataManager;
    @Inject
    NewsAdapter mNewsAdapter;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
        initRecyclerView();
        loadNews();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshNews.setColorSchemeResources(R.color.primary);
        mSwipeRefreshNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerNews.setHasFixedSize(true);
        mRecyclerNews.setLayoutManager(new LinearLayoutManager(mContext));
        mNewsAdapter.setNewsItemListener(new NewsAdapter.NewsItemListener() {
            @Override
            public void onNewsClick(HotNews hotNews) {
                mContext.startActivity(DailyDetailActivity.getStartIntent(mContext, hotNews.news_id()));
            }
        });
        mRecyclerNews.setAdapter(mNewsAdapter);
    }

    private void loadNews() {
        mDataManager.getHotNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showRefresh();
                    }
                })
                .subscribe(new Subscriber<List<HotNews>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载HotNews数据出错。");
                        hideRefresh();
                    }

                    @Override
                    public void onNext(List<HotNews> hotNewses) {
                        hideRefresh();
                        showSections(hotNewses);
                    }
                });
    }

    public void showRefresh() {
        mSwipeRefreshNews.setRefreshing(true);
    }

    public void hideRefresh() {
        mSwipeRefreshNews.setRefreshing(false);
    }

    public void showSections(List<HotNews> hotNewses) {
        mNewsAdapter.clearData();
        mNewsAdapter.addData(hotNewses);
        mNewsAdapter.notifyDataSetChanged();
    }

}
