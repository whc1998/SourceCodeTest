package com.example.whc.wechatlog;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *
 *private Button notifyButton;
 private Button setAuthButton;

 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);

 initView();
 // 开启服务
 startNotificationListenService();
 }

 private void initView() {
 notifyButton = (Button) findViewById(R.id.button_notify);
 notifyButton.setOnClickListener(this);
 setAuthButton = (Button) findViewById(R.id.button_setauth);
 setAuthButton.setOnClickListener(this);
 }

 private void startNotificationListenService() {
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
 Intent intent = new Intent(MainActivity.this,
 NotificationService.class);
 startService(intent);
 } else {
 Toast.makeText(MainActivity.this, "手机的系统不支持此功能", Toast.LENGTH_SHORT)
 .show();
 }
 }

 @Override
 public void onClick(View v) {
 switch (v.getId()) {
 case R.id.button_notify:
 showNotify();
 break;
 case R.id.button_setauth:
 setAuth();
 break;
 default:
 break;
 }
 }

 @SuppressLint("NewApi")
 @SuppressWarnings("deprecation")
 private void showNotify() {
 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
 Notification n;
 if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
 // android 3.0之前
 n = new Notification(R.mipmap.ic_launcher, "title",
 System.currentTimeMillis());
 } else {
 // android 3.0之后
 n = new Notification.Builder(MainActivity.this)
 .setSmallIcon(R.mipmap.ic_launcher).setTicker("title")
 .setContentTitle("content title")
 .setContentText("content text").setSubText("sub text")
 .setWhen(System.currentTimeMillis()).build();
 }
 manager.notify(0, n);
 }

 private void setAuth() {
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
 Intent intent = new Intent(
 "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
 startActivity(intent);
 } else {
 Toast.makeText(MainActivity.this, "手机的系统不支持此功能", Toast.LENGTH_SHORT)
 .show();
 }
 }

 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.whc.wechatlog.test.NotificationMonitor;


public class MainActivity extends Activity {

    private static final String TAG = "SevenNLS";
    private static final String TAG_PRE = "["+MainActivity.class.getSimpleName()+"] ";
    private static final int EVENT_SHOW_CREATE_NOS = 0;
    private static final int EVENT_LIST_CURRENT_NOS = 1;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private boolean isEnabledNLS = false;
    private TextView mTextView;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_SHOW_CREATE_NOS:
                    showCreateNotification();
                    break;
                case EVENT_LIST_CURRENT_NOS:
                    listCurrentNotification();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testview);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isEnabledNLS = isEnabled();
        logNLS("isEnabledNLS = " + isEnabledNLS);
        if (!isEnabledNLS) {
            showConfirmDialog();
        }
    }

    public void buttonOnClicked(View view) {
        mTextView.setTextColor(Color.BLACK);
        switch (view.getId()) {
            case R.id.btnCreateNotify:
                logNLS("Create notifications...");
                createNotification(this);
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_SHOW_CREATE_NOS), 50);
                break;
            case R.id.btnClearLastNotify:
                logNLS("Clear Last notification...");
                clearLastNotification();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_LIST_CURRENT_NOS), 50);
                break;
            case R.id.btnClearAllNotify:
                logNLS("Clear All notifications...");
                clearAllNotifications();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_LIST_CURRENT_NOS), 50);
                break;
            case R.id.btnListNotify:
                logNLS("List notifications...");
                listCurrentNotification();
                break;
            case R.id.btnEnableUnEnableNotify:
                logNLS("Enable/UnEnable notification...");
                openNotificationAccess();
                break;
            default:
                break;
        }
    }

    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder ncBuilder = new NotificationCompat.Builder(context);
        ncBuilder.setContentTitle("My Notification");
        ncBuilder.setContentText("Notification Listener Service Example");
        ncBuilder.setTicker("Notification Listener Service Example");
        ncBuilder.setSmallIcon(R.mipmap.ic_launcher);
        ncBuilder.setAutoCancel(true);
        manager.notify((int)System.currentTimeMillis(),ncBuilder.build());
    }

    private void cancelNotification(Context context, boolean isCancelAll) {
        Intent intent = new Intent();
        intent.setAction(NotificationMonitor.ACTION_NLS_CONTROL);
        if (isCancelAll) {
            intent.putExtra("command", "cancel_all");
        }else {
            intent.putExtra("command", "cancel_last");
        }
        context.sendBroadcast(intent);
    }

    private String getCurrentNotificationString() {
        String listNos = "";
        StatusBarNotification[] currentNos = NotificationMonitor.getCurrentNotifications();
        if (currentNos != null) {
            for (int i = 0; i < currentNos.length; i++) {
                listNos = i +" " + currentNos[i].getPackageName() + "\n" + listNos;
            }
        }
        return listNos;
    }

    private void listCurrentNotification() {
        String result = "";
        if (isEnabledNLS) {
            if (NotificationMonitor.getCurrentNotifications() == null) {
                logNLS("mCurrentNotifications.get(0) is null");
                return;
            }
            int n = NotificationMonitor.mCurrentNotificationsCounts;
            if (n == 0) {
                result = getResources().getString(R.string.active_notification_count_zero);
            }else {
                result = String.format(getResources().getQuantityString(R.plurals.active_notification_count_nonzero, n, n));
            }
            result = result + "\n" + getCurrentNotificationString();
            mTextView.setText(result);
        }else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    private void clearLastNotification() {
        if (isEnabledNLS) {
            cancelNotification(this,false);
        }else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    private void clearAllNotifications() {
        if (isEnabledNLS) {
            cancelNotification(this,true);
        }else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    private void showCreateNotification() {
        if (NotificationMonitor.mPostedNotification != null) {
            String result = NotificationMonitor.mPostedNotification.getPackageName()+"\n"
                    + NotificationMonitor.mPostedNotification.getTag()+"\n"
                    + NotificationMonitor.mPostedNotification.getId()+"\n"+"\n"
                    + mTextView.getText();
            result = "Create notification:"+"\n"+result;
            mTextView.setText(result);
        }
    }

    private void openNotificationAccess() {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    private void showConfirmDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Please enable NotificationMonitor access")
                .setTitle("Notification Access")
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openNotificationAccess();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // do nothing
                            }
                        })
                .create().show();
    }

    private void logNLS(Object object) {
        Log.i(TAG, TAG_PRE+object);
    }
}

