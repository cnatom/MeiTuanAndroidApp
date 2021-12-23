package com.ccatom.meituan.ui.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ccatom.meituan.R;
import com.ccatom.meituan.bean.DetailBean;
import com.ccatom.meituan.bean.SubmitBean;
import com.ccatom.meituan.store.ShoppingCart;
import com.ccatom.meituan.util.StringRequestUTF;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.ccatom.meituan.MyApplication.getContext;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "DetailActivity";
    View windowsView;
    static ShoppingCart cart;
    TextView titleView;
    TextView deleverView;
    TextView announceView;
    ImageView imageView;
    LinearLayout submitView;
    public static TextView priceTotalView;
    public static CardView dotView;
    public static TextView dotTextView;
    RecyclerView recyclerView;
    RecyclerView recyclerCartView;
    CardView shopButtonView;
    LinearLayout clearView;
    RelativeLayout bottomSheetView;
    public static DetailCartAdapter popupCartAdapter;
    LinearLayoutManager recyclerCartLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);
        Log.d(TAG, "onCreate");
        //新建购物车
        cart = new ShoppingCart();
        //设置顶栏，添加返回按钮
        setSupportActionBar(findViewById(R.id.detail_toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //获取上一页传来的数据，以初始化
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String image = intent.getStringExtra("image");
        String url = intent.getStringExtra("url");
        String deliver = intent.getStringExtra("deliver");
        //绑定布局
        titleView = findViewById(R.id.detail_title);
        deleverView = findViewById(R.id.detail_deliver);
        announceView = findViewById(R.id.detail_announce);
        imageView = findViewById(R.id.detail_image);
        recyclerView = findViewById(R.id.detail_recycler);
        shopButtonView = findViewById(R.id.detail_shop_button);
        bottomSheetView = findViewById(R.id.detail_bottom_sheet);
        dotTextView = findViewById(R.id.detail_bottom_dot_text);
        dotView = findViewById(R.id.detail_bottom_dot);
        submitView = findViewById(R.id.detail_submit_button);
        priceTotalView = findViewById(R.id.detail_bottom_price_total);
        //填充布局
        titleView.setText(title);
        deleverView.setText(deliver);
        Picasso.get().load(image).into(imageView);
        shopButtonView.setOnClickListener(this);
        submitView.setOnClickListener(this);
        dotView.setVisibility(View.INVISIBLE);
        initCartRecycler();
        //获取数据并填充布局
        loadData(url,v->{
            //填充布局
            announceView.setText(v.getData().getAnnounce());
            LinearLayoutManager detailListLayoutManager = new LinearLayoutManager(this){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(detailListLayoutManager);
            DetailListAdapter detailListAdapter = new DetailListAdapter(DetailActivity.this,v.getData().getList());
            recyclerView.setAdapter(detailListAdapter);
        });

    }
    //初始化购物车的RecyclerView与Adapter
    private void initCartRecycler(){
        windowsView = LayoutInflater.from(getContext()).inflate(R.layout.detail_cart,null);
        clearView = windowsView.findViewById(R.id.detail_cart_clear);
        clearView.setOnClickListener(v->{
            DetailActivity.cart.clear();
            refreshDot();
            refreshPriceTotal();
            popupCartAdapter.notifyDataSetChanged();
        });
        //PopupWindow内的RecyclerView
        recyclerCartView = (RecyclerView) windowsView.findViewById(R.id.detail_cart_recycler);
        recyclerCartLayoutManager = new LinearLayoutManager(getContext());
        recyclerCartView.setLayoutManager(recyclerCartLayoutManager);
        //PopupWindow的RecyclerView的适配器
        popupCartAdapter = new DetailCartAdapter(DetailActivity.cart,getContext());
        recyclerCartView.setAdapter(popupCartAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_shop_button:{
                showPopupWindow();
                break;
            }
            case R.id.detail_submit_button:{
                submitCart();
            }
        }
    }
    //刷新底栏dot
    @SuppressLint("SetTextI18n")
    public static void refreshDot(){
        dotTextView.setText(Integer.valueOf(DetailActivity.cart.getSize()).toString());
        if(cart.getSize()==0){
            dotView.setVisibility(View.INVISIBLE);
        }else{
            dotView.setVisibility(View.VISIBLE);
        }
    }
    //刷新底栏总价
    @SuppressLint("SetTextI18n")
    public static void refreshPriceTotal(){
        priceTotalView.setText(Double.valueOf(cart.priceTotal).toString());
    }
    //发送订单
    private void submitCart(){
        String url = "http://10.0.2.2:5000/submit";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequestUTF stringRequest = new StringRequestUTF(Request.Method.POST, url,
                response -> {
                    Gson gson = new Gson();
                    SubmitBean submitBean = gson.fromJson(response,SubmitBean.class);
                    showQrDialog(submitBean.getPath());;
                },
                error -> {
                    error.printStackTrace();
                    Log.d(TAG+"/network", "loadData: 加载数据失败");
                }){

            @Override
            public byte[] getBody() throws AuthFailureError {
                byte[] bytes = null;
                try {
                    Gson gson = new Gson();
                    bytes = gson.toJson(DetailActivity.cart.toJson()).getBytes();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return bytes;
            }
        };
        requestQueue.add(stringRequest);
    }
    //显示二维码dialog
    public void showQrDialog(String url) {
        ImageView imageView;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View contentView = View.inflate(this, R.layout.detail_qr_dialog, null);
        imageView = contentView.findViewById(R.id.detail_qr_image);
        builder.setView(contentView);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);
        dialog.show();
    }
    //显示底栏
    private void showPopupWindow() {
        int windowHeight = getDeviceHeight()/4;
        int yoff = -(windowHeight+getBottomSheetHeight());
        popupCartAdapter.notifyDataSetChanged();
        //创建一个PopupWindow对象
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setHeight(windowHeight);//高度
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//宽度
        popupWindow.setContentView(windowsView);//绑定视图
        popupWindow.setFocusable(true);//获取焦点
        popupWindow.setOverlapAnchor(false);
        popupWindow.setAttachedInDecor(true);
        popupWindow.showAsDropDown(bottomSheetView,0,yoff);//播放
    }

    //获取底栏的高度
    private int getBottomSheetHeight(){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        bottomSheetView.measure(w, h);
        return bottomSheetView.getMeasuredHeight();
    }
    //获取设备高度
    private int getDeviceHeight(){
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    private interface Callback{
        void response(DetailBean detailBean);
    }
    private void loadData(String url,DetailActivity.Callback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequestUTF stringRequest = new StringRequestUTF(Request.Method.GET, url,
                response -> {
                    Log.d(TAG+"/network", "loadData: 成功加载数据");
                    Gson gson = new Gson();
                    DetailBean detailBean = gson.fromJson(response,DetailBean.class);
                    callback.response(detailBean);
                },
                error -> {
                    error.printStackTrace();
                    Log.d(TAG+"/network", "loadData: 加载数据失败");
                });
        requestQueue.add(stringRequest);
    }
}