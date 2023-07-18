package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.InventoryListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.InventoryListBean
import cn.ryanliu.jycz.bean.prequest.PsearchInventList
import cn.ryanliu.jycz.bean.prequest.PsearchWareAreaChangeList
import cn.ryanliu.jycz.databinding.DetailActivityInventoryListBinding
import cn.ryanliu.jycz.viewmodel.detail.InventoryListVM

class InventoryListActivity :
    BaseActivity<DetailActivityInventoryListBinding, InventoryListVM>() {
    lateinit var mAdapter: InventoryListAdapter
    lateinit var mList: PsearchInventList
    override fun layoutId(): Int = R.layout.detail_activity_inventory_list

    override fun initView() {

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "盘点明细"
        mAdapter = InventoryListAdapter();
        mDatabind.zcmxRv.adapter = mAdapter

        mList = intent.getSerializableExtra("request") as PsearchInventList
        mViewModel.searchWareAreaChangeList(mList)
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
        fun launch(context: Context,request: PsearchInventList) {
            val intent = Intent(context, InventoryListActivity::class.java)
            intent.putExtra("request",request)
            context.startActivity(intent)
        }

    }
}