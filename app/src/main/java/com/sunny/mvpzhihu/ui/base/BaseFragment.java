package com.sunny.mvpzhihu.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunny.mvpzhihu.injection.component.FragmentComponent;
import com.sunny.mvpzhihu.injection.module.ActivityModule;
import com.sunny.mvpzhihu.injection.module.FragmentModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Abstract fragment that every other Fragment in this application must implement.
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;
    private Unbinder mUnbinder;
    private FragmentComponent mFragmentComponent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentComponent = ((BaseActivity) getActivity()).configPersistentComponent()
                .fragmentComponent(new ActivityModule(getActivity()), new FragmentModule(this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 获取布局文件
     *
     * @return 布局文件ID
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);

    public FragmentComponent fragmentComponent() {
        return mFragmentComponent;
    }

}
