package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.EntryHandoverBean
import cn.ryanliu.jycz.bean.prequest.PsearchTask
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:
 */
class EntryHandoverVM : BaseViewModel() {
    val mSelectCar = MutableLiveData<MutableList<EntryHandoverBean>?>()


    fun searchTask(
        car_number: String? = null,
        oper_flag: String? = null,
        scan_user_name: String? = null
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchTask(
                    PsearchTask(
                        car_number,
                        oper_flag,
                        scan_user_name
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
}