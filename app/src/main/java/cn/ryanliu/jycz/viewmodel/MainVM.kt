package cn.ryanliu.jycz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.ryanliu.jycz.basic.BaseViewModel
import cn.ryanliu.jycz.util.UploadUtil
import kotlinx.coroutines.launch
import java.io.File

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class MainVM :BaseViewModel() {
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
}