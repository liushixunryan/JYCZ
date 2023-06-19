package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivitySortingStackBinding
import cn.ryanliu.jycz.viewmodel.SortingStackVM

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:分拣码放
 */
class SortingStackActivity : BaseActivity<ActivitySortingStackBinding, SortingStackVM>() {

    override fun layoutId(): Int = R.layout.activity_sorting_stack

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "分拣码放"

        onClick()
    }

    private fun onClick() {
        mDatabind.xsTv.setOnClickListener {
//            XMListActivity.launch(this)
        }

        mDatabind.wtdhTv.setOnClickListener {
//            XMListActivity.launch(this)

        }

        mDatabind.cxzdkqTv.setOnClickListener {
            val intent = Intent(this@SortingStackActivity, SelectAreaActivity::class.java)
            startActivityForResult(intent, SelectAreaActivity.REQUEST_CODE_XXKQ)
        }

        mDatabind.ptBtn.setOnClickListener {
            PintoActivity.launch(this)
        }
    }

    override fun createObserver() {


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (SelectAreaActivity.REQUEST_CODE_XXKQ == requestCode) {
                mDatabind.kqTv.setText(data?.getStringExtra("areaName") ?: "")
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, SortingStackActivity::class.java)
            context.startActivity(intent)
        }

    }
}