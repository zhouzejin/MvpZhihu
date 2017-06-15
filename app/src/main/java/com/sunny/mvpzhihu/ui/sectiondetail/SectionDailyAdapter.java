package com.sunny.mvpzhihu.ui.sectiondetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.SectionStory;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Section daily adapter.
 * Created by Zhou Zejin on 2017/6/15.
 */
public class SectionDailyAdapter extends RecyclerView.Adapter<SectionDailyAdapter.SectionDailyViewHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private SectionDailyItemListener mItemListener;

    private List<SectionStory> mSectionStories = new ArrayList<>();

    @Inject
    public SectionDailyAdapter(@ActivityContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    public void setItemListener(SectionDailyItemListener itemListener) {
        mItemListener = itemListener;
    }

    @Override
    public SectionDailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new SectionDailyViewHolder(layoutInflater.inflate(R.layout.item_section_daily, parent, false));
    }

    @Override
    public void onBindViewHolder(SectionDailyViewHolder holder, int position) {
        SectionStory story = mSectionStories.get(position);
        if (story == null) return;
        holder.mTvSectionDailyTitle.setText(story.title());
        holder.mTvSectionDailyDate.setText(story.display_date());
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, holder.mIvSectionDailyImage, story.images().get(0), option);
    }

    @Override
    public int getItemCount() {
        return mSectionStories.size();
    }

    public void addData(Collection<SectionStory> sectionStories) {
        mSectionStories.addAll(sectionStories);
    }

    public void clearData() {
        mSectionStories.clear();
    }

    class SectionDailyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_section_daily_title)
        TextView mTvSectionDailyTitle;
        @BindView(R.id.tv_section_daily_date)
        TextView mTvSectionDailyDate;
        @BindView(R.id.iv_section_daily_image)
        ImageView mIvSectionDailyImage;

        SectionDailyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener == null) return;
                    mItemListener.onItemClick(mSectionStories.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface SectionDailyItemListener {

        void onItemClick(SectionStory sectionStory);
    }

}
