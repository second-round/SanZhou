package com.example.goods.presenter;

import com.example.goods.activity.IView;
import com.example.goods.bean.ShopBean;
import com.example.goods.callback.MyCallBack;
import com.example.goods.model.ModelImpl;

import java.util.Map;

public class PersenterImpl implements Persenter{
    private IView iView;
    private ModelImpl model;

    public PersenterImpl(IView iView) {
        this.iView = iView;
        model=new ModelImpl();
    }

    @Override
    public void startRequest(String url, Map<String, String> map, Class<ShopBean> clazz) {
        model.requestData(url,map,clazz, new MyCallBack() {
            @Override
            public void success(Object data) {
                iView.setData(data);
            }

            @Override
            public void failed(Exception e) {

            }
        });
    }
}
