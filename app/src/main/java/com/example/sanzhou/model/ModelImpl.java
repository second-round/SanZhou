package com.example.sanzhou.model;

import com.example.sanzhou.utils.ICallBack;
import com.example.sanzhou.utils.MyCallBack;
import com.example.sanzhou.utils.OKHttpUtils;

import java.util.Map;

public class ModelImpl implements Model{
    @Override
    public void sendData(String s, Map<String, String> map, Class clazz, final MyCallBack myCallBack) {
        OKHttpUtils.getInstence().getData(s, map, clazz, new ICallBack() {
            @Override
            public void success(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void fail(Exception e) {

            }
        });
    }
}
