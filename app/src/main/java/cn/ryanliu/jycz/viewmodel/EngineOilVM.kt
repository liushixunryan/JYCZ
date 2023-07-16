package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.createoillabel
import cn.ryanliu.jycz.bean.getOilFactory
import cn.ryanliu.jycz.bean.prequest.PcreateTboxCode1
import cn.ryanliu.jycz.bean.prequest.Pcreateoillabel
import cn.ryanliu.jycz.bean.prequest.PgetOilFactory
import cn.ryanliu.jycz.bean.prequest.PsearchOilModel
import cn.ryanliu.jycz.bean.searchOilModel
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:
 */
class EngineOilVM : BaseViewModel() {
    val mOilList = MutableLiveData<MutableList<getOilFactory>?>()
    val mList = MutableLiveData<MutableList<searchOilModel>?>()
    val mCode = MutableLiveData<createoillabel>()
    fun getOilFactory(
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.getOilFactory(
                    PgetOilFactory()
                )
                if (response.isSuccess()) {
                    mOilList.postValue(response.data)

                } else {
                    mOilList.postValue(response.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }


    fun searchOilModel(
        label_model: String?,
        brand_name: String?
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchOilModel(
                    PsearchOilModel(
                        label_model, brand_name
                    )
                )
                if (response.isSuccess()) {
                    mList.postValue(response.data)

                } else {
                    mList.postValue(response.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }


    fun createoillabel(
        bar_code: String,
        model: String,
        name: String,
        num: Int
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.create_oil_label(
                    Pcreateoillabel(
                        bar_code,
                        model,
                        name,
                        num
                    )
                )
                if (response.isSuccess()) {
                    mCode.postValue(response.data!!)

                } else {
//                    mOilList.postValue(response.data)
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