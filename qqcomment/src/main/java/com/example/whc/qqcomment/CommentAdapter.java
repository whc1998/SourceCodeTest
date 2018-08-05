package com.example.whc.qqcomment;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


public class CommentAdapter extends BaseAdapter {

    private Context context;

    private List<PersonItem> list;

    public CommentAdapter(List<PersonItem> dataList,Context context){
        this.list=dataList;
        this.context=context;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null&&list.size()!=0)
            ret = list.size();
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //其实评论一般都是文字，高级点的带有图片评论，光文字的话复用不复用就没什么大区别了
        View view = null;
        if(convertView!=null)
        {
            view = convertView;
        }
        else
        {
            view = LayoutInflater.from(context).inflate(R.layout.itemlayout, parent,false);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder==null)
        {
            holder = new ViewHolder();
            holder.txt_comment = (TextView) view.findViewById(R.id.txt_comment);

            view.setTag(holder);
        }
        //给相应位置的文字赋内容
        if (list != null && list.size()!=0) {
            StringBuilder actionText = new StringBuilder();

            //谁回复
            actionText.append("<a style=\"text-decoration:none;\" href='name' ><font color='#1468a3'>"
                    + list.get(position).getName()  + "</font> </a>");

            // 回复谁，被回复的人可能不存在。
            if(list.get(position).getToName()!=null&&list.get(position).getToName().length()>0) {
                actionText.append("回复");
                actionText.append("<font color='#1468a3'><a style=\"text-decoration:none;\" href='toName'>"
                        + list.get(position).getToName() + " " + " </a></font>");
            }
            // 内容
            actionText.append("<font color='#484848'><a style=\"text-decoration:none;\" href='content'>"
                    + ":" + list.get(position).getContent() + " " + " </a></font>");

            holder.txt_comment.setText(Html.fromHtml(actionText.toString()));
            holder.txt_comment.setMovementMethod(LinkMovementMethod
                    .getInstance());//不设置 没有点击事件
            CharSequence text = holder.txt_comment.getText();
            int ends = text.length();
            Spannable spannable = (Spannable) holder.txt_comment.getText();
            URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
            SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
            stylesBuilder.clearSpans();

            for (URLSpan url : urlspan) {
                FeedTextViewURLSpan myURLSpan = new FeedTextViewURLSpan(url.getURL(),
                        context,list.get(position).getName(),list.get(position).getToName(),list.get(position).getContent());
                stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                        spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            holder.txt_comment.setText(stylesBuilder);
            holder.txt_comment.setFocusable(false);
            holder.txt_comment.setClickable(false);
            holder.txt_comment.setLongClickable(false);

        }

        return view;
    }

    class ViewHolder{
        TextView txt_comment;
    }

}
