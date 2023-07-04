package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PcreateTCode2
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:
 */
class ScanBoxTMVM : BaseViewModel() {
    val mBackList = MutableLiveData<String?>()
    fun createTCode2(
        list: MutableList<PcreateTCode2>
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.createTCode2(
                    list
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