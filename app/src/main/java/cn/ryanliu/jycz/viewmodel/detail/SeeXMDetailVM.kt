package cn.ryanliu.jycz.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.SeeXMDetailBean
import cn.ryanliu.jycz.bean.prequest.PSubmitSaveIn
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:
 */
class SeeXMDetailVM : BaseViewModel() {
    val mSelect = MutableLiveData<MutableList<SeeXMDetailBean>?>()

    //获取卸车/装车当日和昨日统计数据
    fun scanFjCode(
        hand_task_id: String,
        oper_flag: String
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchTaskDetails(
                    PSubmitSaveIn(
                        hand_task_id,
                        oper_flag
                    )
                )

                if (response.isSuccess()) {
                    mSelect.postValue(response.data)

                } else {
                    mSelect.postValue(response.data)

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