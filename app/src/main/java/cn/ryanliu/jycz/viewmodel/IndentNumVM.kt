package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.IndentNumBean
import cn.ryanliu.jycz.bean.List1
import cn.ryanliu.jycz.bean.prequest.PCarOrders
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:
 */
class IndentNumVM : BaseViewModel() {
    val mData = MutableLiveData<MutableList<List1>>()
    val mDataBean = MutableLiveData<IndentNumBean>()

    fun searchCarOrders(
        hand_task_id: Int,
        oper_flag: String,
        search_type: String
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.searchCarOrders(
                    PCarOrders(
                        hand_task_id,
                        oper_flag,
                        search_type
                    )
                )

                if (response.isSuccess()) {
                    mDataBean.postValue(response.data!!)
                    mData.postValue(response.data!!.List1)

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