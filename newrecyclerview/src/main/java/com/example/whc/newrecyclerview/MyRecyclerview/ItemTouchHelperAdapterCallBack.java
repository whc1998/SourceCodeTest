package com.example.whc.newrecyclerview.MyRecyclerview;

/**
 * Created by WSY on 2018/4/28.
 */

public interface ItemTouchHelperAdapterCallBack {

    boolean onItemMove(int fromPosition,int toPosition);

    void onItemSwiped(int position);

}
