package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.scanMCode
import cn.ryanliu.jycz.common.constant.Constant.PageModel
import cn.ryanliu.jycz.databinding.ActivityAreaAdjustBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.AreaAdjustVM

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:库区调整
 */
class AreaAdjustActivity : BaseActivity<ActivityAreaAdjustBinding, AreaAdjustVM>() {
    var areaName = ""
    var areaId = ""
    var yAreaID = ""

    override fun layoutId(): Int = R.layout.activity_area_adjust

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库位调整"

        areaName = intent.getStringExtra("areaName").toString()
        areaId = intent.getStringExtra("areaId").toString()

        mDatabind.xkqTv.text = areaName

        onClick()
    }

    private fun onClick() {
        mDatabind.btnTj.setOnClickListener {
            mViewModel.scanMCode("库位调整", mDatabind.etSmtm.text.toString())
        }

        mDatabind.ysxmTv.setOnClickListener {
            XMListActivity.launch(this, PageModel.XIECHE, 0, mDatabind.khddhTv.text.toString())
        }

        mDatabind.xsTv.setOnClickListener {
            XMListActivity.launch(this, PageModel.XIECHE, 0, mDatabind.khddhTv.text.toString())
        }

        mDatabind.khddhTv.setOnClickListener {
            XMListActivity.launch(this, PageModel.XIECHE, 0, mDatabind.khddhTv.text.toString())

        }

        mDatabind.btnQrtz.setOnClickListener {
            if (mDatabind.xmtmhTv.text.toString() == "") {
                ToastUtilsExt.info("扫描条码不能为空")
            } else {
                mViewModel.changWareArea(
                    "库位调整",
                    mDatabind.xmtmhTv.text.toString(),
                    areaId
                )
            }

        }
    }

    override fun createObserver() {
        mViewModel.mSelectCar.observe(this) {
            mDatabind.xmtmhTv.text = it?.scan_code
            mDatabind.mddTv.text = it?.rec_area
            mDatabind.shrTv.text = it?.rec_man
            mDatabind.shdwTv.text = it?.rec_unit
            mDatabind.smlxTv.text = it?.scan_type
            mDatabind.xsTv.text = it?.tp_num.toString()
            mDatabind.khddhTv.text = it?.py_order_code
            mDatabind.ykqTv.text = it?.ware_area_name
            yAreaID = it?.ware_area.toString()
        }
    }

    companion object {
        fun launch(context: Context, ware_area_name: String, ware_area_id: String) {
            val intent = Intent(context, AreaAdjustActivity::class.java)
            intent.putExtra("areaName", ware_area_name)
            intent.putExtra("areaId", ware_area_id)
            context.startActivity(intent)
        }

    }
}