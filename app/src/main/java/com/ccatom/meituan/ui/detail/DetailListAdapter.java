package com.ccatom.meituan.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.ccatom.meituan.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.ViewHolder>{
    private static final String TAG = "DetailListAdapter";
    List<DetailBean.DataBean.ListBean> dataList;
    Context mContext;
    public DetailListAdapter(Context context,List<DetailBean.DataBean.ListBean> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DetailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item,parent,false);
        return new DetailListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailListAdapter.ViewHolder holder, int position) {
        //用于对RecyclerView的子项进行赋值，会在每个子项滚动到屏幕内的时候执行
        DetailBean.DataBean.ListBean data = this.dataList.get(position);
        holder.titleView.setText(data.getName());
        holder.lableView.setText(data.getLable());
        holder.saleView.setText(data.getSubTitle());
        holder.priceView.setText("￥"+data.getPrice());
//        holder.imageView.setImageURI(Uri.parse(data.getImage()));
        Log.d(TAG, "onBindViewHolder: "+data.getImage());
        Picasso.get().load(data.getImage()).into(holder.imageView);
        //点击进入详情页
        holder.clickButton.setOnClickListener(view -> {
            Toast.makeText(mContext,data.getName(),Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(mContext, DetailActivity.class);
//            intent.putExtra("title",homeListData.getTitle());
//            intent.putExtra("image",homeListData.getImage());
//            intent.putExtra("url",homeListData.getDetailUrl());
//            intent.putExtra("deliver",homeListData.getDeliverTime());
//            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView lableView;
        TextView saleView;
        TextView priceView;
        LinearLayout clickButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detail_item_image);
            titleView = itemView.findViewById(R.id.detail_item_title);
            lableView = itemView.findViewById(R.id.detail_item_lable);
            saleView = itemView.findViewById(R.id.detail_item_sale);
            priceView = itemView.findViewById(R.id.detail_item_price);
            clickButton = itemView.findViewById(R.id.detail_item_click);
        }
    }
}
