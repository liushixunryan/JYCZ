package cn.ryanliu.jycz.util

import android.content.Context
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener


object DialogUtil {

    fun showSelectDialog(
        context: Context, title: String?, datas: Array<String>?,
        listener: OnSelectListener? = null,
    ) {
        XPopup.Builder(context) //                        .maxWidth(600)
            .maxHeight(1200).isDarkTheme(false).isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
            .asCenterList(
                title, datas
            ) { position, text ->
                listener?.onSelect(position, text)
            } //                        .bindLayout(R.layout.my_custom_attach_popup) //自定义布局
            .show()
    }


}