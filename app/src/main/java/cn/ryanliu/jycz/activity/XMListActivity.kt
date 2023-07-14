package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.DetailXMListAdapter
import cn.ryanliu.jycz.adapter.XMListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.XMListBean
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityXmlistBinding
import cn.ryanliu.jycz.viewmodel.XMListVM

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:箱码列表
 */
class XMListActivity : BaseActivity<ActivityXmlistBinding, XMListVM>() {
    private var pageModel: Int = 0
    private var handtaskid: Int = 0
    private var pyordercode: String = ""
    private var scanTag: String = ""

    private var order_id: Int = 0

    private var invent_id: Int = 0
    private var invent_String: String = ""

    lateinit var mAdapter: XMListAdapter
    lateinit var mDetailAdapter: DetailXMListAdapter
    override fun layoutId(): Int = R.layout.activity_xmlist

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "箱码列表"

        order_id = intent.getIntExtra("order_id", 0)
        if (order_id != 0) {
            mViewModel.searchOrderBoxcodeList(order_id)

            mDetailAdapter = DetailXMListAdapter();
            mDatabind.xmlistRv.adapter = mDetailAdapter

        } else if (invent_id != 0) {
            invent_id = intent.getIntExtra("invent_id", 0)
            invent_String = intent.getStringExtra("invent_String").toString()
            mViewModel.searchInventBoxcodeList(invent_id, invent_String)
        } else {
            pageModel = intent.getIntExtra("edit", 0)
            handtaskid = intent.getIntExtra("handtaskid", 0)
            pyordercode = intent.getStringExtra("pyordercode").toString()
            scanTag = intent.getStringExtra("scan_tag").toString()

            mViewModel.getBoxcodeList(
                handtaskid, if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }, pyordercode,scanTag
            )


            mAdapter = XMListAdapter();
            mDatabind.xmlistRv.adapter = mAdapter

        }


        onClick();

    }

    private fun onClick() {
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

        //从详细列表进来的
        mViewModel.mDetailData.observe(this) {
            if (it.isNullOrEmpty()) {
                mDatabind.loadingLayout.showEmpty()
            } else {
                mDetailAdapter.setList(it)
                if (mDetailAdapter.data.isEmpty()) {
                    mDatabind.loadingLayout.showEmpty()
                } else {
                    mDatabind.loadingLayout.showContent()
                }
            }
        }

        //从盘点进来的
        mViewModel.mInventData.observe(this) {
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
        //正常
        fun launch(context: Context, pageModel: Int, handtaskid: Int, pyordercode: String,scan_tag:String) {
            val intent = Intent(context, XMListActivity::class.java)
            intent.putExtra("edit", pageModel)
            intent.putExtra("handtaskid", handtaskid)
            intent.putExtra("pyordercode", pyordercode)
            intent.putExtra("scan_tag", scan_tag)
            context.startActivity(intent)
        }

        //订单明细进来的
        fun launch(context: Context, order_id: Int) {
            val intent = Intent(context, XMListActivity::class.java)
            intent.putExtra("order_id", order_id)
            context.startActivity(intent)
        }

        //盘点明细进来的
        fun launch(context: Context, invent_id: Int, invent_String: String) {
            val intent = Intent(context, XMListActivity::class.java)
            intent.putExtra("invent_id", invent_id)
            intent.putExtra("invent_String", invent_String)
            context.startActivity(intent)
        }

    }
}