package com.example.sanzhou.model;

import com.example.sanzhou.utils.MyCallBack;

import java.util.Map;

public interface Model {
    void sendData(String s, Map<String, String> map, Class clazz, MyCallBack myCallBack);
}
