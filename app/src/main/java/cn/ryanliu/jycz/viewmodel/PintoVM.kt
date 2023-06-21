package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PcreateTboxCode1
import cn.ryanliu.jycz.bean.prequest.PscanFjCode
import cn.ryanliu.jycz.bean.scanFjCode
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:
 */
class PintoVM : BaseViewModel() {

    val mSelectCar = MutableLiveData<scanFjCode?>()
    val mBackList = MutableLiveData<String>()
    val mtpNum = MutableLiveData<String?>()

    fun scanFjCode(
        box_code: String,
        new_tp_code: String,
        oper_type: Int,
        tp_num: Int,
        ware_area: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.scanFjCode(
                    PscanFjCode(
                        box_code, new_tp_code, oper_type, tp_num, ware_area
                    )
                )

                if (response.isSuccess()) {
                    mSelectCar.postValue(response.data)

                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun createTboxCode1(
        tp_num: Int,
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.createTboxCode1(
                    PcreateTboxCode1(
                        tp_num
                    )
                )

                if (response.isSuccess()) {
                    mtpNum.postValue(response.data)

                } else {
                    showServerErr(response.msg)
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