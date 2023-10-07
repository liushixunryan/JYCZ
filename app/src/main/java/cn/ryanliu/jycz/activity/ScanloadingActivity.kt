package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.common.constant.Constant.PageModel
import cn.ryanliu.jycz.databinding.ActivityScanloadingActivityBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.ScanLoadingVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import wang.relish.widget.vehicleedittext.VehicleKeyboardHelper

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:扫码装车
 */
class ScanloadingActivity : BaseActivity<ActivityScanloadingActivityBinding, ScanLoadingVM>() {
    var reservationId = 0
    var mareaID = ""
    override fun layoutId(): Int = R.layout.activity_scanloading_activity

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "扫码装车"
        mDatabind.etYylx.text = "司机预约"

        VehicleKeyboardHelper.bind(mDatabind.etCph);
        onClick();

    }

    private fun onClick() {
        mDatabind.btnSearchcar.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mViewModel.getCarInfoIn2(mDatabind.etCph.text.toString())
            }
        })
        //选择车辆
        mDatabind.btnSelectcar.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                val intent = Intent(this@ScanloadingActivity, SelectCarActivity::class.java)
                intent.putExtra("edit", PageModel.ZHUANGCHE)
                startActivityForResult(intent, SelectCarActivity.REQUEST_CODE_XXCL)
            }
        })


//        mDatabind.etYylx.setOnClickListener {
//            val isyesorno = listOf("司机预约", "项目预约")
//            //创建一个xpopupview
//            val attachPopupView: AttachPopupView = XPopup.Builder(context)
//                .hasShadowBg(false)
//                .popupAnimation(PopupAnimation.ScrollAlphaFromBottom)
//                .popupWidth(mDatabind.etYylx.width ?: 0)
//                .isCenterHorizontal(true) //是否与目标水平居中对齐
//                .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
//                .atView(it) // 依附于所点击的View，内部会自动判断在上方或者下方显示
//                .asAttachList(
//                    isyesorno.toTypedArray(),
//                    intArrayOf(),
//                    { position, text ->
//                        Log.e("sansuiban", "onCreate: ${position},${text}")
//                        mDatabind.etYylx.text = text
//                        reservationId = position
//                    },
//                    0,
//                    0 /*, Gravity.LEFT*/
//                )
//            attachPopupView.show()
//        }

        mDatabind.btnStartscan.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (mDatabind.etCph.text.toString().isNullOrEmpty()) {
                    ToastUtilsExt.info("您未选择车辆信息")
                    return
                }

                if (mDatabind.etYylx.text.toString().isNullOrEmpty()) {
                    ToastUtilsExt.info("您未选择预约类型")
                    return
                }
                mViewModel.IsVehicleCarNumber(mDatabind.etCph.text.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (SelectCarActivity.REQUEST_CODE_XXCL == requestCode) {
                mDatabind.etCph.setText(data?.getStringExtra("carNum") ?: "")
                mViewModel.getCarInfoIn2(mDatabind.etCph.text.toString())
                mareaID = data?.getStringExtra("areaId") ?: ""
            }
        }
    }

    override fun createObserver() {
        mViewModel.mIsCarnumber.observe(this) {

            if (reservationId == 0) {
                //跳转到司机
                DriverActivity.launch(
                    this@ScanloadingActivity,
                    mDatabind.etCph.text.toString(),
                    Constant.PageModel.ZHUANGCHE,
                    mareaID,
                    ""
                )
            } else if (reservationId == 1) {
                //跳转到项目
                ProjectActivity.launch(
                    this@ScanloadingActivity,
                    mDatabind.etCph.text.toString(),
                    Constant.PageModel.ZHUANGCHE,
                    mareaID,
                    ""
                )
            }
        }


        mViewModel.mSelectCar.observe(this) {
            if (it.isNullOrEmpty()) {
                mDatabind.driverLl.visibility = View.GONE
                mDatabind.projectLl.visibility = View.GONE
                mDatabind.hintLl.visibility = View.VISIBLE
                mDatabind.hintTv.text = "未搜索到任何车辆信息，点击 “开始扫码卸车” 将按司机预约执行。"
            } else {

                if (it[0].reservation_type == "项目预约") {
                    mDatabind.xmYqxx.text = it[0].site_name
                    mDatabind.xmDrivernameTv.text = it[0].driver_name
                    mDatabind.xmLicencenumTv.text = it[0].car_number
                    mDatabind.xmRwlxTv.text = it[0].in_report_type
                    mDatabind.xmHwslTv.text = it[0].goods_num.toString()
                    mDatabind.ddtimeTv.text = it[0].order_time
                    mDatabind.etYylx.text = "项目预约"
                    reservationId = 1
                    mDatabind.driverLl.visibility = View.GONE
                    mDatabind.projectLl.visibility = View.VISIBLE
                    mDatabind.hintLl.visibility = View.GONE
                } else {
                    mDatabind.yqxx.text = it[0].site_name
                    mDatabind.drivernameTv.text = it[0].driver_name
                    mDatabind.phonenumTv.text = it[0].phone
                    mDatabind.licencenumTv.text = it[0].car_number
                    mDatabind.cartypeTv.text = it[0].car_model
                    mDatabind.rcbbtypeTv.text = it[0].in_report_type
                    mDatabind.hwnumTv.text = it[0].goods_num.toString()
                    mDatabind.yyddtimeTv.text = it[0].maybe_arrive_time
                    mDatabind.sjrctimeTv.text = it[0].insite_time
                    mDatabind.hwxxTv.text = it[0].goods_info
                    mDatabind.etYylx.text = "司机预约"
                    reservationId = 0

                    mDatabind.driverLl.visibility = View.VISIBLE
                    mDatabind.projectLl.visibility = View.GONE
                    mDatabind.hintLl.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, ScanloadingActivity::class.java)
            context.startActivity(intent)
        }

    }
}