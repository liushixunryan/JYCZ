package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.SelectAreaBean
import cn.ryanliu.jycz.bean.SelectCarBean
import cn.ryanliu.jycz.bean.prequest.PCarInfoIn1
import cn.ryanliu.jycz.bean.prequest.PWareArea
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/6
 * @Description:
 */
class SelectAreaVM:BaseViewModel() {
    val mSelectArea = MutableLiveData<MutableList<SelectAreaBean>?>()

    fun getWareArea(ware_area_name: String? = null) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.getWareArea(PWareArea(ware_area_name,"卸车"))

                if (response.isSuccess()) {
                    mSelectArea.postValue(response.data)

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