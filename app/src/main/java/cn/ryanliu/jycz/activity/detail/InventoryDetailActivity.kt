package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.DetailActivityInventoryBinding
import cn.ryanliu.jycz.viewmodel.detail.InventoryDetailVM
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

        mDatabind.btnSelect.setOnClickListener {
            InventoryListActivity.launch(this)
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