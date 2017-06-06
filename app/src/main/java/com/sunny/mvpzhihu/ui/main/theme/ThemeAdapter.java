package com.sunny.mvpzhihu.ui.main.theme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Theme;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Theme adapter.
 * Created by Zhou Zejin on 2017/6/6.
 */
public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<Theme> mThemes = new ArrayList<>();

    private ThemeItemListener mItemListener;

    @Inject
    public ThemeAdapter(@FragmentContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    public void setItemListener(ThemeItemListener itemListener) {
        mItemListener = itemListener;
    }

    @Override
    public ThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new ThemeViewHolder(layoutInflater.inflate(R.layout.item_theme, parent, false));
    }

    @Override
    public void onBindViewHolder(ThemeViewHolder holder, int position) {
        Theme theme = mThemes.get(position);
        if (theme == null) return;
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, holder.mIvThemeImage, theme.thumbnail(), option);
        holder.mTvThemeDescription.setText(theme.description());
        holder.mTvThemeName.setText(theme.name());
    }

    @Override
    public int getItemCount() {
        return mThemes.size();
    }

    public void addData(Collection<Theme> themes) {
        mThemes.addAll(themes);
    }

    public void clearData() {
        mThemes.clear();
    }

    class ThemeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_theme_image)
        ImageView mIvThemeImage;
        @BindView(R.id.tv_theme_description)
        TextView mTvThemeDescription;
        @BindView(R.id.tv_theme_name)
        TextView mTvThemeName;

        ThemeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener != null)
                        mItemListener.onThemeClick(mThemes.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ThemeItemListener {

        void onThemeClick(Theme theme);
    }

}
