package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PcreateTboxCode1
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class OnlyPrintTMVM : BaseViewModel() {
    val mBackList = MutableLiveData<String?>()
    fun createTCode1(
        tp_num: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.createTCode1(
                    PcreateTboxCode1(
                        tp_num
                    )
                )
                if (response.isSuccess()) {
                    mBackList.postValue(response.data.toString())

                } else {
                    mBackList.postValue(response.data.toString())
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