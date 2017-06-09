package com.sunny.mvpzhihu.ui.main.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.model.bean.Section;
import com.sunny.mvpzhihu.injection.qualifier.FragmentContext;
import com.sunny.mvpzhihu.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Section adapter.
 * Created by Zhou Zejin on 2017/6/9.
 */
public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {

    private final Context mContext;
    private final ImageLoader mImageLoader;

    private List<Section> mSections = new ArrayList<>();

    private SectionItemListener mItemListener;

    @Inject
    public SectionAdapter(@FragmentContext Context context, ImageLoader imageLoader) {
        mContext = context;
        mImageLoader = imageLoader;
    }

    public void setItemListener(SectionItemListener itemListener) {
        mItemListener = itemListener;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new SectionViewHolder(layoutInflater.inflate(R.layout.item_section, parent, false));
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        Section section = mSections.get(position);
        if (section == null) return;
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder()
                .placeHolder(R.drawable.image_default).build();
        mImageLoader.displayUrlImage(mContext, holder.mIvSectionImage, section.thumbnail(), option);
        holder.mTvSectionName.setText(section.name());
        holder.mTvSectionDescription.setText(section.description());
    }

    @Override
    public int getItemCount() {
        return mSections.size();
    }

    public void addData(Collection<Section> sections) {
        mSections.addAll(sections);
    }

    public void clearData() {
        mSections.clear();
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_section_image)
        ImageView mIvSectionImage;
        @BindView(R.id.tv_section_name)
        TextView mTvSectionName;
        @BindView(R.id.tv_section_description)
        TextView mTvSectionDescription;

        SectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener != null)
                        mItemListener.onSectionClick(mSections.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface SectionItemListener {

        void onSectionClick(Section section);
    }

}
