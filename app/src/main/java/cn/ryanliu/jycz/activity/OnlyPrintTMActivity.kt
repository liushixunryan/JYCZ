package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.BoxCode
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.databinding.ActivityOnlyPrintTmactivityBinding
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.OnlyPrintTMVM
import print.Print

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
                mViewModel.createTCode1(mDatabind.etZtjs.text.toString())

            }

        })

        mDatabind.btnClose.setOnClickListener {
            finish()
        }

        mDatabind.btnPrinttm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                try {
                    val printTM = PrintBCCodeType.PrintTM("Q: 20", mAdapter.data[0].box_code)
                    if (printTM == 1) {
                        //切纸
                        Print.GotoNextLabel()
                    } else {
                        ToastUtilsExt.info("打印错误")
                    }

                } catch (e: java.lang.Exception) {
                    Log.e(
                        "SDKSample",
                        java.lang.StringBuilder("Activity_Main --> onClickWIFI ").append(e.message)
                            .toString()
                    )
                }


            }

        })

    }

    override fun createObserver() {
        mViewModel.mBackList.observe(this) {
            mAdapter.setList(null)
            mAdapter.addData(0, BoxCode(it!!))
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, OnlyPrintTMActivity::class.java)
            context.startActivity(intent)
        }

    }
}