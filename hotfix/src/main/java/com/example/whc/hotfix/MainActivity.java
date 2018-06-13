package com.example.whc.hotfix;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whc.hotfix.FixUtil.FixDexUtils;
import com.example.whc.hotfix.FixUtil.MyConstants;
import com.example.whc.hotfix.Test.MyTestClass;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test按钮点击事件
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTestClass myTestClass = new MyTestClass();
                myTestClass.testfix(MainActivity.this);
            }
        });
        //fix按钮点击事件
        findViewById(R.id.fix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fixBug();
            }
        });

    }

    //    /data/user/0/com.example.whc.hotfix/app_odex/MyTestClass1.dex
    // /data/user/0/com.example.whc.hotfix/app_odex/MyTestClass1.dex
    private void fixBug() {
        //目录 data/data/packageName/odex
        File fileDir = getDir(MyConstants.DEX_DIR, Context.MODE_PRIVATE);
        //往该目录下面放置我们修复好的dex文件
        String name = "MyTestClass1.dex";
        String filePath = fileDir.getAbsolutePath() + File.separator + name;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        //把下载好的在sd卡里面的修复了的classes2.dex搬到应用目录filePath
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + name);
            os = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }

            File f = new File(filePath);
            if (f.exists()) {
                Toast.makeText(this, "dex 重写成功", Toast.LENGTH_SHORT).show();
            }
            //热修复
            FixDexUtils.loadFixedDex(this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
