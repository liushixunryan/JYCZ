package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.IndentNumAdapter
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.IndentNumBean
import cn.ryanliu.jycz.bean.SelectCarBean
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityDriverBinding
import cn.ryanliu.jycz.databinding.ActivityIndentNumBinding
import cn.ryanliu.jycz.viewmodel.DriverVM
import cn.ryanliu.jycz.viewmodel.IndentNumVM

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:订单数量
 */

class IndentNumActivity : BaseActivity<ActivityIndentNumBinding, IndentNumVM>() {
    private var pageModel: Int = 0
    private var handtaskid: Int = 0

    lateinit var mAdapter: IndentNumAdapter
    override fun layoutId(): Int = R.layout.activity_indent_num

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "订单数量"

        pageModel = intent.getIntExtra("edit", 0)
        handtaskid = intent.getIntExtra("handtaskid", 0)

        //默认全部订单
        mViewModel.searchCarOrders(
            handtaskid,
            if (pageModel == Constant.PageModel.XIECHE) {
                "卸车"
            } else {
                "装车"
            }, "全部订单"
        )

        mAdapter = IndentNumAdapter();
        mDatabind.ddsllbRv.adapter = mAdapter
        onClick()

    }


    private fun onClick() {
        mDatabind.qbddBtn.setOnClickListener {
            mViewModel.searchCarOrders(
                handtaskid,
                if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }, "全部订单"
            )
        }

        mDatabind.zkwsBtn.setOnClickListener {
            mViewModel.searchCarOrders(
                handtaskid,
                if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }, "只看未扫"
            )
        }

        mDatabind.zkysBtn.setOnClickListener {
            mViewModel.searchCarOrders(
                handtaskid,
                if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }, "只看已扫"
            )
        }

        mAdapter.setOnAllDeviceProcureClickListener {
            XMListActivity.launch(this@IndentNumActivity, pageModel, handtaskid, it.py_order_code)
        }

        mAdapter.setOnReadDeviceProcureClickListener {
            XMListActivity.launch(this@IndentNumActivity, pageModel, handtaskid, it.py_order_code)

        }
        mAdapter.setOnUnreadDeviceProcureClickListener {
            XMListActivity.launch(this@IndentNumActivity, pageModel, handtaskid, it.py_order_code)

        }
    }

    override fun createObserver() {
        mViewModel.mData.observe(this) {
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

        mViewModel.mDataBean.observe(this) {
            if (it.toString() != "null") {
                mDatabind.loadingLayout.showEmpty()
            } else {
                mDatabind.ddslTv.text = it.order_num.toString()
                mDatabind.zxsTv.text = it.goods_num.toString()
                mDatabind.ysmzxsTv.text = it.yes_scan_num.toString()
                mDatabind.wsxsTv.text = it.no_scan_num.toString()
            }

        }
    }

    companion object {
        fun launch(context: Context, pageModel: Int, handtaskid: Int) {
            val intent = Intent(context, IndentNumActivity::class.java)
            intent.putExtra("edit", pageModel)
            intent.putExtra("handtaskid", handtaskid)
            context.startActivity(intent)
        }

    }
}