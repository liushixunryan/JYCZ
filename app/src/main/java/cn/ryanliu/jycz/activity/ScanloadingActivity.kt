package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityScanloadingActivityBinding
import cn.ryanliu.jycz.viewmodel.ScanLoadingVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:扫码装车
 */
class ScanloadingActivity :
    BaseActivity<ActivityScanloadingActivityBinding, ScanLoadingVM>() {
    var reservationId = 0
    override fun layoutId(): Int = R.layout.activity_scanloading_activity

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "扫码装车"

        onClick();
    }

    private fun onClick() {
        mDatabind.btnSearchcar.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                val intent = Intent(this@ScanloadingActivity, SelectCarActivity::class.java)
                startActivityForResult(intent, SelectCarActivity.REQUEST_CODE_XXCL)
            }
        })


        mDatabind.etYylx.setOnClickListener {
            val isyesorno = listOf("司机预约", "项目预约")
            //创建一个xpopupview
            val attachPopupView: AttachPopupView = XPopup.Builder(context)
                .hasShadowBg(false)
                .popupAnimation(PopupAnimation.ScrollAlphaFromBottom)
                .popupWidth(mDatabind.etYylx.width ?: 0)
                .isCenterHorizontal(true) //是否与目标水平居中对齐
                .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
                .atView(it) // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(
                    isyesorno.toTypedArray(),
                    intArrayOf(),
                    { position, text ->
                        Log.e("sansuiban", "onCreate: ${position},${text}")
                        mDatabind.etYylx.text = text
                        reservationId = position
                    },
                    0,
                    0 /*, Gravity.LEFT*/
                )
            attachPopupView.show()
        }

        mDatabind.btnStartscan.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
//                if (mDatabind.etCph.text.toString().isNullOrEmpty()) {
//                    ToastUtilsExt.info("您未选择车辆信息")
//                    return
//                }

//                if (mDatabind.etYylx.text.toString().isNullOrEmpty()) {
//                    ToastUtilsExt.info("您未选择预约类型")
//                    return
//                }

                if (reservationId == 0) {
                    //跳转到司机
                    DriverActivity.launch(this@ScanloadingActivity)
                } else if (reservationId == 1) {
                    //跳转到项目
                    ProjectActivity.launch(this@ScanloadingActivity)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (SelectCarActivity.REQUEST_CODE_XXCL == requestCode) {
                mDatabind.etCph.setText(data?.getStringExtra("carNum") ?: "")
            }
        }
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, ScanloadingActivity::class.java)
            context.startActivity(intent)
        }

    }
}