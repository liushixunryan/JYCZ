package cn.ryanliu.jycz.activity.detail

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.SeeXMDetailAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.SeeXMDetailBean
import cn.ryanliu.jycz.common.constant.Constant.PageModel
import cn.ryanliu.jycz.databinding.DetailActivitySeeXmdetalBinding
import cn.ryanliu.jycz.viewmodel.detail.SeeXMDetailVM

class SeeXMDetalActivity : BaseActivity<DetailActivitySeeXmdetalBinding, SeeXMDetailVM>() {
    lateinit var mAdapter: SeeXMDetailAdapter
    var pageMode = ""
    var handTaskid = ""
    lateinit var selectBean: MutableList<SeeXMDetailBean>
    override fun layoutId(): Int = R.layout.detail_activity_see_xmdetal


    override fun initView() {
        handTaskid = intent.getStringExtra("handTaskid").toString()
        pageMode = intent.getStringExtra("edit").toString()

        mViewModel.scanFjCode(handTaskid, pageMode)

        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "箱码明细"
        mAdapter = SeeXMDetailAdapter();
        mDatabind.zcmxRv.adapter = mAdapter
    }

    override fun createObserver() {
        mViewModel.mSelect.observe(this) {
            if (it.isNullOrEmpty()) {
                mDatabind.loadingLayout.showEmpty()
            } else {
                mAdapter.setList(it)
                if (mAdapter.data.isEmpty()) {
                    mDatabind.loadingLayout.showEmpty()
                } else {
                    mDatabind.loadingLayout.showContent()
                }
            }
        }
    }

    companion object {
        fun launch(context: Context, handTaskid: String, pagemodel: String) {
            val intent = Intent(context, SeeXMDetalActivity::class.java)
            intent.putExtra("handTaskid", handTaskid)
            intent.putExtra("edit", pagemodel)
            context.startActivity(intent)
        }

    }
}