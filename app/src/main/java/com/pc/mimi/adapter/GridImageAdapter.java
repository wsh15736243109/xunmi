package com.pc.mimi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.xlyuns.xunmi.R;

import java.util.List;


public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private static final int mSelectMax = 9;

    private List<LocalMedia> mList;

    public GridImageAdapter(List<LocalMedia> data) {
        super();
        this.mList = data;
    }

    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public void setOnAddPicClickListener(onAddPicClickListener onAddPicClickListener) {
        mOnAddPicClickListener = onAddPicClickListener;
    }

    public interface onAddPicClickListener {
        void onAddPicClick();

        void onItemClick(int position, View v);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gv_filter_image,
                parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            holder.mImg.setImageResource(R.drawable.addimg_1x);
            holder.ll_del.setVisibility(View.GONE);
            holder.mImg.setOnClickListener(view -> mOnAddPicClickListener.onAddPicClick());
        } else {
            holder.ll_del.setVisibility(View.VISIBLE);
            holder.ll_del.setOnClickListener(view -> {
                mList.remove(position);
                notifyDataSetChanged();
            });
            Glide.with(holder.mImg).load(mList.get(position).getCompressPath()).into(holder.mImg);
            //itemView 的点击事件
            if (mOnAddPicClickListener != null) {
                holder.itemView.setOnClickListener(view -> {
                    int adapterPosition = holder.getAdapterPosition();
                    mOnAddPicClickListener.onItemClick(adapterPosition, view);
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = mList.size() == 0 ? 0 : mList.size();
        return position == size;
    }

    @Override
    public int getItemCount() {
        if (mList.size() < mSelectMax) {
            return mList.size() + 1;
        } else {
            return mList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        }
    }
}
