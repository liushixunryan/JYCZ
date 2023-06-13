package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.InventoryListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.InventoryListBean
import cn.ryanliu.jycz.databinding.DetailActivityInventoryListBinding
import cn.ryanliu.jycz.viewmodel.detail.InventoryListVM

class InventoryListActivity :
    BaseActivity<DetailActivityInventoryListBinding, InventoryListVM>() {
    lateinit var mAdapter: InventoryListAdapter

    lateinit var selectBean: MutableList<InventoryListBean>
    override fun layoutId(): Int = R.layout.detail_activity_inventory_list

    override fun initView() {
        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库区调整明细"
        mAdapter = InventoryListAdapter();
        mDatabind.zcmxRv.adapter = mAdapter

        var a = InventoryListBean(1, "TRD1202305W0100137")
        var b = InventoryListBean(2, "TRD1202305W0100137")
        var d = InventoryListBean(3, "TRD1202305W0100137")
        var c = InventoryListBean(4, "TRD1202305W0100137")
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
            val intent = Intent(context, InventoryListActivity::class.java)
            context.startActivity(intent)
        }

    }
}