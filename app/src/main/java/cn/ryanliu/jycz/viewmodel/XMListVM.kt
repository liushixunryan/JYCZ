package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.ScanOrdersBean
import cn.ryanliu.jycz.bean.XMListBean
import cn.ryanliu.jycz.bean.prequest.PBoxcodeList
import cn.ryanliu.jycz.bean.prequest.PScanOrders
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:
 */
class XMListVM:BaseViewModel(){
    val mData = MutableLiveData<MutableList<XMListBean>?>()

    fun getBoxcodeList(
        hand_task_id: Int,
        oper_flag:String,
        py_order_code: String
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.getBoxcodeList(
                    PBoxcodeList(
                        hand_task_id,
                        oper_flag,
                        py_order_code
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
}