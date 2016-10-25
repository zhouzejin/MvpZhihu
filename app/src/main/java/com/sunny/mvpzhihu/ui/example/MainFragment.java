package com.sunny.mvpzhihu.ui.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.injection.module.ActivityModule;
import com.sunny.mvpzhihu.injection.module.FragmentModule;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.utils.factory.DialogFactory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment implements MainMvpView {

    private Unbinder unbinder;

    @Inject
    MainPresenter mMainPresenter;
    @Inject
    SubjectsAdapter mSubjectsAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject instance for fragment
        ((BaseActivity)getActivity()).configPersistentComponent()
                .mainComponent(new ActivityModule(getActivity()), new FragmentModule(this))
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecyclerView.setAdapter(mSubjectsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMainPresenter.attachView(this);
        mMainPresenter.loadSubjects();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mMainPresenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showSubjects(List<Subject> subjects) {
        mSubjectsAdapter.setSubjects(subjects);
        mSubjectsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(getContext(), getString(R.string.error_loading_subjects))
                .show();
    }

    @Override
    public void showSubjectsEmpty() {
        mSubjectsAdapter.setSubjects(Collections.<Subject>emptyList());
        mSubjectsAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), R.string.empty_subjects, Toast.LENGTH_LONG).show();
    }

}
