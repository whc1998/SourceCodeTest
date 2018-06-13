package com.example.whc.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static String TAG="MYWHC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * String  看电影
         */
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.create(new OnSubscrible<String>() {
                    @Override
                    public void call(Subscrible<? super String> subscrible) {
                        subscrible.onNext("男生： 走，看电影去");
                    }
                }).subscrible(new Subscrible<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.i(TAG,s);
                        Log.i(TAG,"女生：可以，那就一起看电影");
                    }
                });
            }
        });

        /**
         * string 看电影
         * bitmap1 旅游
         *
         */
        findViewById(R.id.travel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.create(new OnSubscrible<String>() {
                    @Override
                    public void call(Subscrible<? super String> subscrible) {
                        subscrible.onNext("男生; 看电影");
                        Log.i(TAG, "线程："+Thread.currentThread().getName());
                    }
                }).map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        Log.i(TAG, s);
                        Log.i(TAG, "老婆： 不愿意和你旅游");
                        return BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                    }
                }).subscribOnIo().subscrible(new Subscrible<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        Log.i(TAG, "线程："+Thread.currentThread().getName());
                        Log.i(TAG,"bitmap"+bitmap);
                        Log.i(TAG,"旅游的女生：走，旅游去");
                    }
                });
            }
        });

    }
}
