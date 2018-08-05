package com.example.whc.newrecyclerview.MyRecyclerview;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whc.newrecyclerview.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by WSY on 2018/4/28.
 */

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.MyHolder> implements ItemTouchHelperAdapterCallBack {

    private List<String> list;
    private StartDragListener dragListener;

    public SecondAdapter(List<String> list,StartDragListener listener) {
        this.list = list;
        this.dragListener=listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.seconditem,parent,false);
        return new MyHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.textView.setText(list.get(position));
        holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               dragListener.onStartDrag(holder);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //1 刷新数据--交换两个item的数据
        Collections.swap(list,fromPosition,toPosition);
        //2 刷新adapter
        notifyItemMoved(fromPosition,toPosition);
        return false;
    }

    @Override
    public void onItemSwiped(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.imageview);
            textView= (TextView) itemView.findViewById(R.id.textview);
        }
    }

}
