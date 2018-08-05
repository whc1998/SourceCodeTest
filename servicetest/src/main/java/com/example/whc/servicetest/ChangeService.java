package com.example.whc.servicetest;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

public class ChangeService extends Service {

    private WallpaperManager wallpaperManager;
    int current=0;

    int[] wallpapers=new int[]{
        R.drawable.timg,R.drawable.timg1,R.drawable.timg2
    };

    @Override
    public void onCreate() {
        super.onCreate();
        wallpaperManager=WallpaperManager.getInstance(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (current>=3){
            current=0;
        }
        try {
            wallpaperManager.setResource(wallpapers[current++]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }
}
