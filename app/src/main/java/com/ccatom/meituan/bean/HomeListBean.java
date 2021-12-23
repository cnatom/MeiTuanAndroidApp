package com.ccatom.meituan.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeListBean {

    @SerializedName("data")
    private List<DataBean> data;
    @SerializedName("msg")
    private String msg;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        @SerializedName("deliver")
        private String deliver;
        @SerializedName("deliverTime")
        private String deliverTime;
        @SerializedName("detailUrl")
        private String detailUrl;
        @SerializedName("id")
        private int id;
        @SerializedName("image")
        private String image;
        @SerializedName("lable")
        private String lable;
        @SerializedName("sale")
        private String sale;
        @SerializedName("title")
        private String title;

        public String getDeliver() {
            return deliver;
        }

        public void setDeliver(String deliver) {
            this.deliver = deliver;
        }

        public String getDeliverTime() {
            return deliverTime;
        }

        public void setDeliverTime(String deliverTime) {
            this.deliverTime = deliverTime;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLable() {
            return lable;
        }

        public void setLable(String lable) {
            this.lable = lable;
        }

        public String getSale() {
            return sale;
        }

        public void setSale(String sale) {
            this.sale = sale;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
