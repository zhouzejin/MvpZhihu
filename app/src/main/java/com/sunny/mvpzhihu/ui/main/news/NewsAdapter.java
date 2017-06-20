package com.sunny.mvpzhihu.ui.main.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.HotNews;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type News adapter.
 * Created by Zhou Zejin on 2017/6/20.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private NewsItemListener mNewsItemListener;

    private List<HotNews> mHotNewses = new ArrayList<>();

    @Inject
    public NewsAdapter(@FragmentContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    public void setNewsItemListener(NewsItemListener newsItemListener) {
        mNewsItemListener = newsItemListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new NewsViewHolder(layoutInflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        HotNews hotNews = mHotNewses.get(position);
        if (hotNews == null) return;
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, holder.mIvNewsImage, hotNews.thumbnail(), option);
        holder.mTvNewsTitle.setText(hotNews.title());
    }

    @Override
    public int getItemCount() {
        return mHotNewses.size();
    }

    public void addData(Collection<HotNews> hotNewses) {
        mHotNewses.addAll(hotNewses);
    }

    public void clearData() {
        mHotNewses.clear();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_news_image)
        ImageView mIvNewsImage;
        @BindView(R.id.tv_news_title)
        TextView mTvNewsTitle;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mNewsItemListener == null) return;
                    mNewsItemListener.onNewsClick(mHotNewses.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface NewsItemListener {

        void onNewsClick(HotNews hotNews);
    }

}
