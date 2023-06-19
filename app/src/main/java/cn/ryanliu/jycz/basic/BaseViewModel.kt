package cn.ryanliu.jycz.basic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.ryanliu.jycz.util.ToastUtilsExt
import com.zwl.libBase.bean.PageStatus
import com.zwl.libBase.bean.ProgressStatus
import kotlinx.coroutines.CancellationException

open class BaseViewModel : ViewModel() {
    val TAG: String = "sansuiban"
    val progress = MutableLiveData<Double>()

    val pageState = MutableLiveData<Int>()

    fun showLoading() {
        progress.postValue(ProgressStatus.BEGIN)
    }

    fun hideLoading() {
        progress.postValue(ProgressStatus.END)
    }

    fun isPageLoading(): Boolean {
        return PageStatus.LOADING == pageState.value
    }

    fun showPageLoading() {
        pageState.postValue(PageStatus.LOADING)
    }

    fun showPageEmpty() {
        pageState.postValue(PageStatus.EMPTY)
    }

    fun showPageContent() {
        pageState.postValue(PageStatus.CONTENT)
    }

    fun showPageError() {
        pageState.postValue(PageStatus.ERROR)
    }

    @Deprecated(
        "在此方法多余了", ReplaceWith(
            "BaseResponse.isSuccess()"
        )
    )
    fun showServerErr(message: String?) {
//        message?.let { ToastUtilsExt.error(it) }
    }

    fun showNetErr(e: Exception?) {
        if (e is CancellationException) {
            Log.d(TAG, "CancellationException: ")
        } else {
            ToastUtilsExt.info("请求出错")
        }
    }
}