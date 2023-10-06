package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.SelectCarBean
import cn.ryanliu.jycz.bean.prequest.PCarInfoIn1
import cn.ryanliu.jycz.bean.prequest.PIsVehicleCarNumber
import kotlinx.coroutines.launch

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:
 */
class ScanUnloadingVM : BaseViewModel() {
    val mSelectCar = MutableLiveData<MutableList<SelectCarBean>?>()


    fun getCarInfoIn2(car_number: String? = null) {
        viewModelScope.launch {

            try {
                showLoading()
                val response = ApiService.apiService.getCarInfoIn2(PCarInfoIn1(car_number,"卸车"))

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

    var mIsCarnumber = MutableLiveData<String>()

    fun IsVehicleCarNumber(car_number: String) {
        viewModelScope.launch {

            try {
                showLoading()
                val response =
                    ApiService.apiService.IsVehicleCarNumber(PIsVehicleCarNumber(car_number))

                if (response.isSuccess()) {
                    mIsCarnumber.postValue(response.data.toString())

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