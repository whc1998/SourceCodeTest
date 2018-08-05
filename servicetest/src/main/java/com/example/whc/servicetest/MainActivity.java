package com.example.whc.servicetest;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    //alarm
    private AlarmManager aManager;
    private Calendar currentTime=Calendar.getInstance();
    private Intent intent;
    private PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        //设置闹钟
        findViewById(R.id.bt_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Intent intent=new Intent(MainActivity.this,AlarmActivity.class);
                        PendingIntent pendingIntent= PendingIntent.getActivity(MainActivity.this,0,intent,0);
                        Calendar c=Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());
                        c.set(Calendar.HOUR,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        aManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                        Toast.makeText(MainActivity.this,"setting success ",Toast.LENGTH_SHORT).show();
                    }
                },currentTime.get(Calendar.HOUR_OF_DAY),currentTime.get(Calendar.MINUTE),false).show();
            }
        });
        //开启设置壁纸
        findViewById(R.id.bt_changeBitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //每隔5秒执行一次
                aManager.setRepeating(AlarmManager.RTC_WAKEUP,0,500,pi);
                Toast.makeText(MainActivity.this, "启动成功", Toast.LENGTH_SHORT).show();
            }
        });

        //关闭定时更换壁纸
        findViewById(R.id.bt_changeBitmapStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aManager.cancel(pi);
            }
        });

    }

    private void Init() {

        aManager= (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        intent=new Intent(MainActivity.this,ChangeService.class);
        pi=PendingIntent.getService(MainActivity.this,0,intent,0);
    }
}
