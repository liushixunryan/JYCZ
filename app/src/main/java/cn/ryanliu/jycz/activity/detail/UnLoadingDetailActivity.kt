package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.prequest.PsearchTaskList
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.DetailActivityUnLoadingDetailBinding
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
 * @Date: 2023/6/12
 * @Description:卸车明细
 */
class UnLoadingDetailActivity :
    BaseActivity<DetailActivityUnLoadingDetailBinding, LoadingDetailVM>() {

    override fun layoutId(): Int = R.layout.detail_activity_un_loading_detail

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "卸车明细"
        mDatabind.etDdzt.text = "全部"

        mDatabind.etXcwcsjd1.setText(TimeUtils.getNowString().substring(0, 11))
        mDatabind.etXcwcsjd2.setText(TimeUtils.getNowString().substring(0, 11))

        onClick()
    }

    private fun onClick() {
        mDatabind.btnSelect.setOnClickListener {
            LoadingListActivity.launch(
                this, Constant.PageModel.XIECHE, PsearchTaskList(
                    mDatabind.etXm.text.toString(),
                    mDatabind.etCph.text.toString(),
                    mDatabind.etSjxm.text.toString(),
                    "卸车明细",
                    mDatabind.etDdzt.text.toString(),
                    mDatabind.etXcwcsjd1.text.toString(),
                    mDatabind.etXcwcsjd2.text.toString(),
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


        mDatabind.etXcwcsjd1.setOnClickListener {
            val selectDate = Calendar.getInstance()

            DialogUtil.showTimeSelector(
                this, "选择时间", selectDate, null, null, Constant.TimePattern.PATTERN3
            ) { millis ->
                mDatabind.etXcwcsjd1.setText(
                    TimeUtils.millis2String(
                        millis,
                        Constant.TimePattern.PATTERN3
                    )
                )

            }
        }

        mDatabind.etXcwcsjd2.setOnClickListener {
            val selectDate = Calendar.getInstance()

            DialogUtil.showTimeSelector(
                this, "选择时间", selectDate, null, null, Constant.TimePattern.PATTERN3
            ) { millis ->
                mDatabind.etXcwcsjd2.setText(
                    TimeUtils.millis2String(
                        millis,
                        Constant.TimePattern.PATTERN3
                    )
                )

            }
        }
    }

    override fun createObserver() {
        mViewModel.mSelect.observe(this) {
            mDatabind.zrxcsmTv.text = it?.yesterday_num.toString()
            mDatabind.drxcsmTv.text = it?.today_scan_num.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.scanFjCode("卸车明细")
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, UnLoadingDetailActivity::class.java)
            context.startActivity(intent)
        }

    }
}