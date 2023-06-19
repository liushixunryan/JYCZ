package cn.ryanliu.jycz.util

import android.app.Activity
import java.util.*

/**
 * 应用模块:
 *
 *
 * 类描述: Activity的堆栈管理者
 *
 *
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
class AppManager private constructor() {
    private object SingleHolder {
        val instance = AppManager()
    }

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity?) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
        }
    }

    /**
     * 是否有activity
     */
    val isActivity: Boolean
        get() = if (activityStack != null) {
            !activityStack!!.isEmpty()
        } else false

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return activityStack!!.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity!!.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                finishActivity(activityStack!![i])
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    fun getActivity(cls: Class<*>): Activity? {
        if (activityStack != null) {
            for (activity in activityStack!!) {
                if (activity!!.javaClass == cls) {
                    return activity
                }
            }
        }
        return null
    }

    /**
     * 退出应用程序
     */
    fun AppExit() {
        try {
            finishAllActivity()
        } catch (e: Exception) {
            activityStack!!.clear()
            e.printStackTrace()
        }
    }

    companion object {
        private var activityStack: Stack<Activity?>? = null
        val instance: AppManager
            get() = SingleHolder.instance
    }
}