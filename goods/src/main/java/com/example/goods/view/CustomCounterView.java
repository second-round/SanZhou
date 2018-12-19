package com.example.goods.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.goods.R;
import com.example.goods.adapter.ProductsAdapter;
import com.example.goods.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class CustomCounterView extends RelativeLayout implements View.OnClickListener {
    private int num;
    private Context context;
    private EditText editText;
    public CustomCounterView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        View view=View.inflate(context, R.layout.shop_car_price_layout,null);
        ImageView imageView=view.findViewById(R.id.add_car);
        ImageView imageView1=view.findViewById(R.id.jian_car);
        editText=view.findViewById(R.id.edit_shop_car);
        imageView.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        addView(view);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //改变数量
                String nums = editText.getText().toString();
                if (listener!=null){
                    int i3 = Integer.parseInt(nums);
                    num=i3;
                    listBeans.get(position).setNum(i3);
                    listener.callback();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public CustomCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_car:
                num++;
                editText.setText(num+"");
                listBeans.get(position).setNum(num);
                listener.callback();
                productsAdapter.notifyItemChanged(position);
                break;
            case R.id.jian_car:
                if (num>1){
                    num--;
                }else {
                    Toast.makeText(context,"我是有底线的啊",Toast.LENGTH_LONG).show();
                }
                editText.setText(num+"");
                listBeans.get(position).setNum(num);
                listener.callback();
                productsAdapter.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }
    private List<ShopBean.DataBean.ListBean> listBeans=new ArrayList<>();

    private int position;
    private ProductsAdapter productsAdapter;
    public void setData(ProductsAdapter productsAdapter,List<ShopBean.DataBean.ListBean> listBeans,int i){
        this.listBeans=listBeans;
        this.productsAdapter=productsAdapter;
        position=i;
        num=listBeans.get(i).getNum();
        editText.setText(num+"");
    }
    private CallBackListener listener;
    public interface CallBackListener{
        void callback();
    }
    public void setOnCallBack(CallBackListener listener){
        this.listener=listener;
    }


}
