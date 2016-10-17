package com.sunny.mvpzhihu.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<Subject> mSubjects;

    @Inject
    public SubjectsAdapter(@FragmentContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;

        mSubjects = new ArrayList<>();
    }

    public void setSubjects(List<Subject> subjects) {
        mSubjects = subjects;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubjectViewHolder holder, int position) {
        Subject subject = mSubjects.get(position);
        mImageLoader.displayUrlImage(mContext, holder.imageView, subject.images().small(),
                new ImageLoader.DisplayOption.Builder().build());
        holder.titleTextView.setText(subject.title());
        holder.genresTextView.setText(subject.genres().toString());
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pic)
        ImageView imageView;
        @BindView(R.id.text_title)
        TextView titleTextView;
        @BindView(R.id.text_genres)
        TextView genresTextView;

        public SubjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
