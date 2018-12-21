package com.example.sanzhou;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sanzhou.adapter.XrecycleAdapter;
import com.example.sanzhou.bean.GoodsBean;
import com.example.sanzhou.persenter.PersenterImpl;
import com.example.sanzhou.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView ,View.OnClickListener {
    private EditText editText;
    private XRecyclerView xRecyclerView;
    private ImageView huan,search;
    private PersenterImpl persenter;
    private String path="http://www.zhaoapi.cn/product/searchProducts?keywords=";
    private int page;
    private boolean isLinear=true;
    private final int COUNT=2;
    private XrecycleAdapter adapter;
    private List<GoodsBean.DataBean> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        page=1;
        editText=findViewById(R.id.edit);
        xRecyclerView=findViewById(R.id.xrecycle);
        search=findViewById(R.id.search);
        adapter=new XrecycleAdapter(MainActivity.this,isLinear);
        huan=findViewById(R.id.image_huan);
        persenter=new PersenterImpl(this);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);
        search.setOnClickListener(this);
        huan.setOnClickListener(this);
        xRecyclerView.setAdapter(adapter);
        linear();
//        grid();
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                load();
            }

            @Override
            public void onLoadMore() {
                load();
            }
        });

    }

    private void dong() {
        adapter.setOnItemLongClickl(new XrecycleAdapter.onItemLongClick() {
            @Override
            public void onitemclick(final View view, final int position) {
                list1 = adapter.getList();
                ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(3000);
                animatorSet.playTogether( alpha);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        list1.remove(position);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1.0f);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                });
            }
        });
    }

    private void load() {
        String keywords = editText.getText().toString();
        Map<String,String> map=new HashMap<>();
        map.put("keywords",keywords);
        map.put("page",page+"");
        persenter.sendData(path+keywords+"&page="+page,map,GoodsBean.class);
    }

    private void grid() {
        GridLayoutManager manager=new GridLayoutManager(MainActivity.this,COUNT);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        adapter=new XrecycleAdapter(MainActivity.this,isLinear);
        xRecyclerView.setAdapter(adapter);
        dong();
    }

    private void linear() {
        LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        adapter=new XrecycleAdapter(MainActivity.this,isLinear);
        xRecyclerView.setAdapter(adapter);
        dong();
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.search:
                page=1;
                load();
                break;
            case R.id.image_huan:
                isLinear=!isLinear;
                final List<GoodsBean.DataBean> list = adapter.getList();
                if (isLinear){
                    linear();
                }else {
                    grid();
                }
                adapter.setList(list);
                adapter.setOnItemLongClickl(new XrecycleAdapter.onItemLongClick() {
                    @Override
                    public void onitemclick(final View view, final int position) {
                        list1 = adapter.getList();
                        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.setDuration(3000);
                        animatorSet.playTogether( alpha);
                        animatorSet.start();
                        animatorSet.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationCancel(Animator animation) {
                                super.onAnimationCancel(animation);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                list1.remove(position);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1.0f);
                            }

                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                            }
                        });
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void setData(Object o) {
        GoodsBean goodsBean= (GoodsBean) o;
        if (page==1){
            adapter.setList(goodsBean.getData());
        }else {
            adapter.addList(goodsBean.getData());
        }
        page++;
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
    }
}
