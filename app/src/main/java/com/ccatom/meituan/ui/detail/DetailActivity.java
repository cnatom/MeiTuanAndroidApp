package com.ccatom.meituan.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ccatom.meituan.R;
import com.ccatom.meituan.util.StringRequestUTF;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    TextView titleView;
    TextView deleverView;
    TextView announceView;
    ImageView imageView;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);
        //设置顶栏
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
        //填充布局
        titleView.setText(title);
        deleverView.setText(deliver);
        Picasso.get().load(image).into(imageView);
        //获取数据并填充布局
        loadData(url,v->{
            //填充布局v
            announceView.setText(v.getData().getAnnounce());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            DetailListAdapter detailListAdapter = new DetailListAdapter(DetailActivity.this,v.getData().getList());
            recyclerView.setAdapter(detailListAdapter);
        });

    }
    private interface Callback{
        void response(DetailBean detailBean);
    }
    private void loadData(String url,DetailActivity.Callback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequestUTF stringRequest = new StringRequestUTF(Request.Method.GET, url,
                response -> {
                    Log.d(TAG+"/network", "loadData: "+response);
                    Gson gson = new Gson();
                    DetailBean detailBean = gson.fromJson(response,DetailBean.class);
                    callback.response(detailBean);
                },
                error -> {
                    error.printStackTrace();
                    Log.d(TAG+"/network", "loadData: "+error.toString());
                });
        requestQueue.add(stringRequest);
    }
}