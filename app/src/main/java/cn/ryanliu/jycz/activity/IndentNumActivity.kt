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
    lateinit var mAdapter: IndentNumAdapter
    lateinit var selectBean: MutableList<IndentNumBean>
    override fun layoutId(): Int = R.layout.activity_indent_num

    override fun initView() {
        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "订单数量"
        mAdapter = IndentNumAdapter();
        mDatabind.ddsllbRv.adapter = mAdapter

        var a = IndentNumBean(1, "TRD1202305W0100137")
        var b = IndentNumBean(2, "TRD1202305W0100137")
        var d = IndentNumBean(3, "TRD1202305W0100137")
        var c = IndentNumBean(4, "TRD1202305W0100137")
        selectBean.add(a)
        selectBean.add(b)
        selectBean.add(c)
        selectBean.add(d)

        mAdapter.setList(selectBean)

        onClick()

    }

    private fun onClick() {
        mAdapter.setOnAllDeviceProcureClickListener {
            XMListActivity.launch(this@IndentNumActivity)
        }

        mAdapter.setOnReadDeviceProcureClickListener {
            XMListActivity.launch(this@IndentNumActivity)

        }
        mAdapter.setOnUnreadDeviceProcureClickListener {
            XMListActivity.launch(this@IndentNumActivity)

        }
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, IndentNumActivity::class.java)
            context.startActivity(intent)
        }

    }
}