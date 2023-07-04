package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.LoginResponse
import cn.ryanliu.jycz.bean.SelectCarBean
import cn.ryanliu.jycz.bean.prequest.PCarInfoIn1
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/6
 * @Description:
 */
class SelectCarVM : BaseViewModel() {
    val mSelectCar = MutableLiveData<MutableList<SelectCarBean>?>()


    fun getCarInfoIn1(car_number: String? = null,xzc:String) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.getCarInfoIn1(PCarInfoIn1(car_number,xzc))

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