package cn.ryanliu.jycz.util;

import android.annotation.SuppressLint;
import android.os.SystemClock;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 标题：时间操作工具类
 * 描述：时间格式转换，初始化时间，毫秒转换为时间字符串。
 * 作者：lmy
 * 时间：2020-12-31
 */

public class TimeUtils {

    public static String yyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss:SSS";
    private static long differenceTime;        //以前服务器时间 - 以前服务器时间的获取时刻的系统启动时间

    /**
     * 描述：获取当前计算出来的毫秒时间
     *
     * @return 获取当前毫秒时间
     */
    private static long getNowDateTime_Ms() {
        //时间差加上当前手机启动时间就是准确的服务器时间了
        return differenceTime + SystemClock.elapsedRealtime();
    }

    /**
     * 描述：24小时格式日期时间字符串转成毫秒时间
     *
     * @param st 24小时格式日期时间字符串
     * @return 毫秒时间
     */
    public static long dateTimeStringTo_Ms(String st) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat(yyyMMddHHmmssSSS);
        try {
            return s.parse(st).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 描述：初始化日期时间：输入当前真实时间
     *
     * @param nowDateTime 当前时间
     */
    public synchronized static void syncDateTime(String nowDateTime) {
        long nowDateTimeMs = dateTimeStringTo_Ms(nowDateTime);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        //记录时间差
        if (nowDateTimeMs >= elapsedRealtime) {
            differenceTime = nowDateTimeMs - elapsedRealtime;
        }
    }

    /**
     * 描述：初始化日期时间，获取系统时间
     */
    public synchronized static void syncDateTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(yyyMMddHHmmssSSS);
        String nowDateTime = formatter.format(date);

        long nowDateTimeMs = dateTimeStringTo_Ms(nowDateTime);
        //记录时间差
        differenceTime = nowDateTimeMs - SystemClock.elapsedRealtime();
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

    /**
     * 描述：获取计算出来的当前日期时间，24小时格式
     *
     * @return 24小时格式当前时间
     */
    public static String getNowDateTime_24() {
        long now = getNowDateTime_Ms();
        return dataTimeMsToString(now, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 描述：获取指定格式的日期时间
     * @param format 返回的日期时间格式
     * @return 指定格式的日期时间
     */
    public static String getNowDateTime(String format) {
        long now = getNowDateTime_Ms();
        return dataTimeMsToString(now, format);
    }

    /**
     * 描述：获取网络服务器时间
     *
     * @return 网络获取的时间
     * @throws IOException IO异常
     */
    public static long getNetworkTime() throws Exception {
        //取得资源对象

//        URL url = new URL("http://www.qq.com");
//        URL url = new URL("http://www.ntsc.ac.cn");
        URL url = new URL("https://www.baidu.com");
        URLConnection uc = url.openConnection();
        //发出连接
        uc.connect();
        //取得网站日期时间
        long l = uc.getDate();

        return l;
    }

    /**
     * 描述：获取精确到上下午的时分秒
     *
     * @param time 字符串时间
     * @return 日历(Calender)对象
     */
    public static Calendar getPreciseTime(String time) {

        Calendar calendar1 = null;
        try {
            // 字符串时间转换成Calendar
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = formatter.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar1 = Calendar.getInstance();

            // 获取时分秒
            int hour = calendar.get(Calendar.HOUR);
            if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
                hour = hour + 12;
            }

            calendar.setTimeInMillis(System.currentTimeMillis());
            long a= System.currentTimeMillis();
            long b=date.getTime();
            if (System.currentTimeMillis() <= date.getTime()) {
                calendar.add(Calendar.HOUR, 24);
            }
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            calendar1.set(Calendar.HOUR_OF_DAY, hour);
            calendar1.set(Calendar.MINUTE, minute);
            calendar1.set(Calendar.SECOND, second);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar1;
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
