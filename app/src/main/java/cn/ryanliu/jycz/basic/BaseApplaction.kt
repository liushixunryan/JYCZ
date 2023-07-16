package cn.ryanliu.jycz.basic

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process
import androidx.multidex.MultiDex
import cn.ryanliu.jycz.util.AppManager
import com.blankj.utilcode.util.LogUtils
import com.tencent.mmkv.MMKV
import java.io.File

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
        setApplication(this)
        val rootDir = MMKV.initialize(this)
        println("mmkv root: $rootDir")
        MultiDex.install(this)

        /**
         * 设置全局Log
         */
        val config = LogUtils.getConfig() //获取 log 配置

        config.isLogSwitch = true //获取log总开关
        config.setConsoleSwitch(true) // 设置 log 控制台开关
        config.globalTag = "gulu" //设置全局tag
        config.isLogHeadSwitch = true //设置头部信息开关
        config.isLog2FileSwitch = true //设置设置文件开关
        val file = File("/sdcard/1112")
        config.setDir(file) //设置文件存储目录

        config.filePrefix = "ryanliu" //设置文件前缀
        config.setBorderSwitch(true) //设置边框开关
        config.saveDays = 3 //设置可保留天数
        LogUtils.getLogFiles() //获取所有日志
        LogUtils.file(file);  //log 到文件
        LogUtils.getCurrentLogFilePath() //获取当前日志文件路径

        LogUtils.e(LogUtils.getCurrentLogFilePath())

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

    /**
     * 当宿主没有继承自该Application时,可以使用set方法进行初始化baseApplication
     */
    private fun setApplication(application: BaseApplication) {
        sInstance = application
        application
            .registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(
                    activity: Activity,
                    savedInstanceState: Bundle?
                ) {
                    AppManager.instance.addActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {
                }

                override fun onActivityResumed(activity: Activity) {
                }

                override fun onActivityPaused(activity: Activity) {
                }

                override fun onActivityStopped(activity: Activity) {
                }

                override fun onActivitySaveInstanceState(
                    activity: Activity, outState: Bundle
                ) {
                }

                override fun onActivityDestroyed(activity: Activity) {
                    AppManager.instance.removeActivity(activity)
                }
            })
    }

}