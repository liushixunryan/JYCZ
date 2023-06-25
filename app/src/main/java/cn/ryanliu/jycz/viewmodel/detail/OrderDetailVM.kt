package cn.ryanliu.jycz.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.SearchOrderCount
import cn.ryanliu.jycz.bean.prequest.PsearchScanCount
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:
 */
class OrderDetailVM : BaseViewModel() {
    val mSelect = MutableLiveData<SearchOrderCount?>()

    //获取卸车/装车当日和昨日统计数据
    fun searchOrderCount(
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchOrderCount(
                    PsearchScanCount(
                        ""
                    )
                )

                if (response.isSuccess()) {
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