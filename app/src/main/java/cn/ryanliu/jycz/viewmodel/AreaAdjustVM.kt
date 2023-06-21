package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PchangWareArea
import cn.ryanliu.jycz.bean.prequest.PscanMCode
import cn.ryanliu.jycz.bean.scanMCode
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:
 */
class AreaAdjustVM : BaseViewModel() {
    val mSelectCar = MutableLiveData<scanMCode?>()
    val mBackList = MutableLiveData<String>()

    fun scanMCode(
        oper_flag: String?,
        scan_code: String?
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.scanMCode(
                    PscanMCode(
                        oper_flag, scan_code
                    )
                )

                if (response.isSuccess()) {
                    mSelectCar.postValue(response.data)

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

    fun changWareArea(
        oper_flag: String,
        scan_code: String,
        ware_area: String,
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.changWareArea(
                    PchangWareArea(
                        oper_flag, scan_code, ware_area
                    )
                )

                if (response.isSuccess()) {
                    mBackList.postValue("修改成功")

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