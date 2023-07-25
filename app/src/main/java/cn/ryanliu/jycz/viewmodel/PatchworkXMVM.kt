package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.createBoxCode1
import cn.ryanliu.jycz.bean.creategeneralboxcode
import cn.ryanliu.jycz.bean.creategeneraltpcode
import cn.ryanliu.jycz.bean.getProjectList
import cn.ryanliu.jycz.bean.prequest.PcreateBoxCode1
import cn.ryanliu.jycz.bean.prequest.Pcreategeneralboxcode
import cn.ryanliu.jycz.bean.prequest.PgetOilFactory
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:
 */
class PatchworkXMVM : BaseViewModel() {
    val mBackList = MutableLiveData<createBoxCode1?>()
    val mCode = MutableLiveData<MutableList<creategeneralboxcode>>()
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
    fun createTCode2(
        box_code_num: Int,
        tp_code: String,
         project_id: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.createBoxCode1(
                    PcreateBoxCode1(box_code_num, tp_code,project_id)
                )
                if (response.isSuccess()) {
                    mBackList.postValue(response.data)

                } else {
//                    mBackList.postValue(response.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun creategeneralboxcode(
        p: Pcreategeneralboxcode
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.create_general_boxcode(
                    p
                )
                if (response.isSuccess()) {
                    mCode.postValue(response.data!!)

                } else {
//                    mCode.postValue(response.data)
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