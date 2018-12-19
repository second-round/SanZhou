package com.example.goods.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.example.goods.Apis;
import com.example.goods.Contants;
import com.example.goods.R;
import com.example.goods.adapter.ShopAdapter;
import com.example.goods.bean.ShopBean;
import com.example.goods.presenter.PersenterImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MainActivity extends AppCompatActivity implements IView,View.OnClickListener {
    private PersenterImpl persenter;
    private RecyclerView recyclerView;
    private ShopAdapter adapter;
    private List<ShopBean.DataBean> list=new ArrayList<>();
    private TextView allPrice,sumPrice;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        persenter=new PersenterImpl(this);
        init();
        getData();
    }
    private void init() {
        recyclerView=findViewById(R.id.recyclerview);
        allPrice=findViewById(R.id.all_price);
        sumPrice=findViewById(R.id.sum_price_txt);
        checkBox=findViewById(R.id.iv_cricle);
        LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter=new ShopAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);
        checkBox.setOnClickListener(this);
        adapter.setShopCallBackListener(new ShopAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                double totalPrice=0;
                int num=0;
                int totalNum=0;
                for (int a=0;a<list.size();a++){
                    List<ShopBean.DataBean.ListBean> listBeans=list.get(a).getList();
                    for (int i=0;i<listBeans.size();i++){
                        totalNum=totalNum+listBeans.get(i).getNum();
                        if (listBeans.get(i).isCheck()){
                            totalPrice=totalPrice+(listBeans.get(i).getPrice()*listBeans.get(i).getNum());
                            num=num+listBeans.get(i).getNum();
                        }
                    }
                }
                if (num<totalNum){
                    checkBox.setChecked(false);
                }else {
                    checkBox.setChecked(true);
                }
                allPrice.setText("合计:"+totalPrice);
                sumPrice.setText("去结算("+num+")");
            }
        });
    }
    private void getData() {
        Map<String,String> map=new HashMap<>();
        map.put(Contants.MAP_KEY_GET_PRODUCE_UID,"71");
        persenter.startRequest(Apis.URL_GET_SHOP_CAR_INFO,map,ShopBean.class);
    }
    @Override
    public void setData(Object data) {
        ShopBean bean= (ShopBean) data;
        list=bean.getData();
        adapter.setList(list);
    }
    private void checkSeller(boolean checked) {
        double totalPrice=0;
        Toast.makeText(MainActivity.this,"aaa",Toast.LENGTH_LONG).show();
        int num=0;
        for (int a=0;a<list.size();a++){
            ShopBean.DataBean dataBean=list.get(a);
            dataBean.setCheck(checked);
            List<ShopBean.DataBean.ListBean> listBeans=list.get(a).getList();
            for (int i=0;i<listBeans.size();i++){
                listBeans.get(i).setCheck(checked);
                totalPrice=totalPrice+(listBeans.get(i).getPrice()*listBeans.get(i).getNum());
                num=num+listBeans.get(i).getNum();
            }
            if (checked){
                allPrice.setText("合计:"+totalPrice);
                sumPrice.setText("去结算("+num+")");
            }else {
                allPrice.setText("合计:");
                sumPrice.setText("去结算(0)");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_cricle:
                Toast.makeText(MainActivity.this,"666",Toast.LENGTH_LONG).show();
                checkSeller(checkBox.isChecked());
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}