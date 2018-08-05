package com.example.whc.servicetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class AlarmActivity extends Activity {

    private MediaPlayer alarmMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmMusic = MediaPlayer.create(this, null);
        alarmMusic.setLooping(true);
        alarmMusic.start();
        new AlertDialog.Builder(this).setTitle("闹钟")
                .setMessage("闹钟响了")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alarmMusic.stop();
                        finish();
                    }
                }).show();
    }
}
