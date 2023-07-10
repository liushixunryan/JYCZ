package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityProjectBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.ProjectVM

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:项目预约
 */
class ProjectActivity : BaseActivity<ActivityProjectBinding, ProjectVM>() {
    var carNum = ""
    var areaId = ""
    private var pageModel: Int = 0
    private var hand_task_id: Int = 0
    override fun layoutId(): Int = R.layout.activity_project

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
            }, "项目预约", if (pageModel == Constant.PageModel.XIECHE) {
                "卸车任务"
            } else {
                "装车任务"
            }
        )

        onClick()
    }

    private fun onClick() {
        //点击订单数量
        mDatabind.indentNumTv.setOnClickListener {
            IndentNumActivity.launch(this@ProjectActivity, Constant.PageModel.XIECHE, hand_task_id)
        }
        //如果是托码就能点击
        mDatabind.xsTv.setOnClickListener {
            if (mDatabind.xmtmhTv.text == "托码") {
                XMListActivity.launch(
                    this,
                    pageModel,
                    hand_task_id,
                    mDatabind.wtdhTv.text.toString()
                )
            }
        }
        mDatabind.wtdhTv.setOnClickListener {
            XMListActivity.launch(this, pageModel, hand_task_id, mDatabind.wtdhTv.text.toString())
        }
        //点击提交
        mDatabind.btnTj.setOnClickListener {
            mViewModel.getScanInCode(
                hand_task_id, if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }, mDatabind.etSmxm.text.toString(),carNum,"项目预约",areaId
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

    override fun createObserver() {
        mViewModel.mData.observe(this) {
            if (it.toString() != "null") {
                mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：${it?.yes_scan_num}"
                mDatabind.inNavBar.tvNavRight.text = "库区：${it?.ware_area_name}"

                mDatabind.carDataTv.text = "${it?.car_number}    项目预约数量：${it?.reservation_num}"
                mDatabind.indentNumTv.text = "${it?.order_num}"
                mDatabind.zxsTv.text = "${it?.goods_num}"
                mDatabind.yszxsTv.text = "${it?.yes_scan_num}"
                mDatabind.wsxsTv.text = "${it?.no_scan_num}"
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
            mDatabind.mddTv.text = it?.rec_area.toString()
            mDatabind.smlxTv.text = it?.scan_type.toString()
            mDatabind.xsTv.text = it?.tp_num.toString()
            mDatabind.wtdhTv.text = it?.py_order_code.toString()

        }

        mViewModel.mBackList.observe(this) {
            ToastUtilsExt.info("提交成功")
            onBackPressed()
        }
    }

    companion object {
        fun launch(context: Context, carnumber: String, pageModel: Int,areaid:String) {
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra("carnumber", carnumber)
            intent.putExtra("edit", pageModel)
            intent.putExtra("areaid", areaid)
            context.startActivity(intent)
        }

    }
}