package cn.ryanliu.jycz.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PsearchScanCount
import cn.ryanliu.jycz.bean.scanFjCode
import cn.ryanliu.jycz.bean.searchScanCount
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:卸车跟装车都在这
 */
class LoadingDetailVM : BaseViewModel() {
    val mSelect = MutableLiveData<searchScanCount?>()

    //获取卸车/装车当日和昨日统计数据
    fun scanFjCode(
        oper_flag: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchScanCount(
                    PsearchScanCount(
                        oper_flag
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