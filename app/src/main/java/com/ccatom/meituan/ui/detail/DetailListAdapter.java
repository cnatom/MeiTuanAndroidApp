package com.ccatom.meituan.ui.detail;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.ccatom.meituan.bean.DetailBean;
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

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @NonNull
    @Override
    public DetailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DetailListAdapter.ViewHolder holder, int position) {
        //用于对RecyclerView的子项进行赋值，会在每个子项滚动到屏幕内的时候执行
        DetailBean.DataBean.ListBean data = this.dataList.get(position);
        holder.titleView.setText(data.getName());
        holder.lableView.setText(data.getLable());
        holder.saleView.setText(data.getSubTitle());
        holder.priceView.setText("￥"+data.getPrice());
        Log.d(TAG, "onBindViewHolder: "+data.getImage());
        Picasso.get().load(data.getImage()).into(holder.imageView);
        //选购
        holder.clickButton.setOnClickListener(view -> {
            DetailActivity.cart.add(data);
            DetailActivity.refreshPriceTotal();
            DetailActivity.refreshDot();
            DetailActivity.popupCartAdapter.notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
