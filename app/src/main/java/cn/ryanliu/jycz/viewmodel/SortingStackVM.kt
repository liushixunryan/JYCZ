package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PchangWareArea
import cn.ryanliu.jycz.bean.prequest.PdoSplitTmp
import cn.ryanliu.jycz.bean.prequest.PscanMCode
import cn.ryanliu.jycz.bean.scanMCode
import cn.ryanliu.jycz.util.ToastUtilsExt
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:
 */
class SortingStackVM : BaseViewModel() {
    val mSelectCar = MutableLiveData<scanMCode?>()
    val mBackList = MutableLiveData<String>()
    val mctList = MutableLiveData<String>()

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
                    ToastUtilsExt.info(response.msg.toString())
                    mBackList.postValue(response.msg.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    //拆托
    fun doSplitTmp(
        old_tp_code: String,
        oper_type: String,
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.doSplitTmp(
                    PdoSplitTmp(
                        old_tp_code, oper_type
                    )
                )
                if (response.isSuccess()) {
                    mctList.postValue("拆托成功")

                } else {
                    ToastUtilsExt.info(response.msg.toString())
                    mctList.postValue(response.msg.toString())
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