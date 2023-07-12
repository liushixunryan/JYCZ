package cn.ryanliu.jycz.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
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
    var areaName = ""
    private var pageModel: Int = 0
    private var hand_task_id: Int = 0

    var smxs: Int = 0
    override fun layoutId(): Int = R.layout.activity_driver

    override fun initView() {
        mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：${smxs}"
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
        areaName = intent.getStringExtra("areaname").toString()

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
        mDatabind.etSmxm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                mViewModel.getScanInCode(
                    hand_task_id, if (pageModel == Constant.PageModel.XIECHE) {
                        "卸车"
                    } else {
                        "装车"
                    }, mDatabind.etSmxm.text.toString(), carNum, "司机预约", areaId
                )
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

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
                mDatabind.inNavBar.tvNavRight.text = "库区：${areaName}"

                mDatabind.carDataTv.text = "${it?.car_number}    项目预约数量：${it?.reservation_num}"
                mDatabind.ddslTv.text = "1"
                mDatabind.zxsTv.text = "${it?.goods_num}"
                mDatabind.yszxsTv.text = "${it?.yes_scan_num}"
                hand_task_id = it!!.hand_task_id

            }
        }

        mViewModel.mDatacode.observe(this) {
            smxs = smxs + it?.tp_num!!
            mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：${smxs}"
            mDatabind.iswarnTv.text = it?.scan_tips
            if (it?.scan_tips == "正常") {
                mDatabind.iswarnImg.setImageResource(R.mipmap.suc)
                mDatabind.iswarnTv.setTextColor(Color.parseColor("#333333"))
            } else {
                mDatabind.iswarnImg.setImageResource(R.mipmap.warn)
                mDatabind.iswarnTv.setTextColor(Color.parseColor("#FF0000"))
            }
            mDatabind.etSmxm.setText("")
            mDatabind.xmtmhTv.text = it?.scan_code.toString()

            mDatabind.yszxsTv.text = "${it?.yes_scan_num}"

        }

        mViewModel.mBackList.observe(this) {
            ToastUtilsExt.info("提交成功")
            MainActivity.launchClear(this)
        }
    }

    companion object {
        fun launch(
            context: Context,
            carnumber: String,
            pageModel: Int,
            areaid: String,
            areaname: String
        ) {
            val intent = Intent(context, DriverActivity::class.java)
            intent.putExtra("carnumber", carnumber)
            intent.putExtra("edit", pageModel)
            intent.putExtra("areaid", areaid)
            intent.putExtra("areaname", areaname)
            context.startActivity(intent)
        }

    }
}