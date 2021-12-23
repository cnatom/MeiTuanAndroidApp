# 原生Android仿美团App

## 简介

写的第一个原生Android项目。

参考资料《第一行代码（第2版）》以及各种搜索引擎。

由于以前是写Flutter的，刚开始学原生Android，所以有些地方写的不好，理解万岁。

## 示例截图

|                             主页                             | 详情页                                                       | 购物车                                                       | 订单二维码                                                   |
| :----------------------------------------------------------: | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="https://cdn.jsdelivr.net/gh/cnatom/images/images/meituan1.png" style="zoom: 20%;" /> | <img src="https://cdn.jsdelivr.net/gh/cnatom/images/images/meituan2.png" style="zoom:20%;" /> | <img src="https://cdn.jsdelivr.net/gh/cnatom/images/images/meituan3.png" style="zoom:20%;" /> | <img src="https://cdn.jsdelivr.net/gh/cnatom/images/images/meituan4.png" style="zoom:20%;" /> |



## 演示视频

[https://www.bilibili.com/video/BV1Nq4y1m7kK](https://www.bilibili.com/video/BV1Nq4y1m7kK)

## 项目说明

### 前端

原生Android、xml编写界面、Java编写逻辑

| 功能                           | 实现                       |
| ------------------------------ | -------------------------- |
| 店铺列表、菜品列表、购物车列表 | RecyclerView               |
| 图片加载                       | Picasso                    |
| 网络请求                       | RequestQueue+StringRequest |
| Json解析                       | Gson                       |
| 购物车弹窗                     | PopupWindow                |
| 实体类自动生成                 | GsonFormatPlus插件         |

### 后端

python语言、Flask框架

| 功能                 | 接口                                                       | 发送方法 | 返回格式 |
| -------------------- | ---------------------------------------------------------- | -------- | -------- |
| 获取店铺列表         | http://10.0.2.2:5000/home                                  | GET      | Json     |
| 获取菜品列表         | http://10.0.2.2:5000/detail/1 （1代表第1家店铺的菜品信息） | GET      | Json     |
| 发送订单，生成二维码 | http://10.0.2.2:5000/submit                                | POST     | Jso      |

> 使用qrcode库生成二维码。由于没有数据来源，所以数据全都是静态的。

## 使用步骤

### 1.下载后端项目

仓库链接：

[https://github.com/cnatom/MeiTuanAndroidAppServer](https://github.com/cnatom/MeiTuanAndroidAppServer)

或者在终端中执行：

```
git clone https://github.com/cnatom/MeiTuanAndroidAppServer.git
```

### 2.运行后端服务

将后端项目直接用pycharm打开，引入相关的依赖包，最后直接运行即可。

也可以用以下方式运行，app.py文件目录下运行终端命令：

```
flask run
```

### 3.下载本App项目

仓库链接：

[https://github.com/cnatom/MeiTuanAndroidApp](https://github.com/cnatom/MeiTuanAndroidApp)

或者在终端中执行：

```
git clone https://github.com/cnatom/MeiTuanAndroidApp.git
```

### 4.运行App

用`Android Studio`打开，运行即可。

