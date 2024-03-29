package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.SelectCarBean
import cn.ryanliu.jycz.bean.creategeneralboxcode
import cn.ryanliu.jycz.bean.creategeneraltpcode
import cn.ryanliu.jycz.bean.getProjectList
import cn.ryanliu.jycz.bean.prequest.PcreateTboxCode1
import cn.ryanliu.jycz.bean.prequest.Pcreategeneralboxcode
import cn.ryanliu.jycz.bean.prequest.Pcreategeneraltpcode
import cn.ryanliu.jycz.bean.prequest.PgetOilFactory
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class OnlyPrintTMVM : BaseViewModel() {
    val mBackList = MutableLiveData<String?>()
    val mCode = MutableLiveData<creategeneraltpcode>()

    val mSelect = MutableLiveData<MutableList<getProjectList>?>()


    fun getProjectList() {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.getProjectList(
                    PgetOilFactory()
                )
                if (response.isSuccess()) {
                    mSelect.postValue(response.data!!)

                } else {
//                    mCode.postValue(response.data!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }


    fun creategeneraltpcode(
        tp_code: String,
        tp_code_num: Int
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.create_general_tpcode(
                    Pcreategeneraltpcode(tp_code, tp_code_num)
                )
                if (response.isSuccess()) {
                    mCode.postValue(response.data!!)

                } else {
//                    mCode.postValue(response.data!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun createTCode1(
        tp_num: String, project_id: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.createTCode1(
                    PcreateTboxCode1(
                        tp_num, project_id
                    )
                )
                if (response.isSuccess()) {
                    mBackList.postValue(response.data.toString())

                } else {
                    mBackList.postValue(response.data.toString())
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