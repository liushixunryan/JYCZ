package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.prequest.PsearchTaskList
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.common.constant.Constant.PageModel
import cn.ryanliu.jycz.databinding.DetailActivityLoadingBinding
import cn.ryanliu.jycz.util.DialogUtil
import cn.ryanliu.jycz.viewmodel.detail.LoadingDetailVM
import com.blankj.utilcode.util.TimeUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import java.util.*

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:装车明细
 */
class LoadingDetailActivity : BaseActivity<DetailActivityLoadingBinding, LoadingDetailVM>() {

    override fun layoutId(): Int = R.layout.detail_activity_loading

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "装车明细"
        mDatabind.etDdzt.text = "全部"
        mDatabind.etZcwcsjd1.setText(TimeUtils.getNowString().substring(0, 11))
        mDatabind.etZcwcsjd2.setText(TimeUtils.getNowString().substring(0, 11))

        onClick()
    }

    private fun onClick() {
        mDatabind.btnSelect.setOnClickListener {
            LoadingListActivity.launch(
                this, Constant.PageModel.ZHUANGCHE, PsearchTaskList(
                    mDatabind.etXm.text.toString(),
                    mDatabind.etCph.text.toString(),
                    mDatabind.etSjxm.text.toString(),
                    "装车明细",
                    mDatabind.etDdzt.text.toString(),
                    mDatabind.etZcwcsjd1.text.toString(),
                    mDatabind.etZcwcsjd2.text.toString(),
                    mDatabind.etMdd.text.toString(),
                    mDatabind.etSmr.text.toString(),
                    mDatabind.etTm.text.toString()
                )
            )
        }

        mDatabind.etDdzt.setOnClickListener {
            val isyesorno = listOf("全部", "未完成", "已完成")
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

        mDatabind.etZcwcsjd1.setOnClickListener {
            val selectDate = Calendar.getInstance()

            DialogUtil.showTimeSelector(
                this, "选择时间", selectDate, null, null, Constant.TimePattern.PATTERN3
            ) { millis ->
                mDatabind.etZcwcsjd1.setText(
                    TimeUtils.millis2String(
                        millis,
                        Constant.TimePattern.PATTERN3
                    )
                )

            }
        }

        mDatabind.etZcwcsjd2.setOnClickListener {
            val selectDate = Calendar.getInstance()

            DialogUtil.showTimeSelector(
                this, "选择时间", selectDate, null, null, Constant.TimePattern.PATTERN3
            ) { millis ->
                mDatabind.etZcwcsjd2.setText(
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
        mViewModel.scanFjCode("装车明细")
    }

    override fun createObserver() {
        mViewModel.mSelect.observe(this) {
            mDatabind.zrzcsmTv.text = it?.yesterday_num.toString()
            mDatabind.drzcsmTv.text = it?.today_scan_num.toString()
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, LoadingDetailActivity::class.java)
            context.startActivity(intent)
        }

    }
}