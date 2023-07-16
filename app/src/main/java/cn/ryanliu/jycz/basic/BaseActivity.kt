package cn.ryanliu.jycz.basic

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    lateinit var mDatabind: B
    lateinit var mViewModel: VM;

    //是否显示标题栏
    private var isShowTitle: Boolean? = false;

    //是否显示状态栏
    private var isShowStatusBar: Boolean? = false;

    //是否允许旋转屏幕
    private var isAllowScreenRoate: Boolean? = true;
    var context: Context? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this;
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //初始化布局
        mDatabind = DataBindingUtil.setContentView(this, layoutId());

        //创建我们的ViewModel。
        createViewModel()
        //activity管理
        ActivityCollector().addActivity(this)

        mDatabind.lifecycleOwner = (this)
        //初始化控件
        initView()
        //设置数据
        createObserver()
    }

    /**
     * 绑定viewmodel
     */
    open fun createViewModel() {
        val modelClass: Class<*>
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            modelClass = type.actualTypeArguments[1] as Class<*>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = BaseViewModel::class.java
        }
        mViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get<ViewModel>(modelClass as Class<ViewModel>) as VM

    }

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract fun layoutId(): Int

    /**
     * 初始化控件
     */
    protected abstract fun initView()


    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    abstract class OnSingleClickListener : View.OnClickListener {
        private var lastClickTime: Long = 0
        abstract fun onSingleClick(view: View?)
        override fun onClick(view: View) {
            val curClickTime = System.currentTimeMillis()
            if (curClickTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime
                onSingleClick(view)
            }
        }

        companion object {
            //两次点击按钮之间的间隔，目前为1000ms
            private const val MIN_CLICK_DELAY_TIME = 1000
        }
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    abstract class OnMultiClickListener : View.OnClickListener {
        abstract fun onMultiClick(view: View?)
        override fun onClick(v: View) {
            onMultiClick(v)
        }
    }

    /**
     * 活动管理器
     */
    class ActivityCollector() {
        var activitys: MutableList<Activity> = ArrayList<Activity>();

        /**
         * 向list中添加一个活动
         *
         * @param activity 活动
         */
        fun addActivity(activity: Activity) {
            activitys.add(activity);
        }

        /**
         * 从List中移除活动
         *
         * @param activity 活动
         */
        fun removeActivity(activity: Activity) {
            activitys.remove(activity);
        }

        /**
         * 将List中储存的活动全部销毁掉
         */
        fun finishAll() {
            for (acitvity: Activity in activitys) {
                if (!acitvity.isFinishing()) {
                    acitvity.finish();
                }
            }
        }

    }


    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    override fun onDestroy() {
        super.onDestroy()
        //activity管理
        ActivityCollector().removeActivity(this);
    }


}