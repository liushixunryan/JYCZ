package cn.ryanliu.jycz.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityDriverBinding
import cn.ryanliu.jycz.databinding.ActivityProjectBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.DriverVM
import cn.ryanliu.jycz.viewmodel.ProjectVM

class DriverActivity : BaseActivity<ActivityDriverBinding, DriverVM>() {
    var carNum = ""
    var areaId = ""
    private var pageModel: Int = 0
    private var hand_task_id: Int = 0

    override fun layoutId(): Int = R.layout.activity_driver

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "扫码卸车"

        mDatabind.inNavBar.tvNavCenter.visibility = View.VISIBLE
        mDatabind.inNavBar.tvNavRight.visibility = View.VISIBLE

        pageModel = intent.getIntExtra("edit", 0)
        carNum = intent.getStringExtra("carnumber").toString()
        areaId = intent.getStringExtra("areaid").toString()

        mViewModel.getScanOrders(
            carNum, if (pageModel == Constant.PageModel.XIECHE) {
                "卸车"
            } else {
                "装车"
            }, "司机预约", if (pageModel == Constant.PageModel.XIECHE) {
                "卸车任务"
            } else {
                "装车任务"
            }
        )

        onClick()
    }

    private fun onClick() {
        mDatabind.btnTj.setOnClickListener {
            mViewModel.getScanInCode(
                hand_task_id, if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }, mDatabind.etSmxm.text.toString(), carNum, "司机预约", areaId
            )
        }
        //确认卸车完成
        mDatabind.qrxcwcBtn.setOnClickListener {
            mViewModel.submitSaveIn(
                hand_task_id,
                if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                },
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {
        mViewModel.mData.observe(this) {
            if (it.toString() != "null") {
                mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：${it?.yes_scan_num}"
                mDatabind.inNavBar.tvNavRight.text = "库区：${it?.ware_area_name}"

                mDatabind.carDataTv.text = "${it?.car_number}    项目预约数量：${it?.reservation_num}"
                mDatabind.ddslTv.text = "1"
                mDatabind.zxsTv.text = "${it?.goods_num}"
                mDatabind.yszxsTv.text = "${it?.yes_scan_num}"
                hand_task_id = it!!.hand_task_id

            }
        }

        mViewModel.mDatacode.observe(this) {
            mDatabind.iswarnTv.text = it?.scan_tips
            if (it?.scan_tips == "正常") {
                mDatabind.iswarnImg.setImageResource(R.mipmap.suc)
                mDatabind.iswarnTv.setTextColor(Color.alpha(R.color.common_text_black))
            } else {
                mDatabind.iswarnImg.setImageResource(R.mipmap.warn)
                mDatabind.iswarnTv.setTextColor(Color.alpha(R.color.common_red))
            }

            mDatabind.xmtmhTv.text = it?.scan_code.toString()
        }

        mViewModel.mBackList.observe(this) {
            ToastUtilsExt.info("提交成功")
            onBackPressed()
        }
    }

    companion object {
        fun launch(context: Context, carnumber: String, pageModel: Int, areaid: String) {
            val intent = Intent(context, DriverActivity::class.java)
            intent.putExtra("carnumber", carnumber)
            intent.putExtra("edit", pageModel)
            intent.putExtra("areaid", areaid)
            context.startActivity(intent)
        }

    }
}