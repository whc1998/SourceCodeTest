package com.example.whc.wechatlog;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by WSY on 2018/5/2.
 */
@SuppressLint("NewApi")
public class NotificationService extends NotificationListenerService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("wanghang", "strat");
        return super.onStartCommand(intent, flags, startId);
    }

    // 有新的通知
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.e("wanghang", "get notify");
        Notification n = sbn.getNotification();
        if (n == null) {
            return;
        }
        // 标题和时间
        String title = "";
        if (n.tickerText != null) {
            title = n.tickerText.toString();
        }
        long when = n.when;
        // 其它的信息存在一个bundle中，此bundle在android4.3及之前是私有的，需要通过反射来获取；android4.3之后可以直接获取
        Bundle bundle = null;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // android 4.3
            try {
                Field field = Notification.class.getDeclaredField("extras");
                bundle = (Bundle) field.get(n);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // android 4.3之后
            bundle = n.extras;
        }
        // 内容标题、内容、副内容
        String contentTitle = bundle.getString(Notification.EXTRA_TITLE);
        if (contentTitle == null) {
            contentTitle = "";
        }
        String contentText = bundle.getString(Notification.EXTRA_TEXT);
        if (contentText == null) {
            contentText = "";
        }
        String contentSubtext = bundle.getString(Notification.EXTRA_SUB_TEXT);
        if (contentSubtext == null) {
            contentSubtext = "";
        }
        Log.e("wanghang", "notify msg: title=" + title + " ,when=" + when
                + " ,contentTitle=" + contentTitle + " ,contentText="
                + contentText + " ,contentSubtext=" + contentSubtext);
    }

    // 通知被删除了
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.e("wanghang", "delete notify");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
