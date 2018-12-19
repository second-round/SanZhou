package com.example.goods.presenter;

import com.example.goods.bean.ShopBean;

import java.util.Map;

public interface Persenter {
    void startRequest(String urlGetShopCarInfo, Map<String,String> map, Class<ShopBean> shopBeanClass);
}
