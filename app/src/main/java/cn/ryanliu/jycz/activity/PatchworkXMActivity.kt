package cn.ryanliu.jycz.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.bean.prequest.Boxcode
import cn.ryanliu.jycz.bean.prequest.Pcreategeneralboxcode
import cn.ryanliu.jycz.databinding.ActivityPatchworkXmactivityBinding
import cn.ryanliu.jycz.databinding.ActivityScanBoxTmactivityBinding
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.PatchworkXMVM
import cn.ryanliu.jycz.viewmodel.ScanBoxTMVM
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:补打【箱码】标签
 */
class PatchworkXMActivity : BaseActivity<ActivityPatchworkXmactivityBinding, PatchworkXMVM>() {
    lateinit var mAdapter: TMBQAdapter

    lateinit var list: Pcreategeneralboxcode
    lateinit var boxlist: MutableList<Boxcode>

    override fun layoutId(): Int = R.layout.activity_patchwork_xmactivity

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "补打【箱码】标签"
        mDatabind.etZtjs.setText("1")
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
                mDatabind.xmtmhTv.text = mDatabind.etSmtm.text
            }

        })

        mDatabind.btnScxm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mViewModel.createTCode2(
                    mDatabind.etZtjs.text.toString().toInt(),
                    mDatabind.xmtmhTv.text.toString()
                )

            }

        })
        mDatabind.btnPrinttm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (!mAdapter.data.isNullOrEmpty()) {
                    boxlist = ArrayList()
                    for (i in mAdapter.data.indices) {
                        boxlist.add(Boxcode(mAdapter.data[i].box_code))
                    }


                    list = Pcreategeneralboxcode(boxlist)

                    mViewModel.creategeneralboxcode(
                        list
                    )
                } else {
                    ToastUtilsExt.info("暂无箱码信息")
                }
            }
        })

        mDatabind.btnClose.setOnClickListener {
            finish()
        }

    }

    override fun createObserver() {
        mViewModel.mBackList.observe(this) {
            if (it != null) {
                mAdapter.setList(it.box_code_list)
            }
        }

        mViewModel.mCode.observe(this) {
            try {
                for (i in mAdapter.data.indices) {
                    val printTM = PrintBCCodeType.PrintXM(it[i].img_data)
                    if (printTM != -1) {
                        //切纸
                        Print.GotoNextLabel()
                    } else {
                        ToastUtilsExt.info("打印错误")
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.e(
                    "SDKSample",
                    java.lang.StringBuilder("Activity_Main --> onClickWIFI ").append(e.message)
                        .toString()
                )
            }
        }

    }

    companion object {
        fun launch(context: Context, ispt: String) {
            val intent = Intent(context, PatchworkXMActivity::class.java)
            intent.putExtra("ispt", ispt)
            context.startActivity(intent)
        }


    }
}