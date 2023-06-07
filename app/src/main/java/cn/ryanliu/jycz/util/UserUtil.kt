package cn.ryanliu.jycz.util

import cn.ryanliu.jycz.common.constant.Constant

/**
 * 此类保存登录期间的持久数据
 */
object UserUtil {
    @Volatile
    var sToken: String? = null

    @JvmStatic
    fun getUserToken(): String? {
        if (sToken == null) {
            sToken = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.TOKEN)
        }
        return sToken
    }
}