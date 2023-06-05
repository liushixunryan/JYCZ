package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.databinding.ActivityOnlyPrintTmactivityBinding
import cn.ryanliu.jycz.viewmodel.OnlyPrintTMVM

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:仅仅打印托码标签
 */
class OnlyPrintTMActivity : BaseActivity<ActivityOnlyPrintTmactivityBinding, OnlyPrintTMVM>() {

    lateinit var mAdapter: TMBQAdapter
    override fun layoutId(): Int = R.layout.activity_only_print_tmactivity

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "仅打印一个【托码】标签"

        mAdapter = TMBQAdapter();
        mDatabind.tmRv.adapter = mAdapter

        onClick()
    }

    private fun onClick() {
        mDatabind.btnSctm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mAdapter.addData(TMBQBean(1, "dsda111"))
            }

        })

        mDatabind.btnClose.setOnClickListener {
            finish()
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->


        }
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, OnlyPrintTMActivity::class.java)
            context.startActivity(intent)
        }

    }
}