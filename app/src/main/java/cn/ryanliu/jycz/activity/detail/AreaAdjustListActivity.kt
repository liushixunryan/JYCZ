package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.AreaAdjustListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.prequest.PsearchWareAreaChangeList
import cn.ryanliu.jycz.databinding.DetailActivityAreaAdjustListBinding
import cn.ryanliu.jycz.viewmodel.detail.AreaAdjustListVM

class AreaAdjustListActivity :
    BaseActivity<DetailActivityAreaAdjustListBinding, AreaAdjustListVM>() {
    lateinit var mAdapter: AreaAdjustListAdapter
    lateinit var mList: PsearchWareAreaChangeList
    override fun layoutId(): Int = R.layout.detail_activity_area_adjust_list

    override fun initView() {


        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库区调整明细"

        mAdapter = AreaAdjustListAdapter();
        mDatabind.zcmxRv.adapter = mAdapter

        mList = intent.getSerializableExtra("searchWareAreaChangeList") as PsearchWareAreaChangeList
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
        fun launch(context: Context, searchWareAreaChangeList: PsearchWareAreaChangeList) {
            val intent = Intent(context, AreaAdjustListActivity::class.java)
            intent.putExtra("searchWareAreaChangeList", searchWareAreaChangeList)
            context.startActivity(intent)
        }

    }
}