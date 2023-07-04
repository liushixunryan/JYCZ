package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.createBoxCode1
import cn.ryanliu.jycz.bean.prequest.PcreateBoxCode1
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:
 */
class PatchworkXMVM : BaseViewModel() {
    val mBackList = MutableLiveData<createBoxCode1?>()
    fun createTCode2(
        box_code_num: Int,
        tp_code: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.createBoxCode1(
                    PcreateBoxCode1(box_code_num, tp_code)
                )
                if (response.isSuccess()) {
                    mBackList.postValue(response.data)

                } else {
                    mBackList.postValue(response.data)
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