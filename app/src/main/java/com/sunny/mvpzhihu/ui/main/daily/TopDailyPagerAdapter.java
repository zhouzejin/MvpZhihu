package com.sunny.mvpzhihu.ui.main.daily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.TopStory;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;
import com.sunny.mvpzhihu.widget.bannerviewpager.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * The type Top daily pager adapter.
 * Created by Zhou Zejin on 2017/5/17.
 */
public class TopDailyPagerAdapter extends ViewPagerAdapter {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<TopStory> mTopStories = new ArrayList<>();

    @Inject
    public TopDailyPagerAdapter(@FragmentContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return mTopStories.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_top_daily, container, false);
        ImageView ivTopDailyImage = (ImageView) view.findViewById(R.id.iv_top_daily_image);
        TextView tvTopDailyTitle = (TextView) view.findViewById(R.id.tv_top_daily_title);
        TopStory topStory = mTopStories.get(position);

        mImageLoader.displayUrlImage(mContext, ivTopDailyImage, topStory.image(),
                new ImageLoader.DisplayOption.Builder().placeHolder(R.drawable.image_default).build());
        tvTopDailyTitle.setText(topStory.title());

        if (getOnPageClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnPageClickListener().onPageClick(v, position);
                }
            });
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addData(Collection<TopStory> stories) {
        mTopStories.addAll(stories);
    }

    public void clearData() {
        mTopStories.clear();
    }

    public TopStory getItemData(int position) {
        if (position < 0 || position > mTopStories.size() - 1)
            throw new RuntimeException("参数错误！");
        return mTopStories.get(position);
    }

}
