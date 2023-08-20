package cn.ryanliu.jycz.basic

import cn.ryanliu.jycz.util.ToastUtilsExt

open class BaseResponse<T>(
    var code: Int,
    var `data`: T? = null,
    var msg: String? = null,
    var time_stamp: String? = null,
    var success: Boolean? = null
) {
    fun isSuccess(): Boolean {
        if (code == 1) {
            return true
        } else {
            if (!msg.isNullOrEmpty())
                if (!msg.equals("未查询到信息"))
                    ToastUtilsExt.info(msg)
        }
        return false
    }


}