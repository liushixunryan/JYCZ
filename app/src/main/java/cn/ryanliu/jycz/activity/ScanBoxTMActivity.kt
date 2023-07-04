package cn.ryanliu.jycz.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.BoxCode
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.bean.prequest.PcreateTCode2
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
        var p: MutableList<PcreateTCode2> = ArrayList();
        mDatabind.btnTj.setOnClickListener(object : OnSingleClickListener() {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(view: View?) {
                mAdapter.addData(BoxCode("BOXLBJ0120230411000000${mAdapter.data.size}"))
                p.add(PcreateTCode2("BOXLBJ0120230411000000${mAdapter.data.size}"))
                Log.e("sansuiban", "onSingleClick: ${mAdapter.data.size}")
                var s = mAdapter.data.size
                mDatabind.etZtjs.setText("${mAdapter.data.size}")
            }

        })

        mDatabind.btnSctm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mViewModel.createTCode2(p)
            }

        })

        mDatabind.btnPrinttm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                PrintBCCodeType.PrintTM("Q: 20", mXMAdapter.data[0].box_code)
            }

        })

        mDatabind.btnClose.setOnClickListener {
            finish()
        }


    }

    override fun createObserver() {
        var a: MutableList<BoxCode> = ArrayList();
        mViewModel.mBackList.observe(this) {
            mXMAdapter.setNewInstance(null)
            a.clear()
            a.add(BoxCode(it.toString()))
            mXMAdapter.setList(a)
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, ScanBoxTMActivity::class.java)
            context.startActivity(intent)
        }

    }
}