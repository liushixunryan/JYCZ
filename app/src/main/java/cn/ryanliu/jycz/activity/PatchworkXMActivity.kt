package cn.ryanliu.jycz.activity

import android.annotation.SuppressLint
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

    override fun layoutId(): Int = R.layout.activity_patchwork_xmactivity

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "补打【箱码】标签"

        if (intent.getStringExtra("ispt").toString() != "") {
            isSearch(false);
            mDatabind.xmtmhTv.text = intent.getStringExtra("ispt").toString()
        } else {
            isSearch(true);

        }

        mAdapter = TMBQAdapter();
        mDatabind.tmRv.adapter = mAdapter



        onClick()
    }

    @SuppressLint("ResourceAsColor")
    private fun isSearch(isClick: Boolean) {
        if (!isClick) {
            mDatabind.btnTj.setBackgroundColor(R.color.Gray9)
            mDatabind.etZtjs.setText("1")
        }

        mDatabind.etSmtm.isClickable = isClick
        mDatabind.etSmtm.isFocusable = isClick
        mDatabind.btnTj.isClickable = isClick
        mDatabind.btnTj.isFocusable = isClick
        mDatabind.etZtjs.isClickable = isClick
        mDatabind.etZtjs.isFocusable = isClick

    }


    private fun onClick() {
        mDatabind.btnTj.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {

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
        fun launch(context: Context, ispt: String) {
            val intent = Intent(context, PatchworkXMActivity::class.java)
            intent.putExtra("ispt", ispt)
            context.startActivity(intent)
        }


    }
}