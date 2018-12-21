package com.example.sanzhou.persenter;

import com.example.sanzhou.bean.GoodsBean;
import com.example.sanzhou.model.ModelImpl;
import com.example.sanzhou.utils.MyCallBack;
import com.example.sanzhou.view.IView;

import java.util.Map;

public class PersenterImpl implements Persenter{
    private IView iView;
    private ModelImpl model;

    public PersenterImpl(IView iView) {
        this.iView = iView;
        model=new ModelImpl();
    }

    @Override
    public void sendData(String s, Map<String, String> map, Class clazz) {
        model.sendData(s, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.setData(o);
            }
        });
    }
}
