package com.ccatom.meituan.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccatom.meituan.R;
import com.ccatom.meituan.bean.HomeListBean;
import com.ccatom.meituan.ui.detail.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    private static final String TAG = "HomeListAdapter";
    //数据部分
    private Context mContext;//主页的上下文
    private final List<HomeListBean.DataBean> homeListData;
    public HomeListAdapter(Context context,List<HomeListBean.DataBean> fruitList){
        Log.d(TAG, "HomeListAdapter: 创建了一个Adapter");
        mContext = context;
        homeListData =fruitList;
    }
    //布局元素实例化
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        TextView saleText;
        TextView deliverText;
        TextView deliverTimeText;
        TextView lableText;
        ImageView previewImage;
        LinearLayout clickView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.home_list_item_title);
            saleText = itemView.findViewById(R.id.home_list_item_sale);
            deliverText = itemView.findViewById(R.id.home_list_item_deliver);
            deliverTimeText = itemView.findViewById(R.id.home_list_item_deliver_time);
            lableText = itemView.findViewById(R.id.home_list_item_lable);
            previewImage = itemView.findViewById(R.id.home_list_item_image);
            clickView = itemView.findViewById(R.id.home_list_item_click);
        }
    }
    //创建单项布局
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        return new ViewHolder(view);
    }
    //用数据填充单项布局
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: 填充了数据");
        //用于对RecyclerView的子项进行赋值，会在每个子项滚动到屏幕内的时候执行
        HomeListBean.DataBean homeListData= this.homeListData.get(position);
        holder.titleText.setText(homeListData.getTitle());
        holder.saleText.setText(homeListData.getSale());
        holder.deliverText.setText(homeListData.getDeliver());
        holder.deliverTimeText.setText(homeListData.getDeliverTime());
        holder.lableText.setText(homeListData.getLable());
        Log.d(TAG, "onBindViewHolder: "+homeListData.getImage());
        Picasso.get().load(homeListData.getImage()).into(holder.previewImage);

        //点击进入详情页
        holder.clickView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("title",homeListData.getTitle());
            intent.putExtra("image",homeListData.getImage());
            intent.putExtra("url",homeListData.getDetailUrl());
            intent.putExtra("deliver",homeListData.getDeliverTime());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return homeListData.size();
    }


}


