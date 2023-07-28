package cn.ryanliu.jycz.util;

import static android.content.Context.ALARM_SERVICE;

import static cn.ryanliu.jycz.util.LogTaskReceiver.MONITOR_LOG_SIZE_ACTION;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
    private static final String PACKAGE_NAME = "cn.shixun";
    private final static String LOG_FILE_NAME_PATTERN = "yyyy-MM-dd_HH:mm";
    private final static String LOG_FILE_PATTERN = "yyyy-MM-dd";

    private static LogUtils logUtils;

    private static final long LOG_FILE_SIZE_MAX = 20 * 1024 * 1024L;// 20M
    private static final String PATH = "/sdcard/ShiXun_LOG/" + dataTimeMsToString(getTimesMorning(), LOG_FILE_PATTERN) + "/";
    private static final String TXT = ".txt";
    private static String fileName = "";

    // 是否正在监测日志文件大小；
    private boolean logSizeMoniting = false;
    //内存中的日志文件大小监控时间间隔，5分钟
    private static final int MEMORY_LOG_FILE_MONITOR_INTERVAL = 5 * 60 * 1000;


    public final static boolean isDebug = true;

    private final static String APP_TAG = "ShiXun》》》";
    private static String lastMsg;

    private LogUtils() {
    }

    public static LogUtils getInstance() {
        if (logUtils == null) {
            logUtils = new LogUtils();
        }
        return logUtils;
    }

    public void init() {
        deleteCacheLog();
        createLogCollector();
    }

    //在程序退出时调用
    public void deInit() {
        // 取消定时闹铃
        cancelLogSizeMonitorTask();
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
    public void createLogCollector() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    error(TAG, "文件是否存在： " + isFileExist(PATH, fileName));
                    if (TextUtils.isEmpty(fileName) || !isFileExist(PATH, fileName) || getFileSize(PATH + fileName) >= LOG_FILE_SIZE_MAX)
                        fileName = dataTimeMsToString(System.currentTimeMillis(), LOG_FILE_NAME_PATTERN) + TXT;
                    if (!isFileExist(PATH, fileName)) {
                        createFile(PATH, fileName);
                    }

                    try {
                        deployLogSizeMonitorTask();
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
                        //                        commandList.add("System.err:F");// 过滤所有的错误信息
                        //                        commandList.add("System.out:I");// 过滤所有的错误信息
                        commandList.add("AndroidRuntime:E"); //运行报错

                        try {
                            process = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
                            Thread.sleep(1000);
                            LogUtils.error(TAG, "waitFor:" + process.waitFor());
                            if (logSizeMoniting)
                                createLogCollector();
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
    private void deployLogSizeMonitorTask() {
        //如果当前正在监控着，则不需要继续部署
        if (logSizeMoniting) {
            return;
        }
        logSizeMoniting = true;
        Intent intent = new Intent(MONITOR_LOG_SIZE_ACTION);
        PendingIntent sender = PendingIntent.getBroadcast(BaseApplication.getInstance().getMediaTApplication(), 0, intent, 0);
        AlarmManager am = (AlarmManager) BaseApplication.getInstance().getMediaTApplication().getSystemService(ALARM_SERVICE);
        if (am != null) {
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), MEMORY_LOG_FILE_MONITOR_INTERVAL, sender);
        }
        LogUtils.error(TAG, "开始闹铃定时任务");
    }

    /**
     * 取消部署日志大小监控任务
     */
    private void cancelLogSizeMonitorTask() {
        logSizeMoniting = false;
        AlarmManager am = (AlarmManager) BaseApplication.getInstance().getMediaTApplication().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MONITOR_LOG_SIZE_ACTION);
        PendingIntent sender = PendingIntent.getBroadcast(BaseApplication.getInstance().getMediaTApplication(), 0, intent, 0);
        if (am != null) {
            am.cancel(sender);
        }
        LogUtils.error(TAG, "取消闹铃定时任务");
    }

    /**
     * 监测文件大小，如超出文件大小限制，重新写入新文件中
     */
    public void updateLogFileSize() {
        error("定时监控当前写入文件大小");
        if (logSizeMoniting)
            if (getFileSize(PATH + fileName) >= LOG_FILE_SIZE_MAX) {
                process.destroy();
                //日志文件监测action
                createLogCollector();
            }
    }

    /**
     * 删除2天前的缓存日志文件
     */
    private void deleteCacheLog() {
        LogUtils.error(TAG, "deleteCacheLog(),删除缓存的日志文件");
        File[] files = getFileList("/sdcard/ShiXun_LOG/");
        if (files == null || files.length <= 0)
            return;
        for (File file : files) {
            if (getTimesMorning() - 86400 * 1000 * 2L >= dateTimeStringTo_Ms(file.getName(), LOG_FILE_PATTERN)) {
                LogUtils.error(TAG, "删除文件夹：" + file.getName());
                deleteDirection(file);
            }
        }

    }


    /**
     * 判断文件是否已经存在
     *
     * @param fileName 要检查的文件名
     * @return boolean, true表示存在，false表示不存在
     */
    public static boolean isFileExist(String path, String fileName) {
        File file = new File(path + fileName);
        return file.exists();
    }


    /**
     * 创建文件
     *
     * @param path     文件所在目录的目录名
     * @param fileName 文件名
     * @return 文件新建成功则返回true
     */
    public static boolean createFile(String path, String fileName) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                //按照指定的路径创建文件夹
                boolean a = file.mkdirs();
                Log.e(TAG, "createFile: " + a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File dir = new File(path + fileName);
        if (!dir.exists()) {
            try {
                boolean newFile = dir.createNewFile();
                //在指定的文件夹中创建文件
                Log.e(TAG, "createFile: " + newFile);
                return newFile;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 描述：删除一个目录（可以是非空目录）
     *
     * @param dir 目录绝对路径
     */
    public static boolean deleteDirection(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteDirection(file);//递归
            }
        }
        dir.delete();
        return true;
    }


    /**
     * 描述：计算某个文件的大小
     *
     * @param path 文件的绝对路径
     * @return 文件大小
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        return file.length();
    }


    /**
     * 描述：获取某个路径下的文件列表
     *
     * @param path 文件路径
     * @return 文件列表File[] files
     */
    public static File[] getFileList(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                return files;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 描述：将毫秒转成所需的格式
     *
     * @param nowDateTime_Ms：毫秒
     * @param format：日期时间格式
     * @return 指定格式的时间字符串
     */
    public static String dataTimeMsToString(long nowDateTime_Ms, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(nowDateTime_Ms);

        return formatter.format(calendar.getTime());
    }

    //获得当天0点时间 
    public static long getTimesMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }


    public static long dateTimeStringTo_Ms(String st, String pattern) {
        SimpleDateFormat s = new SimpleDateFormat(pattern);
        try {
            return s.parse(st).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
