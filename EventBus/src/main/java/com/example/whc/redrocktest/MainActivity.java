package com.example.whc.redrocktest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册成为订阅者
        EventBus.getDefault().register(this);
        Init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 100,sticky = true)
    public void getMessage(MessageEvent event){
        textView.setText(event.getMessage());
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void Init(){
        textView= (TextView) findViewById(R.id.tv_text);
        button= (Button) findViewById(R.id.secondActivityBtn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);
    }
}
