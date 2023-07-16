package cn.ryanliu.jycz.util

import cn.ryanliu.jycz.common.constant.Constant

/**
 * 此类保存登录期间的持久数据
 */
object UserUtil {
    @Volatile
    var sToken: String? = null

    @Volatile
    var spandian: String? = null

    @JvmStatic
    fun getUserToken(): String? {
        if (sToken == null) {
            sToken = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.TOKEN)
        }
        return sToken
    }

    @JvmStatic
    fun setUserToken(token: String?) {
        sToken = token
        MmkvHelper.getInstance().putString(Constant.MmKv_KEY.TOKEN, token ?: "")
    }


    @JvmStatic
    fun setPanDian(pandian: String?) {
        spandian = pandian
        MmkvHelper.getInstance().putString(Constant.MmKv_KEY.PANDIAN, pandian ?: "")
    }

    @JvmStatic
    fun getPanDian(): String? {
        if (spandian == null) {
            spandian = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.PANDIAN)
        }
        return spandian
    }


}