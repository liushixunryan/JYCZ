package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivitySortingStackBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.SortingStackVM

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:分拣码放
 */
class SortingStackActivity : BaseActivity<ActivitySortingStackBinding, SortingStackVM>() {
    var AreaID = ""
    override fun layoutId(): Int = R.layout.activity_sorting_stack

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "分拣码放"

        onClick()
    }

    private fun onClick() {
        //点击搜索
        mDatabind.btnTj.setOnClickListener {
            mViewModel.scanMCode("分拣码放", mDatabind.etSmtm.text.toString())
        }

        //点击托码
        mDatabind.ctBtn.setOnClickListener {
            if (mDatabind.smlxTv.text == "托码") {
                mViewModel.doSplitTmp(mDatabind.xmtmhTv.text.toString(), "0")
            }

        }

        mDatabind.xsTv.setOnClickListener {
//            XMListActivity.launch(this)
        }

        mDatabind.wtdhTv.setOnClickListener {
//            XMListActivity.launch(this)

        }

        mDatabind.cxzdkqTv.setOnClickListener {
            val intent = Intent(this@SortingStackActivity, SelectAreaActivity::class.java)
            startActivityForResult(intent, SelectAreaActivity.REQUEST_CODE_XXKQ)
        }

        mDatabind.ptBtn.setOnClickListener {
            PintoActivity.launch(
                this,
                mDatabind.kqTv.text.toString(),
                AreaID
            )
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
            mDatabind.wtdhTv.text = it?.py_order_code
            mDatabind.kqTv.text = it?.ware_area_name
            AreaID = it?.ware_area.toString()
        }

        mViewModel.mBackList.observe(this) {
            if (it == "拆托成功") {
                ToastUtilsExt.info("拆托成功")
            } else {
                mDatabind.kqTv.setText(areaName)
            }
        }

    }

    var areaName = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (SelectAreaActivity.REQUEST_CODE_XXKQ == requestCode) {
                mViewModel.changWareArea(
                    "分拣码放",
                    mDatabind.xmtmhTv.text.toString(),
                    (data?.getStringExtra("areaName") ?: "")
                )
                areaName = data?.getStringExtra("areaName") ?: ""
                AreaID = data?.getIntExtra("areaId", 0).toString()
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, SortingStackActivity::class.java)
            context.startActivity(intent)
        }

    }
}