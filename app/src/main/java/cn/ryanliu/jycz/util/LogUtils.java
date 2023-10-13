package cn.ryanliu.jycz.util;

import static android.content.Context.ALARM_SERVICE;

import static cn.ryanliu.jycz.util.LogTaskReceiver.MONITOR_LOG_SIZE_ACTION;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.ryanliu.jycz.basic.BaseApplication;

/**
 * * 日志相关类:默认是测试环境<br>
 * <b>支持：存储Log日志文件到本地。发送Log日志信息到服务器</b>
 * <p>
 * Created by shixun on 2023/7/26.
 */

public class LogUtils {
    private static final String TAG = LogUtils.class.getSimpleName();
    private static final String PACKAGE_NAME = "com.xql.iiscreen";
    private final static String LOG_FILE_NAME_PATTERN = "yyyy-MM-dd_HH:mm";
    private final static String LOG_FILE_PATTERN = "yyyy-MM-dd";

    private static LogUtils logUtils;

    private static final long LOG_FILE_SIZE_MAX = 20 * 1024 * 1024L;// 20M
    private static final String PATH = "/sdcard/Sys_LOG/" + TimeUtils.dataTimeMsToString(TimeUtils.getTimesMorning(), LOG_FILE_PATTERN) + "/";
    private static final String TXT = ".txt";
    private static String fileName = "";

    // 是否正在监测日志文件大小；
    private boolean logSizeMoniting = false;
    //内存中的日志文件大小监控时间间隔，5分钟
    private static final int MEMORY_LOG_FILE_MONITOR_INTERVAL = 5 * 60 * 1000;


    public final static boolean isDebug = true;

    private final static String APP_TAG = "IIScreen》》》";
    private static String lastMsg;

    private LogUtils() {
    }

    public static LogUtils getInstance() {
        if (logUtils == null) {
            logUtils = new LogUtils();
        }
        return logUtils;
    }

    public void init(Context context) {
        deleteCacheLog();
        createLogCollector(context);
    }

    //在程序退出时调用
    public void deInit(Context context) {
        // 取消定时闹铃
        cancelLogSizeMonitorTask(context);
        if (process != null)
            process.destroy();
    }


    /**
     * 获取相关数据:类名,方法名,行号等.用来定位行<br>
     * at cn.utils.MainActivity.onCreate(MainActivity.java:17) 就是用來定位行的代碼<br>
     *
     * @return [ Thread:main, at
     * cn.utils.MainActivity.onCreate(MainActivity.java:17)]
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts != null) {
            for (StackTraceElement st : sts) {
                if (st.isNativeMethod()) {
                    continue;
                }
                if (st.getClassName().equals(Thread.class.getName())) {
                    continue;
                }
                if (st.getClassName().equals(LogUtils.class.getName())) {
                    continue;
                }
                return "[ Thread:" + Thread.currentThread().getName() + ", at " + st.getClassName() + "." + st.getMethodName() + "(" + st.getFileName() + ":" + st.getLineNumber() + ")" + " ]";
            }
        }
        return null;
    }


    public static void v(String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.v(APP_TAG, lastMsg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.v(tag, lastMsg);
        }
    }


    public static void d(String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.d(APP_TAG, lastMsg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.d(tag, lastMsg);
        }
    }


    public static void i(String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.i(APP_TAG, lastMsg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.i(tag, lastMsg);
        }
    }


    public static void w(String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.w(APP_TAG, lastMsg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.w(tag, lastMsg);
        }
    }


    public static void error(String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.e(APP_TAG, lastMsg);
        }
    }

    public static void error(String tag, String msg) {
        if (isDebug) {
            lastMsg = getMsgFormat(msg);
            Log.e(tag, lastMsg);
        }
    }

    /**
     * 输出格式定义
     */
    private static String getMsgFormat(String msg) {
        return msg + " ;" + getFunctionName();
    }


    Process process;

    /**
     * 开始收集日志信息
     */
    public void createLogCollector(Context context) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    error(TAG, "文件是否存在： " + FileUtils.isFileExist(PATH, fileName));
                    if (TextUtils.isEmpty(fileName) || !FileUtils.isFileExist(PATH, fileName) || FileUtils.getFileSize(PATH + fileName) >= LOG_FILE_SIZE_MAX)
                        fileName = TimeUtils.dataTimeMsToString(System.currentTimeMillis(), LOG_FILE_PATTERN) + TXT;
                    if (!FileUtils.isFileExist(PATH, fileName)) {
                        FileUtils.createFile(PATH, fileName);
                    }

                    try {
                        deployLogSizeMonitorTask(context);
                        List<String> commandList = new ArrayList<>();
                        commandList.add("logcat");
                        commandList.add("-f");
                        commandList.add(PATH + fileName);
                        commandList.add("-v");
                        commandList.add("time");

                        commandList.add(PACKAGE_NAME + ":V");
                        commandList.add(PACKAGE_NAME + ":D");
                        commandList.add(PACKAGE_NAME + ":I");
                        commandList.add(PACKAGE_NAME + ":W");
                        commandList.add(PACKAGE_NAME + ":E");

                        commandList.add("System.err:W");// 过滤所有的错误信息
                        commandList.add("System.err:F");// 过滤所有的错误信息
                        commandList.add("System.out:I");// 过滤所有的错误信息
                        commandList.add("AndroidRuntime:E"); //运行报错

                        try {
                            process = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
                            Thread.sleep(1000);
                            LogUtils.error(TAG, "waitFor:" + process.waitFor());
                            if (logSizeMoniting)
                                createLogCollector(context);
                        } catch (Exception e) {
                            LogUtils.error(TAG, "CollectorThread == >" + e.getMessage());
                        }
                    } catch (Exception ex) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    LogUtils.error(TAG, ex.getMessage());
                }
                LogUtils.d(TAG, "收集日志循环已完全启动!!!");
            }
        }.start();
    }


    /**
     * 部署日志大小监控任务
     */
    private void deployLogSizeMonitorTask(Context context) {
        //如果当前正在监控着，则不需要继续部署
        if (logSizeMoniting) {
            return;
        }
        logSizeMoniting = true;
        Intent intent = new Intent("MONITOR_LOG_SIZE");
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (am != null) {
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), MEMORY_LOG_FILE_MONITOR_INTERVAL, sender);
        }
        LogUtils.error(TAG, "开始闹铃定时任务");
    }

    /**
     * 取消部署日志大小监控任务
     */
    private void cancelLogSizeMonitorTask(Context context) {
        logSizeMoniting = false;
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent("MONITOR_LOG_SIZE");
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (am != null) {
            am.cancel(sender);
        }
        LogUtils.error(TAG, "取消闹铃定时任务");
    }

    /**
     * 监测文件大小，如超出文件大小限制，重新写入新文件中
     */
    public void updateLogFileSize(Context context) {
        error("定时监控当前写入文件大小");
        if (logSizeMoniting)
            if (FileUtils.getFileSize(PATH + fileName) >= LOG_FILE_SIZE_MAX) {
                process.destroy();
                //日志文件监测action
                createLogCollector(context);
            }
    }

    /**
     * 删除2天前的缓存日志文件
     */
    private void deleteCacheLog() {
        LogUtils.error(TAG, "deleteCacheLog(),删除缓存的日志文件");
        File[] files = FileUtils.getFileList("/sdcard/XQL_LOG/");
        if (files == null || files.length <= 0)
            return;
        for (File file : files) {
            if (TimeUtils.getTimesMorning() - 86400 * 1000 * 2L >= TimeUtils.dateTimeStringTo_Ms(file.getName(), LOG_FILE_PATTERN)) {
                LogUtils.error(TAG, "删除文件夹：" + file.getName());
                FileUtils.deleteDirection(file);
            }
        }

    }
}