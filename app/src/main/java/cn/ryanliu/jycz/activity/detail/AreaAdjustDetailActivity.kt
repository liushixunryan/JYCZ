package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.DetailActivityAreaAdjustBinding
import cn.ryanliu.jycz.viewmodel.detail.AreaAdjustDetailVM

/**
 * @Author: lsx
 * @Date: 2023/6/12
 * @Description:库区调整明细
 */
class AreaAdjustDetailActivity :
    BaseActivity<DetailActivityAreaAdjustBinding, AreaAdjustDetailVM>() {
    override fun layoutId(): Int = R.layout.detail_activity_area_adjust

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库区调整明细"

        mDatabind.btnSelect.setOnClickListener {
            AreaAdjustListActivity.launch(this)
        }
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, AreaAdjustDetailActivity::class.java)
            context.startActivity(intent)
        }

    }
}