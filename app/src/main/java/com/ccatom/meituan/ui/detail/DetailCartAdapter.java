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
import com.ccatom.meituan.store.ShoppingCart;

import java.util.List;


public class DetailCartAdapter extends RecyclerView.Adapter<DetailCartAdapter.ViewHolder>{
    private String TAG = "DetailCartAdapter";
    ShoppingCart cart;
    Context context;

    LinearLayout clearView;
    public DetailCartAdapter(ShoppingCart data, Context context) {
        Log.d(TAG, "DetailCartAdapter: 构造函数");
        this.context = context;
        this.cart = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private String TAG = "DetailCartAdapter-ViewHolder";
        ImageView addView;
        ImageView delView;
        TextView titleView;
        TextView cntView;
        TextView priceView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: 构造函数");
            addView = itemView.findViewById(R.id.detail_cart_item_add);
            delView = itemView.findViewById(R.id.detail_cart_item_mini);
            titleView = itemView.findViewById(R.id.detail_cart_item_title);
            cntView = itemView.findViewById(R.id.detail_cart_item_cnt);
            priceView = itemView.findViewById(R.id.detail_cart_item_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_cart_item,parent,false);
        return new DetailCartAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder"+Integer.valueOf(position).toString());
        List<ShoppingCart.CartItem> list = cart.getCartItemList();
        ShoppingCart.CartItem data = list.get(position);
        holder.titleView.setText(data.title);
        holder.priceView.setText(Double.valueOf(data.priceTotal).toString());
        holder.cntView.setText(Integer.valueOf(data.cnt).toString());
        holder.delView.setOnClickListener(v->{
            DetailActivity.cart.deleteByStr(data.title);
            DetailActivity.popupCartAdapter.notifyDataSetChanged();
            DetailActivity.refreshPriceTotal();
            DetailActivity.refreshDot();

        });
        holder.addView.setOnClickListener(v->{
            DetailActivity.cart.addByStr(data.title);
            DetailActivity.refreshPriceTotal();
            DetailActivity.refreshDot();
            DetailActivity.popupCartAdapter.notifyDataSetChanged();
        });

    }


    @Override
    public int getItemCount() {
        int cnt = cart.typeCnt();
        Log.d(TAG, "getItemCount: "+ Integer.valueOf(cnt).toString());
        return cnt;
    }
}
