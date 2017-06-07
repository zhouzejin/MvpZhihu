package com.sunny.mvpzhihu.ui.main.theme;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.DataManager;
import com.sunny.mvpzhihu.data.model.bean.Theme;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.ui.base.BaseFragment;
import com.sunny.mvpzhihu.ui.themedetail.ThemeDetailActivity;
import com.sunny.mvpzhihu.utils.LogUtil;
import com.sunny.mvpzhihu.widget.CircleProgressView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * The type Theme fragment.
 * Created by Zhou Zejin on 2017/6/5.
 */
public class ThemeFragment extends BaseFragment {

    @BindView(R.id.circle_progress_theme)
    CircleProgressView mCircleProgressTheme;
    @BindView(R.id.recycler_theme)
    RecyclerView mRecyclerTheme;

    @Inject
    @FragmentContext
    Context mContext;
    @Inject
    ThemeAdapter mThemeAdapter;
    @Inject
    DataManager mDataManager;

    public ThemeFragment() {
    }

    public static ThemeFragment newInstance() {
        return new ThemeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_theme;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mRecyclerTheme.setHasFixedSize(true);
        mRecyclerTheme.setLayoutManager(new LinearLayoutManager(mContext));
        mThemeAdapter.setItemListener(new ThemeAdapter.ThemeItemListener() {
            @Override
            public void onThemeClick(Theme theme) {
                startActivity(ThemeDetailActivity.getStartIntent(mContext, theme));
            }
        });
        mRecyclerTheme.setAdapter(mThemeAdapter);
        loadThemes();
    }

    private void loadThemes() {
        mDataManager.getThemes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new Subscriber<List<Theme>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "加载Theme数据出错。");
                        hideProgress();
                    }

                    @Override
                    public void onNext(List<Theme> themes) {
                        hideProgress();
                        showThemes(themes);
                    }
                });
    }

    public void showProgress() {
        mCircleProgressTheme.setVisibility(View.VISIBLE);
        mCircleProgressTheme.spin();
        mRecyclerTheme.setVisibility(View.GONE);
    }

    public void hideProgress() {
        mCircleProgressTheme.setVisibility(View.GONE);
        mCircleProgressTheme.stopSpinning();
        mRecyclerTheme.setVisibility(View.VISIBLE);
    }

    public void showThemes(List<Theme> themes) {
        mThemeAdapter.addData(themes);
        mThemeAdapter.notifyDataSetChanged();
    }

}
