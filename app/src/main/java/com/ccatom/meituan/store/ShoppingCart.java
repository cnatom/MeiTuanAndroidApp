package com.ccatom.meituan.store;

import android.os.Build;

import com.ccatom.meituan.bean.DetailBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//购物车类
public class ShoppingCart {
    public List<DetailBean.DataBean.ListBean> dataList;//菜列表
    public Map<DetailBean.DataBean.ListBean,Integer> cntMap;//某道菜被点了多少次
    public double priceTotal;//总价
    public int getSize() {
        return dataList.size();
    }

    //构造函数
    public ShoppingCart() {
        dataList = new ArrayList<>();
        cntMap = new HashMap<>();
        priceTotal = 0;
    }
    //生成购物车数据列表
    public List<CartItem> getCartItemList(){
        List<CartItem> list = new ArrayList<>();
        for (DetailBean.DataBean.ListBean data : cntMap.keySet()){
            int cnt = cntMap.get(data);
            double priceTotal = Double.parseDouble(data.getPrice())*cnt;
            DecimalFormat df = new DecimalFormat("#.00");
            priceTotal = Double.parseDouble(df.format(priceTotal));
            CartItem cartItem = new CartItem(data.getName(),priceTotal,cnt);
            list.add(cartItem);
        }
        return list;
    }
    public Map<String,String> toJson(){
        Map<String,String> result = new HashMap<>();
        List<String> list = toStrList();
        result.put("data",list.toString());
        return result;
    }
    //购物车转换为String列表
    public List<String> toStrList(){
        List<String> result = new ArrayList<>();
        List<CartItem> list = getCartItemList();
        for(CartItem item : list){
            Map<String,String> m = new HashMap<>();
            m.put("title",item.title);
            m.put("priceTotal",Double.valueOf(item.priceTotal).toString());
            m.put("count",Integer.valueOf(item.cnt).toString());
            result.add(m.toString());
        }
        return result;
    }
    //购物车单项
    public class CartItem{
        public String title;//菜名
        public double priceTotal;//总价
        public int cnt;//数量

        public CartItem(String title, double priceTotal, int cnt) {
            this.title = title;
            this.priceTotal = priceTotal;
            this.cnt = cnt;
        }

    }
    //添加菜品
    public void add(DetailBean.DataBean.ListBean data){
        this.dataList.add(data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cntMap.put(data, cntMap.getOrDefault(data,0)+1);
        }
        priceTotal+=Double.parseDouble(data.getPrice());
    }
    //添加菜品（通过菜名
    public void addByStr(String text){
        for(int i = 0;i<dataList.size();i++){
            DetailBean.DataBean.ListBean d = dataList.get(i);
            if(d.getName().equals(text)){
                dataList.add(d);
                cntMap.put(d, cntMap.get(d)+1);
                priceTotal+=Double.parseDouble(d.getPrice());
                break;
            }
        }
    }
    //菜品类型数量
    public int typeCnt(){
        int result=0;
        for(DetailBean.DataBean.ListBean data : cntMap.keySet()){
            result++;
        }
        return result;
    }
    //删除菜品（通过菜名）
    public void deleteByStr(String text){
        for(int i = 0;i<dataList.size();i++){
            DetailBean.DataBean.ListBean d = dataList.get(i);
            if(d.getName().equals(text)){
                dataList.remove(i);
                priceTotal-=Double.parseDouble(d.getPrice());
                if(priceTotal<0) priceTotal=0;
                if(cntMap.containsKey(d)){
                    cntMap.put(d,cntMap.get(d)-1);
                    if(cntMap.get(d).equals(0)){
                        cntMap.remove(d);
                    }
                }
                break;
            }
        }
    }
    //清空购物车
    public void clear(){
        dataList = new ArrayList<>();
        cntMap = new HashMap<>();
        priceTotal = 0;
    }

}