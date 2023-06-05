package cn.ryanliu.jycz.util

import android.graphics.Color
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils

object ToastUtilsExt {

    @JvmStatic
    fun info(str: String?) {
        str?.let {
            ToastUtils.make().setBgColor(Color.parseColor("#cc000000"))
                .setGravity(Gravity.CENTER, 0, 0)
                .setTextColor(Color.parseColor("#ffffff")).show(it)
        }
    }
}