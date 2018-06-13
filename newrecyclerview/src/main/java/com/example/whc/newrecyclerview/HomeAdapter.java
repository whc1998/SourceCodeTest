package com.example.whc.newrecyclerview;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by WSY on 2018/3/28.
 */

public class HomeAdapter extends BaseQuickAdapter<Homeitem,BaseViewHolder> {

    public HomeAdapter(int layoutResId, @Nullable List<Homeitem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Homeitem item) {
        helper.setText(R.id.textview,item.getText())
        .addOnClickListener(R.id.textview);
        Glide.with(mContext)
                .load(item.getUrl())
                .crossFade()
                .error(R.mipmap.ic_launcher_round)
                .into((ImageView) helper.getView(R.id.imagehead));
    }
}
