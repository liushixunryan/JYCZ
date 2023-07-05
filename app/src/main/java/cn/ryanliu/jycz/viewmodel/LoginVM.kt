package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.LoginResponse
import cn.ryanliu.jycz.bean.prequest.LoginRequest
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.UserUtil
import com.blankj.utilcode.util.EncryptUtils
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class LoginVM : BaseViewModel() {
    val loginResponseLV = MutableLiveData<LoginResponse?>()
    fun login(name: String, password: String) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.login(
                    LoginRequest(name, EncryptUtils.encryptMD5ToString(password))
                )

                if (response.isSuccess()) {
                    UserUtil.setUserToken(response.data?.token)
                    MmkvHelper.getInstance()
                        .putString(
                            Constant.MmKv_KEY.USER_ID,
                            response.data?.user_id.toString() ?: ""
                        )
                    MmkvHelper.getInstance().putString(
                        Constant.MmKv_KEY.USER_NAME, response.data?.user_name ?: ""
                    )
                    MmkvHelper.getInstance()
                        .putString(
                            Constant.MmKv_KEY.LOGIN_ACOUNT,
                            response.data?.login_acount ?: ""
                        )
                    MmkvHelper.getInstance()
                        .putString(Constant.MmKv_KEY.PHONE, response.data?.phone ?: "")
                    MmkvHelper.getInstance()
                        .putString(Constant.MmKv_KEY.UNIT_NAME, response.data?.unit_name ?: "")
                    MmkvHelper.getInstance()
                        .putString(Constant.MmKv_KEY.UNIT_CODE, response.data?.unit_code ?: "")

                    MmkvHelper.getInstance()
                        .putString(Constant.MmKv_KEY.UNIT_ID, response.data?.unit_id ?: "")
                    MmkvHelper.getInstance()
                        .putString(Constant.MmKv_KEY.SITE_ID, response.data?.site_id ?: "")
                    MmkvHelper.getInstance()
                        .putString(Constant.MmKv_KEY.SITE_CODE, response.data?.site_code ?: "")
                    MmkvHelper.getInstance()
                        .putString(Constant.MmKv_KEY.SITE_NAME, response.data?.site_name ?: "")
                    loginResponseLV.postValue(response.data)

                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }
}