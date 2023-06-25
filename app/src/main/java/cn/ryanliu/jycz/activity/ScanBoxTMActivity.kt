package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.databinding.ActivityScanBoxTmactivityBinding
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.viewmodel.ScanBoxTMVM

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:扫箱码后补打【托码】标签
 */
class ScanBoxTMActivity : BaseActivity<ActivityScanBoxTmactivityBinding, ScanBoxTMVM>() {
    lateinit var mAdapter: TMBQAdapter
    lateinit var mXMAdapter: TMBQAdapter
    override fun layoutId(): Int = R.layout.activity_scan_box_tmactivity

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "扫箱码后补打【托码】标签"

        mAdapter = TMBQAdapter();
        mDatabind.dyxmlbRv.adapter = mAdapter

        mXMAdapter = TMBQAdapter();
        mDatabind.tmRv.adapter = mXMAdapter

        onClick()
    }

    private fun onClick() {
        var a: MutableList<TMBQBean> = ArrayList();
        mDatabind.btnTj.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mAdapter.addData(TMBQBean(1, "BOXLBJ01202304110000001"))
            }

        })

        mDatabind.btnSctm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mXMAdapter.setNewInstance(null)
                a.clear()
                a.add(TMBQBean(1, "TLBJ012023041100001Q20"))
                mXMAdapter.setList(a)
            }

        })

        mDatabind.btnPrinttm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                PrintBCCodeType.PrintTM("Q: 20", mXMAdapter.data[0].bqname)
            }

        })

        mDatabind.btnClose.setOnClickListener {
            finish()
        }


    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, ScanBoxTMActivity::class.java)
            context.startActivity(intent)
        }

    }
}