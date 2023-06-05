package cn.ryanliu.jycz.util

import cn.ryanliu.jycz.common.constant.Constant
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken

object AuthorUtil {

    var roles: List<String>? = null
    var allRoles: List<String>? = null
    fun saveRoles(list: List<String>?) {
        roles = list
        MmkvHelper.getInstance()
            .putString(Constant.MmKv_KEY.ROLES, GsonUtils.toJson(roles))
    }

    fun saveAllRoles(list: List<String>?) {
        allRoles = list
        MmkvHelper.getInstance()
            .putString(Constant.MmKv_KEY.ALL_ROLES, GsonUtils.toJson(allRoles))
    }

    @JvmStatic
    fun isInRole(role: String): Boolean {
        if (roles.isNullOrEmpty()) {
            MmkvHelper.getInstance().getString(Constant.MmKv_KEY.ROLES)?.let {
                roles = GsonUtils.fromJson(it, object : TypeToken<List<String>>() {}.type)
            }
        }
//        return true
        //TODO 假数据
        return roles?.contains(role) ?: false
    }

    @JvmStatic
    fun isInAllRole(role: String): Boolean {
        if (allRoles.isNullOrEmpty()) {
            MmkvHelper.getInstance().getString(Constant.MmKv_KEY.ALL_ROLES)?.let {
                allRoles = GsonUtils.fromJson(it, object : TypeToken<List<String>>() {}.type)
            }
        }
//        return true
        //TODO 假数据
        return allRoles?.contains(role) ?: false
    }
}