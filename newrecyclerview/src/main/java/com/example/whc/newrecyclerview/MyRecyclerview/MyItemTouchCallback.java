package com.example.whc.newrecyclerview.MyRecyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by WSY on 2018/4/28.
 */

public class MyItemTouchCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapterCallBack adapterCallBack;

    public MyItemTouchCallback(ItemTouchHelperAdapterCallBack adapterCallBack) {
        this.adapterCallBack = adapterCallBack;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipFlages=ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags,swipFlages);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapterCallBack.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapterCallBack.onItemSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //侧滑缩放，透明度
        if (actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
            float alpha=1-Math.abs(dX)/viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);
            if (alpha<=0){
                viewHolder.itemView.setAlpha(1);
                viewHolder.itemView.setScaleX(1);
                viewHolder.itemView.setScaleY(1);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
