package com.sunny.mvpzhihu.ui.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.utils.DateUtil;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Story adapter.
 * Created by Zhou Zejin on 2017/4/27.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {

    private static final int ITEM_CONTENT = 0;
    private static final int ITEM_TIME = 1;

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private DailyItemListener mItemListener;

    private List<DailyModel> mDailyModels = new ArrayList<>();

    @Inject
    public DailyAdapter(@FragmentContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    public void setItemListener(DailyItemListener itemListener) {
        mItemListener = itemListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return ITEM_TIME;

        String time = mDailyModels.get(position).getDate();
        boolean isDiff = !mDailyModels.get(position - 1).getDate().equals(time);
        return isDiff ? ITEM_TIME : ITEM_CONTENT;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (viewType == ITEM_TIME) {
            return new DailyTimeViewHolder(layoutInflater.inflate(R.layout.item_daily_time,
                    parent, false));
        } else {
            return new DailyViewHolder(layoutInflater.inflate(R.layout.item_daily, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {
        DailyModel model = mDailyModels.get(position);
        if (model == null) return;
        setDailyData(holder, model);
        if (holder instanceof DailyTimeViewHolder) {
            DailyTimeViewHolder dailyTimeHoder = (DailyTimeViewHolder) holder;
            String time = DateUtil.formatDate(model.getDate() + "  " +
                    DateUtil.getWeek(model.getDate()));
            if (position == 0) time = mContext.getString(R.string.today_hot_news);
            dailyTimeHoder.mTvDailyTime.setText(time);
        }
    }

    private void setDailyData(DailyViewHolder holder, final DailyModel model) {
        holder.mCardViewDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) mItemListener.onDailyClick(model);
            }
        });

        holder.mTvDailyTitle.setText(model.getStory().title());

        List<String> images = model.getStory().images();
        if (images.size() > 0) {
            mImageLoader.displayUrlImage(mContext, holder.mIvDailyImage, images.get(0),
                    new ImageLoader.DisplayOption.Builder().placeHolder(R.drawable.image_default)
                            .build());
        }

        if (model.getStory().multipic() != null) {
            holder.mIvMorePic.setVisibility(View.VISIBLE);
        } else {
            holder.mIvMorePic.setVisibility(View.GONE);
        }

        if (model.getRead()) {
            holder.mTvDailyTitle.setTextColor(ContextCompat.getColor(mContext,
                    R.color.text_color_read));
        } else {
            holder.mTvDailyTitle.setTextColor(ContextCompat.getColor(mContext,
                    R.color.text_color_unread));
        }
    }

    @Override
    public int getItemCount() {
        return mDailyModels.size();
    }

    public void addData(Collection<DailyModel> models) {
        mDailyModels.addAll(models);
    }

    public void clearData() {
        mDailyModels.clear();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view_daily)
        CardView mCardViewDaily;
        @BindView(R.id.tv_daily_title)
        TextView mTvDailyTitle;
        @BindView(R.id.iv_daily_image)
        ImageView mIvDailyImage;
        @BindView(R.id.iv_more_pic)
        ImageView mIvMorePic;

        DailyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DailyTimeViewHolder extends DailyViewHolder {

        @BindView(R.id.tv_daily_time)
        TextView mTvDailyTime;

        DailyTimeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface DailyItemListener {

        void onDailyClick(DailyModel dailyModel);
    }

}
