package me.weishu.leoric;


import android.util.Log;

public class LogUtil {
    public static boolean isDebugLeoric = true;

    public static void leoricLog(String remark) {
        if (isDebugLeoric) {
            Log.e("Leoric", remark);
        }
    }

}
