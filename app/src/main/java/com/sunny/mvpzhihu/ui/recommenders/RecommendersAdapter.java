package com.sunny.mvpzhihu.ui.recommenders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * The type Recommenders adapter.
 * Created by Zhou Zejin on 2017/5/31.
 */
public class RecommendersAdapter extends RecyclerView.Adapter<RecommendersAdapter.RecommendersHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<Editor> mEditors = new ArrayList<>();

    @Inject
    public RecommendersAdapter(@ActivityContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    @Override
    public RecommendersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new RecommendersHolder(layoutInflater.inflate(R.layout.item_recommenders, parent, false));
    }

    @Override
    public void onBindViewHolder(RecommendersHolder holder, int position) {
        Editor editor = mEditors.get(position);
        if (editor == null) return;
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, holder.mCivAvatar, editor.avatar(), option);
        holder.mTvName.setText(editor.name());
        holder.mTvBio.setText(editor.bio());
        holder.mTvTitle.setText(editor.title());
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

    class RecommendersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_avatar)
        CircleImageView mCivAvatar;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_bio)
        TextView mTvBio;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        RecommendersHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
