package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PInventResult
import cn.ryanliu.jycz.bean.scanMCode
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:
 */
class InventoryResultVM : BaseViewModel() {
    val mBackList = MutableLiveData<String?>()
    fun saveInvent(
        invent_id: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.saveInvent(
                    PInventResult(
                        invent_id
                    )
                )
                if (response.isSuccess()) {
                    mBackList.postValue("保存成功")

                } else {
                    mBackList.postValue("失败")
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