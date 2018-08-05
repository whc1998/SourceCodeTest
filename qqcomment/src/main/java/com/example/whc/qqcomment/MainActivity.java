package com.example.whc.qqcomment;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<PersonItem> dataList;
    /* --------- 适配器------------*/
    private CommentAdapter adapter;

//    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        dataList = new ArrayList<>();
        InitData();

        adapter = new CommentAdapter(dataList, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PersonItem personItem=new PersonItem();
                personItem.setName("点击人");
                personItem.setToName(dataList.get(position).getName());
                new MyDialog(MainActivity.this,"回复 " + dataList.get(position).getName() + " 的评论:",new MyDialog.SendListener(){
                    @Override
                    public void sendComment(String inputText) {
                        personItem.setContent(inputText);
                        dataList.add(personItem);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                    }
                }).show(getFragmentManager(),"comment");
//                showReplyDialog(position);
            }
        });

    }

    private void InitData() {
        PersonItem personItem = new PersonItem();
        personItem.setName("白雪公主");
        personItem.setToName("小矮人");
        personItem.setContent("你好啊-");
        dataList.add(personItem);

        PersonItem personItem1 = new PersonItem();
        personItem1.setName("小矮人");
        personItem1.setToName("白雪公主");
        personItem1.setContent("白雪公主，早上好啊~");
        dataList.add(personItem1);

        PersonItem personItem2 = new PersonItem();
        personItem2.setName("王子");
        personItem2.setToName("");
        personItem2.setContent("这条说说很有道理的样子啊~");
        dataList.add(personItem2);

        PersonItem personItem3 = new PersonItem();
        personItem3.setName("国王");
        personItem3.setToName("");
        personItem3.setContent("我很喜欢这条说说~");
        dataList.add(personItem3);

        PersonItem personItem4 = new PersonItem();
        personItem4.setName("白雪公主");
        personItem4.setToName("王子");
        personItem4.setContent("你也是XX的朋友啊？");
        dataList.add(personItem4);
    }
/**
    private void showReplyDialog(final int position) {
        dialog = new Dialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.commmentback, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        final EditText commentText = commentView.findViewById(R.id.comment_edit);
        final Button bt_comment = commentView.findViewById(R.id.comment_bt);
        commentText.setHint("回复 " + dataList.get(position).getName() + " 的评论:");
        dialog.setContentView(commentView);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        //设置大小
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width =WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {

                    dialog.dismiss();
                    PersonItem personItem = new PersonItem();
                    personItem.setName("点击人");
                    personItem.setToName(dataList.get(position).getName());
                    personItem.setContent(replyContent);
                    dataList.add(personItem);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }
*/
}
