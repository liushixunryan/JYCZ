package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.createoillabel
import cn.ryanliu.jycz.bean.prequest.PSearchbarcode
import cn.ryanliu.jycz.bean.prequest.Pcreateoillabel
import cn.ryanliu.jycz.bean.searchbarcode
import kotlinx.coroutines.launch

/**
 * Author: lsx
 * Description
 * Date:2024/2/19 15:55
 */
class RepairDamagedVM:BaseViewModel() {
    val mCode = MutableLiveData<searchbarcode>()
    val mData = MutableLiveData<createoillabel>()

    /**
     * 根据条码关键字后六位进行条码模糊查询
     */
    fun SearchBarcode(
        bar_code: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.SearchBarcode(
                    PSearchbarcode(
                        bar_code
                    )
                )
                if (response.isSuccess()) {
                    mCode.postValue(response.data!!)

                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun RebarCode(
        bar_code: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.RebarCode(
                    PSearchbarcode(
                        bar_code
                    )
                )
                if (response.isSuccess()) {
                    mData.postValue(response.data!!)

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