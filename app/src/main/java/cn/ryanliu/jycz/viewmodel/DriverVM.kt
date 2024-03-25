package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.ScanInCodeBean
import cn.ryanliu.jycz.bean.ScanOrdersBean
import cn.ryanliu.jycz.bean.prequest.PScanInCode
import cn.ryanliu.jycz.bean.prequest.PScanOrders
import cn.ryanliu.jycz.bean.prequest.PSubmitSaveIn
import cn.ryanliu.jycz.bean.prequest.PsaveAbor
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.util.UploadUtil
import kotlinx.coroutines.launch
import java.io.File

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:
 */
class DriverVM : BaseViewModel() {
    val mData = MutableLiveData<ScanOrdersBean?>()
    val mDatacode = MutableLiveData<ScanInCodeBean?>()

    //返回
    val mBackList = MutableLiveData<String>()

    fun getScanOrders(
        car_number: String, xzc: String,
        reservation_type: String,
        task_type: String
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.getScanOrders(
                    PScanOrders(
                        car_number,
                        xzc,
                        reservation_type,
                        task_type
                    )
                )

                if (response.isSuccess()) {
                    mData.postValue(response.data)

                } else {
                    mData.postValue(response.data)
                    ToastUtilsExt.info(response.msg)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun getScanInCode(
        hand_task_id: Int, xzc: String,
        scan_code: String, car_number: String,
        reservation_type: String,
        ware_area: String
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.getScanInCode(
                    PScanInCode(
                        hand_task_id, xzc,
                        scan_code,
                        car_number,
                        reservation_type,
                        ware_area
                    )
                )

                if (response.isSuccess()) {
                    mDatacode.postValue(response.data)

                } else {
                    mDatacode.postValue(response.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun submitSaveIn(
        hand_task_id: Int, xzc: String
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.submitSaveIn(
                    PSubmitSaveIn(
                        hand_task_id.toString(), xzc

                    )
                )

                if (response.isSuccess()) {
                    mBackList.postValue("成功")

                } else {
                    mBackList.postValue("失败")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }

    }


    //返回
    val mUrl = MutableLiveData<String>()
    val mycBackList = MutableLiveData<String>()
    val mIndex = MutableLiveData<Int>()

    fun upload(file: File, index: Int) {
        viewModelScope.launch {
            try {
                val upload = UploadUtil.YTupload(file)
                if (upload.isNullOrEmpty()) {
                    mIndex.postValue(7)
                    mUrl.postValue("失败")
                } else {
                    mIndex.postValue(index)
                    mUrl.postValue(upload.toString())
                }


            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }
    }

    fun saveAbor(
        bar_code: String,
        abor_txt: String,
        abor_img1: String?,
        abor_img2: String?,
        abor_img3: String?,
        abor_img4: String?
    ) {
        viewModelScope.launch {
            try {
                val response = ApiService.apiService.saveAborPhoto(
                    PsaveAbor(
                        bar_code,
                        abor_txt,
                        abor_img1,
                        abor_img2,
                        abor_img3, abor_img4
                    )
                )
                if (response.isSuccess()) {
                    mycBackList.postValue("提交成功")

                } else {
                    ToastUtilsExt.info(response.msg.toString())
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