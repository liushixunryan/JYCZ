package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.SelectAreaBean
import cn.ryanliu.jycz.bean.lockAllCancel
import cn.ryanliu.jycz.bean.prequest.Area
import cn.ryanliu.jycz.bean.prequest.PWareArea
import cn.ryanliu.jycz.bean.prequest.PlockAllCancel
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:
 */
class AreaSelectVM : BaseViewModel() {
    val mSelectArea = MutableLiveData<MutableList<SelectAreaBean>?>()
    val mSurepd = MutableLiveData<lockAllCancel?>()

    fun getWareArea(ware_area_name: String? = null, xzc: String) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.getWareArea(PWareArea(ware_area_name, xzc))

                if (response.isSuccess()) {
                    mSelectArea.postValue(response.data)

                } else {
                    mSelectArea.postValue(response.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun lockAllCancel(
        area_list: List<Area>,
        lock_type: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response =
                    ApiService.apiService.lockAllCancel(PlockAllCancel(area_list, lock_type))

                if (response.isSuccess()) {
                    mSurepd.postValue(response.data)

                } else {
//                    mSurepd.postValue(response.msg)
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