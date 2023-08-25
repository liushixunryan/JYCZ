package cn.ryanliu.jycz.basic

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process
import androidx.multidex.MultiDex
import cn.ryanliu.jycz.util.AppManager
import cn.ryanliu.jycz.utillog.Anomalous
import cn.ryanliu.jycz.utillog.Service1
import cn.ryanliu.jycz.utillog.Service2
import com.tencent.mmkv.MMKV
import me.weishu.leoric.Leoric
import me.weishu.leoric.LeoricConfigs

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
open class BaseApplication : Application(){
    private var mContext: Context? = null
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

    //全局contenxt
    open fun getMediaTApplication(): Context? {
        return mContext
    }

    override fun onCreate() {
        super.onCreate()
        //全局contenxt
        mContext = applicationContext
        setApplication(this)
        val rootDir = MMKV.initialize(this)
        println("mmkv root: $rootDir")
        MultiDex.install(this)

        Anomalous.install(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
////        MultiDex.install(this)
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//
//        val myDate: String = format.format(Date())
//        //定义前台服务的默认样式。即标题、描述和图标
//        KeepLive.foregroundNotification = ForegroundNotification(
//            getString(R.string.app_monitor) + "后台服务正在运行", "启动时间:$myDate", R.mipmap.ic_logo
//        )  //定义前台服务的通知点击事件
//        { context, intent ->
//            val component = ComponentName(packageName, LAUNCHER_NAME)
//            val explicitIntent = Intent()
//            explicitIntent.component = component
//            startActivity(explicitIntent)
//        }
//        KeepLive.launcherName = LAUNCHER_NAME

        Leoric.init(
            base, LeoricConfigs(
                LeoricConfigs.LeoricConfig(
                    "$packageName:daemon1", Service1::class.java.canonicalName, "", ""
                ), LeoricConfigs.LeoricConfig(
                    "$packageName.daemon", Service2::class.java.canonicalName, "", ""
                )
            )
        )
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