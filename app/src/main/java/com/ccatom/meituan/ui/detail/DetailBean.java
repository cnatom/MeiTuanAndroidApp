package com.ccatom.meituan.ui.detail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailBean {

    @SerializedName("data")
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("announce")
        private String announce;
        @SerializedName("list")
        private List<ListBean> list;

        public String getAnnounce() {
            return announce;
        }

        public void setAnnounce(String announce) {
            this.announce = announce;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            @SerializedName("image")
            private String image;
            @SerializedName("lable")
            private String lable;
            @SerializedName("name")
            private String name;
            @SerializedName("price")
            private String price;
            @SerializedName("subTitle")
            private String subTitle;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }
        }
    }
}
