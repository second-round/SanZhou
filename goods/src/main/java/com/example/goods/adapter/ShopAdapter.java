package com.example.goods.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.goods.R;
import com.example.goods.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<ShopBean.DataBean> list=new ArrayList<>();
    private Context context;

    public ShopAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ShopBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.shop_seller_car_adapter,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(list.get(i).getSellerName());
        final ProductsAdapter productsAdapter=new ProductsAdapter(context,list.get(i).getList());
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(manager);
        viewHolder.recyclerView.setAdapter(productsAdapter);
        viewHolder.checkBox.setChecked(list.get(i).isCheck());
        productsAdapter.setListener(new ProductsAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                if (shopCallBackListener!=null){
                    shopCallBackListener.callBack(list);
                }
                List<ShopBean.DataBean.ListBean> listBeans=list.get(i).getList();
                boolean ischeck=true;
                for (ShopBean.DataBean.ListBean bean:listBeans) {
                    if (!bean.isCheck()){
                        ischeck=false;
                        break;
                    }
                }
                viewHolder.checkBox.setChecked(ischeck);
                list.get(i).setCheck(ischeck);
            }
        });
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).setCheck(viewHolder.checkBox.isChecked());
                productsAdapter.selectOrRemoveAll(viewHolder.checkBox.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView name;
        CheckBox checkBox;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.recycler_shop);
            checkBox=itemView.findViewById(R.id.check_shop);
            name=itemView.findViewById(R.id.tv_shop);

        }
    }
    private ShopCallBackListener shopCallBackListener;

    public void setShopCallBackListener(ShopCallBackListener shopCallBackListener) {
        this.shopCallBackListener = shopCallBackListener;
    }

    public interface ShopCallBackListener{
        void callBack(List<ShopBean.DataBean> list);
    }


}
