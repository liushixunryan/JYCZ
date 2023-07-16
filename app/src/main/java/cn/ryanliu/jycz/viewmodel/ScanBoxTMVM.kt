package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.creategeneraltpcode
import cn.ryanliu.jycz.bean.prequest.PcreateTCode2
import cn.ryanliu.jycz.bean.prequest.Pcreategeneraltpcode
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:
 */
class ScanBoxTMVM : BaseViewModel() {
    val mBackList = MutableLiveData<String?>()
    val mCode = MutableLiveData<creategeneraltpcode>()

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

    fun creategeneraltpcode(
        tp_code: String,
        tp_code_num: Int
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.create_general_tpcode(
                    Pcreategeneraltpcode(tp_code,tp_code_num)
                )
                if (response.isSuccess()) {
                    mCode.postValue(response.data!!)

                } else {
//                    mCode.postValue(response.data!!)
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