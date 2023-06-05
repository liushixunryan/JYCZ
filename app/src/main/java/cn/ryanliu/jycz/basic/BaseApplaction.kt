package cn.ryanliu.jycz.basic

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
open class BaseApplication : Application(){
    companion object {
        lateinit var sInstance: BaseApplication
        @JvmField
        var isDebug: Boolean = true

        /**
         * 获得当前app运行的Application
         */
        @JvmStatic
        fun getInstance(): BaseApplication {
            if (sInstance == null) {
                throw NullPointerException(
                    "please inherit BaseApplication or call setApplication."
                )
            }
            return sInstance
        }
    }
    override fun onCreate() {
        super.onCreate()

    }

    /**
     * 获取进程名
     *
     * @param context
     * @return
     */
    fun getCurrentProcessName(context: Context): String? {
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (proInfo in runningApps) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName
                }
            }
        }
        return null
    }
}