package com.sunny.mvpzhihu.ui.themedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Editor;
import com.sunny.mvpzhihu.injection.qualifier.ActivityContext;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;
import com.sunny.mvpzhihu.widget.CircleImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Theme editor adapter.
 * Created by Zhou Zejin on 2017/6/7.
 */
public class ThemeEditorAdapter extends RecyclerView.Adapter<ThemeEditorAdapter.ThemeEditorHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<Editor> mEditors = new ArrayList<>();

    @Inject
    public ThemeEditorAdapter(@ActivityContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    @Override
    public ThemeEditorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new ThemeEditorHolder(layoutInflater.inflate(R.layout.item_theme_editor, parent, false));
    }

    @Override
    public void onBindViewHolder(ThemeEditorHolder holder, int position) {
        Editor editor = mEditors.get(position);
        if (editor == null) return;
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, holder.mCivThemeEditorAvatar, editor.avatar(), option);
    }

    @Override
    public int getItemCount() {
        return mEditors.size();
    }

    public void addData(Collection<Editor> editors) {
        mEditors.addAll(editors);
    }

    public void clearData() {
        mEditors.clear();
    }

    class ThemeEditorHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_theme_editor_avatar)
        CircleImageView mCivThemeEditorAvatar;

        ThemeEditorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
