package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.SeeXMDetailAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.SeeXMDetailBean
import cn.ryanliu.jycz.databinding.DetailActivitySeeXmdetalBinding
import cn.ryanliu.jycz.viewmodel.detail.SeeXMDetailVM

class SeeXMDetalActivity : BaseActivity<DetailActivitySeeXmdetalBinding, SeeXMDetailVM>() {
    lateinit var mAdapter: SeeXMDetailAdapter

    lateinit var selectBean: MutableList<SeeXMDetailBean>
    override fun layoutId(): Int = R.layout.detail_activity_see_xmdetal


    override fun initView() {
        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "箱码明细"
        mAdapter = SeeXMDetailAdapter();
        mDatabind.zcmxRv.adapter = mAdapter

        var a = SeeXMDetailBean(1, "TRD1202305W0100137")
        var b = SeeXMDetailBean(2, "TRD1202305W0100137")
        var d = SeeXMDetailBean(3, "TRD1202305W0100137")
        var c = SeeXMDetailBean(4, "TRD1202305W0100137")
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
            val intent = Intent(context, SeeXMDetalActivity::class.java)
            context.startActivity(intent)
        }

    }
}