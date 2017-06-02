package com.sunny.mvpzhihu.ui.comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Comment;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.utils.DateUtil;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;
import com.sunny.mvpzhihu.widget.CircleImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Comment adapter.
 * Created by Zhou Zejin on 2017/6/2.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<Comment> mComments = new ArrayList<>();

    @Inject
    public CommentAdapter(@FragmentContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new CommentViewHolder(layoutInflater.inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        if (comment == null) return;
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, holder.mCivAvatar, comment.avatar(), option);
        holder.mTvAuthor.setText(comment.author());
        holder.mTvLikes.setText(String.valueOf(comment.likes()));
        holder.mTvContent.setText(comment.content());
        holder.mTvTime.setText(DateUtil.getTime(comment.time()));
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void addData(Collection<Comment> comments) {
        mComments.addAll(comments);
    }

    public void clearData() {
        mComments.clear();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_avatar)
        CircleImageView mCivAvatar;
        @BindView(R.id.tv_author)
        TextView mTvAuthor;
        @BindView(R.id.tv_likes)
        TextView mTvLikes;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_time)
        TextView mTvTime;

        CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
