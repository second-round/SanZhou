package com.example.sanzhou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sanzhou.R;
import com.example.sanzhou.bean.GoodsBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XrecycleAdapter extends RecyclerView.Adapter<XrecycleAdapter.ViewHolder> {
    private List<GoodsBean.DataBean> list;
    private Context context;
    private boolean isLinear=true;
    public XrecycleAdapter(Context context,boolean isLinear) {
        this.context = context;
        list=new ArrayList<>();
        this.isLinear=isLinear;
    }

    public void setList(List<GoodsBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<GoodsBean.DataBean> list) {
        this.list .addAll(list);
        notifyDataSetChanged();
    }

    public List<GoodsBean.DataBean> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = null;
        if (isLinear){
            View view=View.inflate(context, R.layout.linear,null);
            viewHolder=new ViewHolder(view);
        }else {
            View view=View.inflate(context,R.layout.grid,null);
            viewHolder=new ViewHolder(view);
        }
        return viewHolder;
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        List<String> list = Arrays.asList(split);
        Glide.with(context).load(list.get(0)).into(viewHolder.imageView);
        viewHolder.textView.setText(this.list.get(i).getTitle());
        viewHolder.textView2.setText(this.list.get(i).getPrice()+"");
        viewHolder.textView3.setText(this.list.get(i).getSubhead()+"");
        viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickl!=null){
                    Toast.makeText(context,"aaa",Toast.LENGTH_LONG).show();
                    onItemLongClickl.onitemclick(v,i);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView,textView2,textView3;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.image1);
            textView2=itemView.findViewById(R.id.image2);
            textView3=itemView.findViewById(R.id.image3);

        }
    }
    public interface onItemLongClick{
        void onitemclick(View view,int position);
    }
    private onItemLongClick onItemLongClickl;

    public void setOnItemLongClickl(onItemLongClick onItemLongClickl) {
        this.onItemLongClickl = onItemLongClickl;
    }
}