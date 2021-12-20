package com.ccatom.meituan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ccatom.meituan.ui.home.HomeListAdapter;
import com.ccatom.meituan.ui.home.HomeListBean;
import com.ccatom.meituan.util.StringRequestUTF;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    String url = "http://10.0.2.2:5000/home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_main);
        setSupportActionBar(findViewById(R.id.home_actionbar));
        //------------------主页滑动列表-----------------------
        //绑定视图
        Log.d(TAG, "onCreate: 绑定视图");
        recyclerView = findViewById(R.id.home_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //网络请求并导入数据
        loadData(list->{
            HomeListAdapter adapter = new HomeListAdapter(MainActivity.this,list);
            recyclerView.setAdapter(adapter);
        });

    }


    interface Callback{
        void func(List<HomeListBean.DataBean> list);
    }
    private void loadData(MainActivity.Callback callback){
        // 创建请求
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequestUTF stringRequest = new StringRequestUTF(Request.Method.GET, url,
                response -> {
                    Log.d(TAG+"/network", "loadData: "+response);
                    Gson gson = new Gson();
                    HomeListBean homeListBean = gson.fromJson(response,HomeListBean.class);
                    callback.func(homeListBean.getData());

                },
                error -> {
                    error.printStackTrace();
                    Log.d(TAG+"/network", "loadData: "+error.toString());
                });
        queue.add(stringRequest);
    }
}
