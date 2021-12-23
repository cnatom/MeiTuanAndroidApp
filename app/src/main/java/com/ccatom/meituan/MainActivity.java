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
        //修改ActionBar
        setSupportActionBar(findViewById(R.id.home_actionbar));
        //------------------主页滑动列表-----------------------
        //绑定视图
        Log.d(TAG, "onCreate: 绑定视图");
        recyclerView = findViewById(R.id.home_recycler);//绑定ID
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);//布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);//添加布局管理器
        //进行网络请求，通过实现Callback接口获取数据list，导入适配器
        loadData(list -> {
            HomeListAdapter adapter = new HomeListAdapter(MainActivity.this,list);//初始化适配器并传入数据
            recyclerView.setAdapter(adapter);//设置适配器
        });
    }

    interface Callback{
        void func(List<HomeListBean.DataBean> list);
    }
    private void loadData(MainActivity.Callback callback){
        //创建请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        //创建请求
        StringRequestUTF stringRequest = new StringRequestUTF(Request.Method.GET, url,
                response -> {
                    //请求成功回调
                    Log.d(TAG+"/network", "loadData: "+response);
                    Gson gson = new Gson();//用于Json解析
                    HomeListBean homeListBean = gson.fromJson(response,HomeListBean.class);//将响应String转换为实体类
                    callback.func(homeListBean.getData());//将实体类对象通过回调发出去
                },
                error -> {
                    //请求失败回调
                    error.printStackTrace();
                });
        //加入请求队列
        queue.add(stringRequest);
    }
}
