package com.sunny.mvpzhihu.ui.themedetail;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Story;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Theme daily adapter.
 * Created by Zhou Zejin on 2017/6/7.
 */
public class ThemeDailyAdapter extends RecyclerView.Adapter<ThemeDailyAdapter.ThemeDailyViewHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<Story> mStories = new ArrayList<>();

    private ThemeDailyListener mThemeDailyListener;

    @Inject
    public ThemeDailyAdapter(@ActivityContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    public void setThemeDailyListener(ThemeDailyListener themeDailyListener) {
        mThemeDailyListener = themeDailyListener;
    }

    @Override
    public ThemeDailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new ThemeDailyViewHolder(layoutInflater.inflate(R.layout.item_theme_daily, parent, false));
    }

    @Override
    public void onBindViewHolder(ThemeDailyViewHolder holder, final int position) {
        Story story = mStories.get(position);
        if (story == null) return;
        List<String> images = story.images();
        if (images != null) {
            ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                    .placeHolder(R.drawable.image_default).build();
            mImageLoader.displayUrlImage(mContext, holder.mIvThemeDailyImage, images.get(0), option);
            holder.mIvThemeDailyImage.setVisibility(View.VISIBLE);
        } else {
            holder.mIvThemeDailyImage.setVisibility(View.GONE);
        }
        holder.mTvThemeDailyTitle.setText(story.title());

        holder.mCardViewThemeDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mThemeDailyListener != null)
                    mThemeDailyListener.onThemeDailyClick(mStories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public void addData(Collection<Story> stories) {
        mStories.addAll(stories);
    }

    public void clearData() {
        mStories.clear();
    }

    class ThemeDailyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view_theme_daily)
        CardView mCardViewThemeDaily;
        @BindView(R.id.iv_theme_daily_image)
        ImageView mIvThemeDailyImage;
        @BindView(R.id.tv_theme_daily_title)
        TextView mTvThemeDailyTitle;

        ThemeDailyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ThemeDailyListener {

        void onThemeDailyClick(Story story);
    }

}
