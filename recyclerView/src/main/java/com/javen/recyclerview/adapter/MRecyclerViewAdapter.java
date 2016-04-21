package com.javen.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.javen.recyclerview.R;

import java.util.List;

public class MRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mTitle;
    private List<Integer> mPic;
    //建立枚举 2个item 类型
    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }
    public MRecyclerViewAdapter(Context context, List<String> title, List<Integer> pic){
        mContext=context;
        mTitle=title;
        mPic=pic;
        mLayoutInflater=LayoutInflater.from(context);

    }
     //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  static class Item1ViewHolder  extends RecyclerView.ViewHolder{
        TextView mTextView;
        CardView mCardView;
        ImageView mImageView;
        public Item1ViewHolder (View itemView) {
            super(itemView);
            mTextView=(TextView)itemView.findViewById(R.id.tv_text);
            mCardView=(CardView)itemView.findViewById(R.id.cv_item);
            mImageView=(ImageView)itemView.findViewById(R.id.iv_pic);
        }
    }

    //item2 的ViewHolder
    public static class Item2ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        public Item2ViewHolder(View itemView) {
            super(itemView);
            mTextView=(TextView)itemView.findViewById(R.id.tv_item2_text);
        }
    }

    /**
     * 在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，
     * 这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new Item1ViewHolder(mLayoutInflater.inflate(R.layout.item_view, parent, false));
        } else {
            return new Item2ViewHolder(mLayoutInflater.inflate(R.layout.item_view2, parent, false));
        }
    }
     //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Item1ViewHolder) {
            ((Item1ViewHolder)holder).mTextView.setText(mTitle.get(position));
            ((Item1ViewHolder)holder).mImageView.setBackgroundResource(mPic.get(position));
        } else if (holder instanceof Item2ViewHolder) {
            ((Item2ViewHolder) holder).mTextView.setText(mTitle.get(position));
        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，
        // 这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        return position % 2 == 0 ? ITEM_TYPE.ITEM1.ordinal() : ITEM_TYPE.ITEM2.ordinal();
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mTitle==null ? 0 : mTitle.size();
    }

    public void addData(int position) {
        notifyItemInserted(position);
        mPic.add(position,R.drawable.wapvideo_49);
        mTitle.add(position,"Insert One");
        notifyItemRangeChanged(position, getItemCount());
    }

    public void removeData(int position) {
        notifyItemRemoved(position);
        mPic.remove(position);
        mTitle.remove(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}