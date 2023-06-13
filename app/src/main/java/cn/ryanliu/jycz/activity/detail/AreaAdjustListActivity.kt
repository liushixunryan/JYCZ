package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.AreaAdjustListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.AreajustListBean
import cn.ryanliu.jycz.databinding.DetailActivityAreaAdjustListBinding
import cn.ryanliu.jycz.viewmodel.detail.AreaAdjustListVM

class AreaAdjustListActivity :
    BaseActivity<DetailActivityAreaAdjustListBinding, AreaAdjustListVM>() {
    lateinit var mAdapter: AreaAdjustListAdapter

    lateinit var selectBean: MutableList<AreajustListBean>
    override fun layoutId(): Int = R.layout.detail_activity_area_adjust_list

    override fun initView() {
        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库区调整明细"
        mAdapter = AreaAdjustListAdapter();
        mDatabind.zcmxRv.adapter = mAdapter

        var a = AreajustListBean(1, "TRD1202305W0100137")
        var b = AreajustListBean(2, "TRD1202305W0100137")
        var d = AreajustListBean(3, "TRD1202305W0100137")
        var c = AreajustListBean(4, "TRD1202305W0100137")
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
            val intent = Intent(context, AreaAdjustListActivity::class.java)
            context.startActivity(intent)
        }

    }
}