package cn.ryanliu.jycz.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.OrderListBean
import cn.ryanliu.jycz.bean.prequest.PsearchOrderDetails
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:
 */
class OrderListVM : BaseViewModel() {
    val mSelect = MutableLiveData<MutableList<OrderListBean>?>()

    fun searchOrderDetails(
        PsearchOrderDetails: PsearchOrderDetails
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchOrderDetails(
                    PsearchOrderDetails
                )

                if (response.isSuccess()) {
                    mSelect.postValue(response.data)
                }else{
                    mSelect.postValue(response.data)
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