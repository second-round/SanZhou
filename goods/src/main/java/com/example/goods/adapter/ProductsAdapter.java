package com.example.goods.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.goods.R;
import com.example.goods.bean.ShopBean;
import com.example.goods.view.CustomCounterView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean.ListBean> listBeans;

    public ProductsAdapter(Context context, List<ShopBean.DataBean.ListBean> listBeans) {
        this.context = context;
        this.listBeans = listBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.shop_car_adapter,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String url = listBeans.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(context).load(url).into(viewHolder.imageView);
        viewHolder.title.setText(listBeans.get(i).getTitle());
        viewHolder.price.setText(listBeans.get(i).getPrice()+"");
        viewHolder.customCounterView.setData(this,listBeans,i);
        viewHolder.box.setChecked(listBeans.get(i).isCheck());
        viewHolder.customCounterView.setOnCallBack(new CustomCounterView.CallBackListener() {
            @Override
            public void callback() {
                if (shopCallBackListener!=null){
                    shopCallBackListener.callBack();
                }
            }
        });
        viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listBeans.get(i).setCheck(b);
                if (shopCallBackListener!=null){
                    shopCallBackListener.callBack();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CustomCounterView customCounterView;
        TextView title,price;
        ImageView imageView;
        CheckBox box;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_product);
            title=itemView.findViewById(R.id.tv_product_title);
            price=itemView.findViewById(R.id.tv_product_price);
            box=itemView.findViewById(R.id.check_product);
            customCounterView=itemView.findViewById(R.id.custom_product_counter);
        }
    }

    public void selectOrRemoveAll(boolean isSelect){
        for (ShopBean.DataBean.ListBean listBean:listBeans){
            listBean.setCheck(isSelect);
        }
        notifyDataSetChanged();
    }

    private ShopCallBackListener shopCallBackListener;
    public interface ShopCallBackListener{
        void callBack();
    }

    public void setListener(ShopCallBackListener shopCallBackListener) {
        this.shopCallBackListener = shopCallBackListener;
    }
}
