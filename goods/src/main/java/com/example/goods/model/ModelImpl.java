package com.example.goods.model;

import com.example.goods.bean.ShopBean;
import com.example.goods.callback.MyCallBack;
import com.example.goods.okhttp.ICallBack;
import com.example.goods.okhttp.OKHttpUtils;

import java.util.Map;

public class ModelImpl implements Model {

    @Override
    public void requestData(String url, Map<String, String> map, Class<ShopBean> clazz, final MyCallBack myCallBack) {
        OKHttpUtils.getInstance().postEnqueue(url, map, clazz, new ICallBack() {
            @Override
            public void success(Object oj) {
                myCallBack.success(oj);
            }

            @Override
            public void failed(Exception e) {
                myCallBack.failed(e);
            }
        });
    }
}
