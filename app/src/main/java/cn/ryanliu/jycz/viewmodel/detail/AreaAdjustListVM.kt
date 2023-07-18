package cn.ryanliu.jycz.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.AreajustListBean
import cn.ryanliu.jycz.bean.prequest.PsearchWareAreaChangeList
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/13
 * @Description:
 */
class AreaAdjustListVM : BaseViewModel() {
    val mSelect = MutableLiveData<MutableList<AreajustListBean>?>()

    fun searchWareAreaChangeList(
        PsearchWareAreaChangeList: PsearchWareAreaChangeList
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchWareAreaChangeList(
                    PsearchWareAreaChangeList
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