package com.example.whc.qqcomment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyDialog extends DialogFragment implements TextWatcher, View.OnClickListener {

    public SendListener sendListener;

    private Dialog dialog;
    private EditText commentText;
    private Button bt_comment;
    private String hintText;
    private Context context;

    @SuppressLint("ValidFragment")
    public MyDialog(Context context, String hintText, SendListener sendListener) {
        this.context = context.getApplicationContext();
        this.hintText = hintText;
        this.sendListener = sendListener;
    }

    public MyDialog() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.Comment_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View contentView = View.inflate(getActivity(), R.layout.commmentback, null);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.0f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        commentText = contentView.findViewById(R.id.comment_edit);
        bt_comment = contentView.findViewById(R.id.comment_bt);
        commentText.setHint(hintText);
        commentText.addTextChangedListener(this);
        commentText.setOnClickListener(this);
        bt_comment.setOnClickListener(this);
        return dialog;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s) && s.length() > 2) {
            bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
            Log.d("Test", "this is ui test");
        } else {
            bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            bt_comment.setEnabled(true);
            bt_comment.setTextColor(Color.BLACK);
        } else {
            bt_comment.setEnabled(false);
            bt_comment.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_bt:
                checkContent();
                break;
        }
    }

    private void checkContent() {
        String content = commentText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(context, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        sendListener.sendComment(content);
        dismiss();
    }

    public interface SendListener {
        void sendComment(String inputText);
    }

}
