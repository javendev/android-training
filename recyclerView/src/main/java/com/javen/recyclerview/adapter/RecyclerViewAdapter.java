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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mTitle;
    private List<Integer> mPic;

    public RecyclerViewAdapter(Context context,List<String> title,List<Integer> pic){
        mContext=context;
        mTitle=title;
        mPic=pic;
        mLayoutInflater=LayoutInflater.from(context);

    }
     //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  static class NormalViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        CardView mCardView;
        ImageView mImageView;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mTextView=(TextView)itemView.findViewById(R.id.tv_text);
            mCardView=(CardView)itemView.findViewById(R.id.cv_item);
            mImageView=(ImageView)itemView.findViewById(R.id.iv_pic);
        }
}
        //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，
        // 这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_view,parent,false));
    }
     //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final NormalViewHolder holder, final int position) {
        holder.mTextView.setText(mTitle.get(position));
        holder.mImageView.setBackgroundResource(mPic.get(position));
        /*holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mTitle[position],Toast.LENGTH_LONG).show();
            }
        });*/
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
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mTitle==null ? 0 : mTitle.size();
    }

    public void addData(int position) {
        mPic.add(position,R.drawable.wapvideo_49);
        mTitle.add(position,"Insert One");
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mPic.remove(position);
        mTitle.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}