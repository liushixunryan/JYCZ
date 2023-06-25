package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.LoadingListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.LoadingListBean
import cn.ryanliu.jycz.bean.prequest.PsearchTaskList
import cn.ryanliu.jycz.common.constant.Constant.PageModel
import cn.ryanliu.jycz.databinding.DetailActivityLoadingListBinding
import cn.ryanliu.jycz.viewmodel.detail.LoadingListVM

/**
 * @Author: lsx
 * @Date: 2023/6/12
 * @Description:装车/卸车明细列表
 */

class LoadingListActivity : BaseActivity<DetailActivityLoadingListBinding, LoadingListVM>() {
    lateinit var mAdapter: LoadingListAdapter

    private var pageModel: Int = 0
    lateinit var mList: PsearchTaskList

    override fun layoutId(): Int = R.layout.detail_activity_loading_list

    override fun initView() {
        pageModel = intent.getIntExtra("edit", 0)
        mList = intent.getSerializableExtra("psearchTaskList") as PsearchTaskList

        mViewModel.searchTaskList(mList)

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        if (pageModel == PageModel.XIECHE) {
            mDatabind.inNavBar.tvNavTitle.text = "卸车明细"
        } else {
            mDatabind.inNavBar.tvNavTitle.text = "装车明细"
        }
        mAdapter = LoadingListAdapter();
        mDatabind.zcmxRv.adapter = mAdapter

    }

    override fun createObserver() {
        mViewModel.mList.observe(this) {
            if (it.isNullOrEmpty()) {
                mDatabind.loadingLayout.showEmpty()
            } else {
                if (mDatabind.inNavBar.tvNavTitle.text == "卸车明细") {
                    for (i in it.indices) {
                        it[i].mx = "卸车明细"
                    }
                } else {
                    for (i in it.indices) {
                        it[i].mx = "装车明细"
                    }
                }

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
        fun launch(context: Context, pageModel: Int, mList: PsearchTaskList) {
            val intent = Intent(context, LoadingListActivity::class.java)
            intent.putExtra("edit", pageModel)
            intent.putExtra("psearchTaskList", mList)
            context.startActivity(intent)
        }

    }
}