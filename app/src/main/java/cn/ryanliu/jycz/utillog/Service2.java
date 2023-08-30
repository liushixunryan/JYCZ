/*
 * Original Copyright 2015 Mars Kwok
 * Modified work Copyright (c) 2020, weishu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.ryanliu.jycz.utillog;

import static me.weishu.leoric.LogUtil.leoricLog;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;

import cn.ryanliu.jycz.R;

public class Service2 extends Service{
    private final static int NOTIFY_ID = 100;
    Notification notification;
    NotificationManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        leoricLog("appService2 onCreate");
//        saveRuntimeLog("appService2 onCreate");
//        showForegroundNotification(this,LAUNCHER_NAME);

//        startService(new Intent(Service2.this,Service1.class));
//        bindService(new Intent(Service2.this,Service1.class),connection, Context.BIND_ABOVE_CLIENT);

//        MyApplication.startSocketService(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
//        saveRuntimeLog("Service2 onBind");
        //此处不返回不会调用onServiceConnected
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        leoricLog("Service2 onStartCommand");
//        saveRuntimeLog("Service2 onStartCommand");
//        showNotification();
//        startForeground(NOTIFY_ID,notification);
        return Service.START_NOT_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        leoricLog("Service2 onDestroy");
//        saveRuntimeLog("Service2 onDestroy");
    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
//            saveRuntimeLog("Service2与Service1断连");
            startService(new Intent(Service2.this,Service1.class));
            bindService(new Intent(Service2.this,Service1.class),connection, Context.BIND_ABOVE_CLIENT);
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            saveRuntimeLog("Service2与Service1连接");
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
//            saveRuntimeLog("Service2 binderDied");
        }
    };

    private void showNotification() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String className = "com.zwl.moduletest.activity.TestBaseActivity";
        String packageName = AppUtils.getAppPackageName();
        ComponentName component = new ComponentName(packageName, className);
        Intent explicitIntent =new Intent();
        explicitIntent.setComponent(component);
//        Intent hangIntent = new Intent(this, MainActivity.class);

        PendingIntent hangPendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hangPendingIntent = PendingIntent.getActivity(this, 1001, explicitIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        }else {
            hangPendingIntent = PendingIntent.getActivity(this, 1001, explicitIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        String CHANNEL_ID = "your_custom_id";//应用频道Id唯一值， 长度若太长可能会被截断，
        String CHANNEL_NAME = "your_custom_name";//最长40个字符，太长会被截断
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("appService2通知Title")
                .setContentText("appService2通知Content")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(hangPendingIntent)
                .setAutoCancel(true)
                .build();

        //Android 8.0 以上需包添加渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
        }

        manager.notify(NOTIFY_ID, notification);
    }
}
