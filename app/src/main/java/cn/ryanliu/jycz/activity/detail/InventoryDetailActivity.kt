package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.prequest.PsearchInventList
import cn.ryanliu.jycz.bean.prequest.PsearchWareAreaChangeList
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.DetailActivityInventoryBinding
import cn.ryanliu.jycz.util.DialogUtil
import cn.ryanliu.jycz.viewmodel.detail.InventoryDetailVM
import com.blankj.utilcode.util.TimeUtils
import java.util.*

/**
 * @Author: lsx
 * @Date: 2023/6/13
 * @Description:盘点明细
 */
class InventoryDetailActivity :
    BaseActivity<DetailActivityInventoryBinding, InventoryDetailVM>() {

    override fun layoutId(): Int = R.layout.detail_activity_inventory

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "盘点明细"

        mDatabind.tzsjTv1.setText(TimeUtils.getNowString().substring(0, 11))
        mDatabind.tzsjTv2.setText(TimeUtils.getNowString().substring(0, 11))


        mDatabind.btnSelect.setOnClickListener {
            InventoryListActivity.launch(
                this, PsearchInventList(
                    mDatabind.tzsjTv1.text.toString(),
                    mDatabind.tzsjTv2.text.toString(),
                    mDatabind.pdrTv.text.toString(),
                    mDatabind.xmTv.text.toString()
                )
            )
        }

        mDatabind.tzsjTv1.setOnClickListener {
            val selectDate = Calendar.getInstance()

            DialogUtil.showTimeSelector(
                this, "选择时间", selectDate, null, null, Constant.TimePattern.PATTERN3
            ) { millis ->
                mDatabind.tzsjTv1.setText(
                    TimeUtils.millis2String(
                        millis,
                        Constant.TimePattern.PATTERN3
                    )
                )

            }
        }

        mDatabind.tzsjTv2.setOnClickListener {
            val selectDate = Calendar.getInstance()

            DialogUtil.showTimeSelector(
                this, "选择时间", selectDate, null, null, Constant.TimePattern.PATTERN3
            ) { millis ->
                mDatabind.tzsjTv2.setText(
                    TimeUtils.millis2String(
                        millis,
                        Constant.TimePattern.PATTERN3
                    )
                )

            }
        }

    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, InventoryDetailActivity::class.java)
            context.startActivity(intent)
        }

    }
}