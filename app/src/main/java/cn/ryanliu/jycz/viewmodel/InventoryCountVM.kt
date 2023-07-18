package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.InventResult
import cn.ryanliu.jycz.bean.cancel_inventory
import cn.ryanliu.jycz.bean.prequest.PInventResult
import cn.ryanliu.jycz.bean.prequest.Pcancel_inventory
import cn.ryanliu.jycz.bean.prequest.PscanMCode
import cn.ryanliu.jycz.bean.scanMCode
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:
 */
class InventoryCountVM : BaseViewModel() {
    val mSelectCar = MutableLiveData<scanMCode?>()
    val mInventResult = MutableLiveData<InventResult?>()
    val mBackList = MutableLiveData<String>()
    val mcancelpd = MutableLiveData<String>()

    fun scanMCode(
        oper_flag: String?,
        scan_code: String?,
        invent_id: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.scanMCode(
                    PscanMCode(
                        oper_flag, scan_code, invent_id,
                    )
                )

                if (response.isSuccess()) {
                    mSelectCar.postValue(response.data)

                } else {
                    mSelectCar.postValue(response.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }


    fun getInventResult(
        invent_id: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.getInventResult(
                    PInventResult(
                        invent_id
                    )
                )

                if (response.isSuccess()) {
                    mInventResult.postValue(response.data)

                } else {
                    mInventResult.postValue(response.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun cancelInventory(
        invent_id: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.cancel_inventory(
                    Pcancel_inventory(
                        invent_id
                    )
                )

                if (response.isSuccess()) {
                    mcancelpd.postValue("成功")

                } else {
//                    mcancelpd.postValue(response.data)
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