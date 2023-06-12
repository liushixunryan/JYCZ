package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.DetailActivityOrderBinding
import cn.ryanliu.jycz.viewmodel.detail.OrderDetailVM

/**
 * @Author: lsx
 * @Date: 2023/6/12
 * @Description:订单查询
 */
class OrderDetailActivity : BaseActivity<DetailActivityOrderBinding, OrderDetailVM>() {

    override fun layoutId(): Int = R.layout.detail_activity_order

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "订单查询"


        mDatabind.btnSelect.setOnClickListener {
            OrderListActivity.launch(this)
        }
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, OrderDetailActivity::class.java)
            context.startActivity(intent)
        }

    }
}