package com.example.whc.qqcomment;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

public class FeedTextViewURLSpan extends ClickableSpan {
    private String clickString;
    private Context context;
    // 回复人的名字
    private String name;
    // 被回复人的名字
    private String toName;
    // 评论内容
    private String content;

    public FeedTextViewURLSpan(String clickString, Context context, String name, String toName, String content) {
        this.clickString = clickString;
        this.context = context.getApplicationContext();
        this.name = name;
        this.toName = toName;
        this.content = content;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
        //给标记的部分 的文字 添加颜色
        if(clickString.equals("toName")){
            ds.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }else if(clickString.equals("name")){
            ds.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onClick(View widget) {
        // 根据文字的标记 来进行相应的 响应事件
        if (clickString.equals("toName")) {
            //可以再次进行跳转activity的操作
            Toast.makeText(context,"点击了"+toName,Toast.LENGTH_SHORT).show();
        } else if (clickString.equals("name")) {
            //可以再次进行跳转activity的操作
            Toast.makeText(context,"点击了"+name,Toast.LENGTH_SHORT).show();
        } else if(clickString.equals("content")){
            //可以再次进去回复评论的操作
//            Toast.makeText(context,"点击了"+content,Toast.LENGTH_SHORT).show();
        }

    }
}
