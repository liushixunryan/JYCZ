package cn.ryanliu.jycz.view

import android.content.Context
import cn.ryanliu.jycz.R
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import java.lang.ref.WeakReference

object Loading {
    private var loadingPopup: WeakReference<LoadingPopupView>? = null
    fun show(context: Context) {
        dismiss()
        loadingPopup = WeakReference(XPopup.Builder(context)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .isLightNavigationBar(true)
            .hasShadowBg(false)
            .isViewMode(true) //
            .asLoading(null, R.layout.custom_loading_popup, LoadingPopupView.Style.Spinner)
//            .asLoading("加载中")
            .show() as LoadingPopupView)
    }

    fun dismiss() {
        loadingPopup?.get()?.dismiss()
        loadingPopup?.clear()
        loadingPopup = null
    }
}