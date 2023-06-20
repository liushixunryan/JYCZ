package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.api.ApiService
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.bean.prequest.PconfirmTask
import cn.ryanliu.jycz.util.UploadUtil
import kotlinx.coroutines.launch
import java.io.File

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:
 */
class EntryPhotoVM : BaseViewModel() {
    //返回
    val mUrl = MutableLiveData<String>()
    val mBackList = MutableLiveData<String>()
    val mIndex = MutableLiveData<Int>()

    fun upload(file: File, index: Int) {
        viewModelScope.launch {
            try {
                val upload = UploadUtil.upload(file)
                mIndex.postValue(index)
                mUrl.postValue(upload.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                showNetErr(e)
            } finally {
                hideLoading()
            }
        }
    }

    fun confirmTask(
        hand_task_id: Int,
        oper_flag: String,
        scan_hand_img1: String,
        scan_hand_img2: String,
        scan_hand_img3: String,
        scan_hand_img4: String,
        scan_hand_img5: String,
        scan_hand_img6: String,
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = ApiService.apiService.confirmTask(
                    PconfirmTask(
                        hand_task_id,
                        oper_flag,
                        scan_hand_img1,
                        scan_hand_img2,
                        scan_hand_img3,
                        scan_hand_img4,
                        scan_hand_img5,
                        scan_hand_img6

                    )
                )

                if (response.isSuccess()) {
                    mBackList.postValue("成功")

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