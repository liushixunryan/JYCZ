package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PconfirmTask
import cn.ryanliu.jycz.bean.prequest.PsaveAbor
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.util.UploadUtil
import kotlinx.coroutines.launch
import java.io.File

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class MainVM : BaseViewModel() {
    //返回
    val mUrl = MutableLiveData<String>()
    val mBackList = MutableLiveData<String>()
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
                    mBackList.postValue("提交成功")

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