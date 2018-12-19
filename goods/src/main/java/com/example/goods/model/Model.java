package com.example.goods.model;

import com.example.goods.bean.ShopBean;
import com.example.goods.callback.MyCallBack;

import java.util.Map;

public interface Model {
    void requestData(String url, Map<String,String> map, Class<ShopBean> clazz, MyCallBack myCallBack);
}
