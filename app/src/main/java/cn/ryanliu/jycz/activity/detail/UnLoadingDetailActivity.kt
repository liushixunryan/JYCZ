package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.DetailActivityUnLoadingDetailBinding
import cn.ryanliu.jycz.viewmodel.detail.LoadingDetailVM

/**
 * @Author: lsx
 * @Date: 2023/6/12
 * @Description:卸车明细
 */
class UnLoadingDetailActivity :
    BaseActivity<DetailActivityUnLoadingDetailBinding, LoadingDetailVM>() {

    override fun layoutId(): Int = R.layout.detail_activity_un_loading_detail

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "卸车明细"

        mDatabind.btnSelect.setOnClickListener {
            LoadingListActivity.launch(this)
        }
    }

    override fun createObserver() {
        mViewModel.mSelect.observe(this) {
            mDatabind.zrxcsmTv.text = it?.yesterday_num.toString()
            mDatabind.drxcsmTv.text = it?.today_scan_num.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.scanFjCode("卸车明细")
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, UnLoadingDetailActivity::class.java)
            context.startActivity(intent)
        }

    }
}