package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.LoadingListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.LoadingListBean
import cn.ryanliu.jycz.databinding.DetailActivityLoadingListBinding
import cn.ryanliu.jycz.viewmodel.detail.LoadingListVM

/**
 * @Author: lsx
 * @Date: 2023/6/12
 * @Description:装车明细列表
 */

class LoadingListActivity : BaseActivity<DetailActivityLoadingListBinding, LoadingListVM>() {
    lateinit var mAdapter: LoadingListAdapter

    lateinit var selectBean: MutableList<LoadingListBean>
    override fun layoutId(): Int = R.layout.detail_activity_loading_list

    override fun initView() {
        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "装车明细"
        mAdapter = LoadingListAdapter();
        mDatabind.zcmxRv.adapter = mAdapter

        var a = LoadingListBean(1, "TRD1202305W0100137")
        var b = LoadingListBean(2, "TRD1202305W0100137")
        var d = LoadingListBean(3, "TRD1202305W0100137")
        var c = LoadingListBean(4, "TRD1202305W0100137")
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
            val intent = Intent(context, LoadingListActivity::class.java)
            context.startActivity(intent)
        }

    }
}