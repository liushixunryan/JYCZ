package cn.ryanliu.jycz.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 日志接收储存广播
 */
public class LogTaskReceiver extends BroadcastReceiver {
    //日志文件监测action
    public static String MONITOR_LOG_SIZE_ACTION = "MONITOR_LOG_SIZE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (MONITOR_LOG_SIZE_ACTION.equals(action)) {
            LogUtils.getInstance().updateLogFileSize();

        }
    }
}