package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.OrderListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.OrderListBean
import cn.ryanliu.jycz.databinding.DetailActivityOrderListBinding
import cn.ryanliu.jycz.viewmodel.detail.OrderListVM

class OrderListActivity : BaseActivity<DetailActivityOrderListBinding, OrderListVM>() {
    lateinit var mAdapter: OrderListAdapter

    lateinit var selectBean: MutableList<OrderListBean>
    override fun layoutId(): Int = R.layout.detail_activity_order_list

    override fun initView() {
        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "订单明细"
        mAdapter = OrderListAdapter();
        mDatabind.zcmxRv.adapter = mAdapter

        var a = OrderListBean(1, "TRD1202305W0100137")
        var b = OrderListBean(2, "TRD1202305W0100137")
        var d = OrderListBean(3, "TRD1202305W0100137")
        var c = OrderListBean(4, "TRD1202305W0100137")
        selectBean.add(a)
        selectBean.add(b)
        selectBean.add(c)
        selectBean.add(d)

        mAdapter.setList(selectBean)
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, OrderListActivity::class.java)
            context.startActivity(intent)
        }

    }
}