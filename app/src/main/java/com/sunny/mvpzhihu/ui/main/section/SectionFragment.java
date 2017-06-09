package com.sunny.mvpzhihu.ui.main.section;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.bean.Section;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.ui.base.BaseFragment;
import com.sunny.mvpzhihu.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * The type Section fragment.
 * Created by Zhou Zejin on 2017/6/9.
 */
public class SectionFragment extends BaseFragment {

    @BindView(R.id.swipe_refresh_section)
    SwipeRefreshLayout mSwipeRefreshSection;
    @BindView(R.id.recycler_section)
    RecyclerView mRecyclerSection;

    @Inject
    @FragmentContext
    Context mContext;
    @Inject
    DataManager mDataManager;
    @Inject
    SectionAdapter mSectionAdapter;

    public SectionFragment() {
    }

    public static SectionFragment newInstance() {
        return new SectionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_section;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
        initRecyclerView();
        loadSections();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshSection.setColorSchemeResources(R.color.primary);
        mSwipeRefreshSection.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadSections();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerSection.setHasFixedSize(true);
        mRecyclerSection.setLayoutManager(new LinearLayoutManager(mContext));
        mSectionAdapter.setItemListener(new SectionAdapter.SectionItemListener() {
            @Override
            public void onSectionClick(Section section) {
                Toast.makeText(mContext, section.description(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerSection.setAdapter(mSectionAdapter);
    }

    private void loadSections() {
        mDataManager.getSections()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showRefresh();
                    }
                })
                .subscribe(new Subscriber<List<Section>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载Section数据出错。");
                        hideRefresh();
                    }

                    @Override
                    public void onNext(List<Section> sections) {
                        hideRefresh();
                        showSections(sections);
                    }
                });
    }

    public void showRefresh() {
        mSwipeRefreshSection.setRefreshing(true);
    }

    public void hideRefresh() {
        mSwipeRefreshSection.setRefreshing(false);
    }

    public void showSections(List<Section> sections) {
        mSectionAdapter.clearData();
        mSectionAdapter.addData(sections);
        mSectionAdapter.notifyDataSetChanged();
    }

}
