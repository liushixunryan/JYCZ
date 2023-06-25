package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.prequest.PsearchOrderDetails
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.DetailActivityOrderBinding
import cn.ryanliu.jycz.util.DialogUtil
import cn.ryanliu.jycz.viewmodel.detail.OrderDetailVM
import com.blankj.utilcode.util.TimeUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import java.util.*

/**
 * @Author: lsx
 * @Date: 2023/6/12
 * @Description:订单查询
 */
class OrderDetailActivity : BaseActivity<DetailActivityOrderBinding, OrderDetailVM>() {

    override fun layoutId(): Int = R.layout.detail_activity_order

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "订单查询"

        mDatabind.etRwlx.text = "全部"
        mDatabind.etDdzt.text = "全部"

        onclick();
    }

    private fun onclick() {
        mDatabind.btnSelect.setOnClickListener {
            OrderListActivity.launch(
                this, PsearchOrderDetails(
                    mDatabind.etXm.text.toString(),
                    mDatabind.etCph.text.toString(),
                    mDatabind.etSjxm.text.toString(),
                    mDatabind.etDdzt.text.toString(),
                    mDatabind.etDdsj1.text.toString(),
                    mDatabind.ddsj2.text.toString(),
                    mDatabind.etMdd.text.toString(),
                    mDatabind.etRwlx.text.toString(),
                    mDatabind.etTm.text.toString()
                )
            )
        }

        mDatabind.etRwlx.setOnClickListener {
            val isyesorno = listOf(
                "全部",
                "卸车任务",
                "装车任务",
            )
            //创建一个xpopupview
            val attachPopupView: AttachPopupView = XPopup.Builder(context)
                .hasShadowBg(false)
                .popupAnimation(PopupAnimation.ScrollAlphaFromBottom)
                .popupWidth(mDatabind.etRwlx.width ?: 0)
                .isCenterHorizontal(true) //是否与目标水平居中对齐
                .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
                .atView(it) // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(
                    isyesorno.toTypedArray(),
                    intArrayOf(),
                    { position, text ->
                        mDatabind.etRwlx.text = text
                    },
                    0,
                    0 /*, Gravity.LEFT*/
                )
            attachPopupView.show()
        }

        mDatabind.etDdzt.setOnClickListener {
            val isyesorno = listOf(
                "全部",
                "卸车未完成",
                "卸车已完成",
                "入场交接未完成",
                "入场交接已完成",
                "装车未完成",
                "装车已完成",
                "出场交接未完成",
                "出场交接已完成"
            )
            //创建一个xpopupview
            val attachPopupView: AttachPopupView = XPopup.Builder(context)
                .hasShadowBg(false)
                .popupAnimation(PopupAnimation.ScrollAlphaFromBottom)
                .popupWidth(mDatabind.etDdzt.width ?: 0)
                .isCenterHorizontal(true) //是否与目标水平居中对齐
                .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
                .atView(it) // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(
                    isyesorno.toTypedArray(),
                    intArrayOf(),
                    { position, text ->
                        mDatabind.etDdzt.text = text
                    },
                    0,
                    0 /*, Gravity.LEFT*/
                )
            attachPopupView.show()
        }

        mDatabind.etDdsj1.setOnClickListener {
            val selectDate = Calendar.getInstance()

            DialogUtil.showTimeSelector(
                this, "选择时间", selectDate, null, null, Constant.TimePattern.PATTERN3
            ) { millis ->
                mDatabind.etDdsj1.setText(
                    TimeUtils.millis2String(
                        millis,
                        Constant.TimePattern.PATTERN3
                    )
                )

            }
        }

        mDatabind.ddsj2.setOnClickListener {
            val selectDate = Calendar.getInstance()

            DialogUtil.showTimeSelector(
                this, "选择时间", selectDate, null, null, Constant.TimePattern.PATTERN3
            ) { millis ->
                mDatabind.ddsj2.setText(
                    TimeUtils.millis2String(
                        millis,
                        Constant.TimePattern.PATTERN3
                    )
                )

            }
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.searchOrderCount()
    }

    override fun createObserver() {
        mViewModel.mSelect.observe(this) {
            mDatabind.drzcsmzxs.text = it?.today_out_scan_num.toString()
            mDatabind.drxcsmzxs.text = it?.today_in_scan_num.toString()
            mDatabind.zrxcsmzxs.text = it?.yesterday_in_num.toString()
            mDatabind.zrzcsmzxs.text = it?.yesterday_out_num.toString()
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, OrderDetailActivity::class.java)
            context.startActivity(intent)
        }

    }
}