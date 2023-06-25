package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.OrderListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.OrderListBean
import cn.ryanliu.jycz.bean.prequest.PsearchOrderDetails
import cn.ryanliu.jycz.databinding.DetailActivityOrderListBinding
import cn.ryanliu.jycz.viewmodel.detail.OrderListVM

class OrderListActivity : BaseActivity<DetailActivityOrderListBinding, OrderListVM>() {
    lateinit var mAdapter: OrderListAdapter
    lateinit var PsearchOrderDetails: PsearchOrderDetails
    override fun layoutId(): Int = R.layout.detail_activity_order_list

    override fun initView() {

        PsearchOrderDetails =
            intent.getSerializableExtra("PsearchOrderDetails") as PsearchOrderDetails

        mViewModel.searchOrderDetails(PsearchOrderDetails)

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "订单明细"
        mAdapter = OrderListAdapter();
        mDatabind.zcmxRv.adapter = mAdapter
    }

    override fun createObserver() {
        mViewModel.mSelect.observe(this) {
            if (it.isNullOrEmpty()) {
                mDatabind.loadingLayout.showEmpty()
            } else {
                mAdapter.setList(it)
                if (mAdapter.data.isEmpty()) {
                    mDatabind.loadingLayout.showEmpty()
                } else {
                    mDatabind.loadingLayout.showContent()
                }
            }
        }
    }

    companion object {
        fun launch(context: Context, PsearchOrderDetails: PsearchOrderDetails) {
            val intent = Intent(context, OrderListActivity::class.java)
            intent.putExtra("PsearchOrderDetails", PsearchOrderDetails)
            context.startActivity(intent)
        }

    }
}