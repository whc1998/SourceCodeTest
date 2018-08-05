package com.example.whc.newrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private List<Homeitem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        InitDate();
        adapter=new HomeAdapter(R.layout.itemview,list);
        View view=LayoutInflater.from(this).inflate(R.layout.headview,null);
        // 没有数据的时候默认显示该布局
//        adapter.setEmptyView(view);
        //开启动画(默认为渐显效果)
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //下拉加载
        adapter.setUpFetchEnable(true);
        adapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                Toast.makeText(MainActivity.this, "你触发了下拉加载", Toast.LENGTH_SHORT).show();
            }
        });
        //开始加载的位置
        adapter.setStartUpFetchPosition(2);
        //添加头部view
        adapter.addHeaderView(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        //item点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this,"click"+position,Toast.LENGTH_SHORT).show();
            }
        });
        //item长按事件
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, "onItemLongClick" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //item子控件点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void InitDate() {
        list=new ArrayList<>();
        Homeitem homeitem=new Homeitem();
        homeitem.setText("你好");
        homeitem.setUrl("http://ww4.sinaimg.cn/large/610dc034jw1f6ipaai7wgj20dw0kugp4.jpg");
        list.add(homeitem);
        Homeitem homeitem1=new Homeitem();
        homeitem1.setText("this is second item");
        homeitem1.setUrl("http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg");
        list.add(homeitem1);
        Homeitem homeitem2=new Homeitem();
        homeitem2.setText("this is third item");
        homeitem2.setUrl("http://ww3.sinaimg.cn/large/610dc034jw1f689lmaf7qj20u00u00v7.jpg");
        list.add(homeitem2);
        Homeitem homeitem3=new Homeitem();
        homeitem3.setText("this is fourth item");
        homeitem3.setUrl("http://ww3.sinaimg.cn/large/610dc034jw1f6aipo68yvj20qo0qoaee.jpg");
        list.add(homeitem3);
        Homeitem homeitem4=new Homeitem();
        homeitem4.setText("this is fifth item");
        homeitem4.setUrl("http://ww4.sinaimg.cn/large/610dc034jw1f6f5ktcyk0j20u011hacg.jpg");
        list.add(homeitem4);
        Homeitem homeitem5=new Homeitem();
        homeitem5.setText("this is fifth item");
        homeitem5.setUrl("http://ww1.sinaimg.cn/large/610dc034jw1f6e1f1qmg3j20u00u0djp.jpg");
        list.add(homeitem5);
        Homeitem homeitem6=new Homeitem();
        homeitem6.setText("this is fifth item");
        homeitem6.setUrl("http://ww3.sinaimg.cn/large/610dc034jw1f6gcxc1t7vj20hs0hsgo1.jpg");
        list.add(homeitem6);
        Homeitem homeitem7=new Homeitem();
        homeitem7.setText("this is fifth item");
        homeitem7.setUrl("http://ww3.sinaimg.cn/large/c85e4a5cjw1f671i8gt1rj20vy0vydsz.jpg");
        list.add(homeitem7);
    }
}
