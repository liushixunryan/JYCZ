package cn.ryanliu.jycz.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.LoadingListBean
import cn.ryanliu.jycz.bean.prequest.PsearchTaskList
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:
 */
class LoadingListVM : BaseViewModel() {

    val mList = MutableLiveData<MutableList<LoadingListBean>?>()

    fun searchTaskList(
        PsearchTaskList: PsearchTaskList
    ) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.searchTaskList(
                    PsearchTaskList
                )

                if (response.isSuccess()) {
                    if (PsearchTaskList.oper_flag == "卸车明细") {
                        for (i in response.data!!.indices) {
                            response.data!![i].mx = "卸车明细"
                        }
                    } else {
                        for (i in response.data!!.indices) {
                            response.data!![i].mx = "装车明细"
                        }
                    }

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
}