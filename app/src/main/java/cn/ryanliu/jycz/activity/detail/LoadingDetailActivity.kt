package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.DetailActivityLoadingBinding
import cn.ryanliu.jycz.viewmodel.detail.LoadingDetailVM

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:装车明细
 */
class LoadingDetailActivity : BaseActivity<DetailActivityLoadingBinding, LoadingDetailVM>() {

    override fun layoutId(): Int = R.layout.detail_activity_loading

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "装车明细"

        mDatabind.btnSelect.setOnClickListener {
            LoadingListActivity.launch(this)
        }
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, LoadingDetailActivity::class.java)
            context.startActivity(intent)
        }

    }
}