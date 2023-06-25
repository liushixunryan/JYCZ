package cn.ryanliu.jycz.util

import android.content.Context
import cn.ryanliu.jycz.common.constant.Constant.TimePattern.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupPosition
import com.lxj.xpopup.interfaces.OnSelectListener
import java.util.*


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

    fun showBottomSelectDialog(
        context: Context, title: String?, datas: Array<String>?,
        listener: OnSelectListener? = null,
    ) {
        XPopup.Builder(context) //                        .maxWidth(600)
            .maxHeight(1200).popupPosition(PopupPosition.Bottom).isDarkTheme(false)
            .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
            .asBottomList(
                title, datas
            ) { position, text ->
                listener?.onSelect(position, text)
            } //                        .bindLayout(R.layout.my_custom_attach_popup) //自定义布局
            .show()
    }

    fun showTimeSelector(
        context: Context,
        title: String = "选择时间",
        selectDate: Calendar? = null,
        startDate: Calendar? = null,
        endDate: Calendar? = null,
        pattern: String = PATTERN2,
        listener: ((Long) -> Unit)? = null,
    ) {
        val displayList: MutableList<Int> = mutableListOf()
        when (pattern) {
            PATTERN1 -> {
                displayList.add(DateTimeConfig.YEAR)
                displayList.add(DateTimeConfig.MONTH)
                displayList.add(DateTimeConfig.DAY)
                displayList.add(DateTimeConfig.HOUR)
                displayList.add(DateTimeConfig.MIN)
                displayList.add(DateTimeConfig.SECOND)
            }
            PATTERN2 -> {
                displayList.add(DateTimeConfig.YEAR)
                displayList.add(DateTimeConfig.MONTH)
                displayList.add(DateTimeConfig.DAY)
                displayList.add(DateTimeConfig.HOUR)
                displayList.add(DateTimeConfig.MIN)
            }
            PATTERN6 -> {
                displayList.add(DateTimeConfig.YEAR)
                displayList.add(DateTimeConfig.MONTH)
                displayList.add(DateTimeConfig.DAY)
                displayList.add(DateTimeConfig.HOUR)
            }
            PATTERN3 -> {
                displayList.add(DateTimeConfig.YEAR)
                displayList.add(DateTimeConfig.MONTH)
                displayList.add(DateTimeConfig.DAY)
            }
            PATTERN4 -> {
                displayList.add(DateTimeConfig.YEAR)
                displayList.add(DateTimeConfig.MONTH)
            }
        }

        val dialogBuilder =
            CardDatePickerDialog.builder(context).setTitle(title).setDisplayType(displayList)
                .setBackGroundModel(CardDatePickerDialog.CARD).showBackNow(false)
                .setWrapSelectorWheel(displayList,true)
                .setTouchHideable(true)
                .showDateLabel(true)
                .showFocusDateInfo(false).setOnChoose("选择") {
                    listener?.invoke(it)
                }.setOnCancel("关闭") {}
        selectDate?.let {
            dialogBuilder.setDefaultTime(it.timeInMillis)
        }
        startDate?.let {
            dialogBuilder.setMinTime(it.timeInMillis)
        }
        endDate?.let {
            dialogBuilder.setMaxTime(it.timeInMillis)
        }
        val dialog = dialogBuilder.build()
        dialog.show()
        //重点 需要在dialog show 方法后
        //得到 BottomSheetDialog 实体，设置其 isHideable 为 fasle
        (dialog as BottomSheetDialog).behavior.isHideable = false
    }


}