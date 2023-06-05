package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.databinding.ActivityPatchworkXmactivityBinding
import cn.ryanliu.jycz.databinding.ActivityScanBoxTmactivityBinding
import cn.ryanliu.jycz.viewmodel.PatchworkXMVM
import cn.ryanliu.jycz.viewmodel.ScanBoxTMVM

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:补打【箱码】标签
 */
class PatchworkXMActivity : BaseActivity<ActivityPatchworkXmactivityBinding, PatchworkXMVM>() {
    lateinit var mAdapter: TMBQAdapter
    lateinit var mXMAdapter: TMBQAdapter

    override fun layoutId(): Int = R.layout.activity_patchwork_xmactivity

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "补打【箱码】标签"

        mAdapter = TMBQAdapter();
        mDatabind.tmRv.adapter = mAdapter

        mXMAdapter = TMBQAdapter();
        mDatabind.dyxmlbRv.adapter = mXMAdapter

        onClick()
    }

    private fun onClick() {
        mDatabind.btnTj.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mXMAdapter.addData(TMBQBean(1, "dsda111"))
            }

        })

        mDatabind.btnScxm.setOnClickListener(object : OnSingleClickListener() {
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
            val intent = Intent(context, PatchworkXMActivity::class.java)
            context.startActivity(intent)
        }

    }
}