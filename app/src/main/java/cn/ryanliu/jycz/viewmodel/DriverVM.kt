package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.ScanInCodeBean
import cn.ryanliu.jycz.bean.ScanOrdersBean
import cn.ryanliu.jycz.bean.prequest.PScanInCode
import cn.ryanliu.jycz.bean.prequest.PScanOrders
import cn.ryanliu.jycz.bean.prequest.PSubmitSaveIn
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:
 */
class DriverVM : BaseViewModel() {
    val mData = MutableLiveData<ScanOrdersBean?>()
    val mDatacode = MutableLiveData<ScanInCodeBean?>()

    //返回
    val mBackList = MutableLiveData<String>()

    fun getScanOrders(
        car_number: String,xzc:String,
        reservation_type: String,
        task_type: String
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.getScanOrders(
                    PScanOrders(
                        car_number,
                        xzc,
                        reservation_type,
                        task_type
                    )
                )

                if (response.isSuccess()) {
                    mData.postValue(response.data)

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

    fun getScanInCode(
        hand_task_id: Int, xzc:String,
        scan_code: String
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.getScanInCode(
                    PScanInCode(
                        hand_task_id, xzc,
                        scan_code,
                    )
                )

                if (response.isSuccess()) {
                    mDatacode.postValue(response.data)

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

    fun submitSaveIn(
        hand_task_id: Int,xzc:String
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.submitSaveIn(
                    PSubmitSaveIn(
                        hand_task_id.toString(), xzc

                    )
                )

                if (response.isSuccess()) {
                    mBackList.postValue("成功")

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