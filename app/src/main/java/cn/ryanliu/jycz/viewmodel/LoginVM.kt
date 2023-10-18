package cn.ryanliu.jycz.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.LoginResponse
import cn.ryanliu.jycz.bean.prequest.LoginRequest
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.UserUtil
import cn.ryanliu.jycz.util.okHttpUtil
import com.blankj.utilcode.util.EncryptUtils
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.xml.sax.ContentHandler
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class LoginVM : BaseViewModel() {
    val loginResponseLV = MutableLiveData<LoginResponse?>()
    val updateApp = MutableLiveData<String?>()
    val failData = MutableLiveData<String?>()
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
                    failData.postValue(response.data.toString())
                    hideLoading()
                }
            } catch (e: Exception) {
                hideLoading()
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }


    fun updateAppfun() {
        viewModelScope.launch {
            try {
                okHttpUtil.getOkhttpRequest(
                    "https://jys.caacpl.com:33250/appver.xml",
                    object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Log.e("sansuiban", "onFailure: $e")
                        }

                        @Throws(IOException::class)
                        override fun onResponse(call: Call, response: Response) {
                            val query = response.body.string()
                            Log.e(TAG, "onResponse: ${query}", )
                            updateApp.postValue(query)
                        }
                    })
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }


}