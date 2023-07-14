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
        oper_flag: String? = null,
        srh_key_value: String? = null,
        srh_type: String? = null
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchTask(
                    PsearchTask(
                        oper_flag,
                        srh_key_value,
                        srh_type
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
}