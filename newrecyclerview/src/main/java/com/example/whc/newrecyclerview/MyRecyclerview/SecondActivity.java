package com.example.whc.newrecyclerview.MyRecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.whc.newrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WSY on 2018/4/28.
 */

public class SecondActivity extends AppCompatActivity implements StartDragListener {

    private RecyclerView recyclerView;
    private SecondAdapter adapter;
    private List<String> list;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        adapter=new SecondAdapter(list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback=new MyItemTouchCallback(adapter);
        itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void Init() {
        list=new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            list.add("this is "+i+"item");
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
