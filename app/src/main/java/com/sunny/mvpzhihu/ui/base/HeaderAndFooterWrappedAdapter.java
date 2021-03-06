package com.sunny.mvpzhihu.ui.base;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * The type Header and footer wrapped adapter.
 * Created by Zhou Zejin on 2017/5/16.
 *
 * @see <a href="Android 优雅的为RecyclerView添加HeaderView和FooterView">http://blog.csdn.net/lmj623565791/article/details/51854533</a>
 */
public class HeaderAndFooterWrappedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mWrappedAdapter;

    public HeaderAndFooterWrappedAdapter(RecyclerView.Adapter wrappedAdapter) {
        mWrappedAdapter = wrappedAdapter;
        mWrappedAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                notifyItemRangeChanged(getHeaderCount() + positionStart, itemCount);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                notifyItemRangeChanged(getHeaderCount() + positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                notifyItemRangeInserted(getHeaderCount() + positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                notifyItemRangeRemoved(getHeaderCount() + positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                notifyItemRangeRemoved(getHeaderCount() + fromPosition,
                        getHeaderCount() + toPosition + itemCount);
            }
        });
    }

    private int getHeaderCount() {
        return mHeaderViews.size();
    }

    private int getFooterCount() {
        return mFooterViews.size();
    }

    private int getRealItemCount() {
        return mWrappedAdapter.getItemCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(BASE_ITEM_TYPE_HEADER + getHeaderCount(), view);
    }

    public void addFooterView(View view) {
        mFooterViews.put(BASE_ITEM_TYPE_FOOTER + getFooterCount(), view);
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeaderCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeaderCount() + getRealItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position))
            return mHeaderViews.keyAt(position);
        else if (isFooterViewPos(position))
            return mFooterViews.keyAt(position - getHeaderCount() - getRealItemCount());
        return mWrappedAdapter.getItemViewType(position - getHeaderCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null)
            return new StaticViewHolder(mHeaderViews.get(viewType));
        else if (mFooterViews.get(viewType) != null)
            return new StaticViewHolder(mFooterViews.get(viewType));
        return mWrappedAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position))
            return;
        if (isFooterViewPos(position))
            return;
        mWrappedAdapter.onBindViewHolder(holder, position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getRealItemCount() + getFooterCount();
    }

    /**
     * 针对GridLayoutManager
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mWrappedAdapter.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    } else if (mFooterViews.get(viewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null)
                        return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    /**
     * 针对StaggeredGridLayoutManage
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mWrappedAdapter.onViewAttachedToWindow(holder);

        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    private static final class StaticViewHolder extends RecyclerView.ViewHolder {

        private StaticViewHolder(View itemView) {
            super(itemView);
        }
    }

}
