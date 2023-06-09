package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.XMListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.XMListBean
import cn.ryanliu.jycz.databinding.ActivityInventoryCountBinding
import cn.ryanliu.jycz.databinding.ActivityInventoryResultBinding
import cn.ryanliu.jycz.viewmodel.InventoryResultVM

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:盘点结果
 */
class InventoryResultActivity : BaseActivity<ActivityInventoryResultBinding, InventoryResultVM>() {
    lateinit var mAdapter: XMListAdapter
    override fun layoutId(): Int = R.layout.activity_inventory_result

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "箱码列表"

        mAdapter = XMListAdapter();
        mDatabind.cylistRv.adapter = mAdapter

        mAdapter.addData(XMListBean(0, "aslidhakjsbdalskndlaksf"))
        mAdapter.addData(XMListBean(1, "aslidhakjsbdalskndlaksf"))
        mAdapter.addData(XMListBean(2, "aslidhakjsbdalskndlaksf"))
        mAdapter.addData(XMListBean(3, "aslidhakjsbdalskndlaksf"))
        mAdapter.addData(XMListBean(4, "aslidhakjsbdalskndlaksf"))
        mAdapter.addData(XMListBean(5, "aslidhakjsbdalskndlaksf"))

        onClick();

    }

    private fun onClick() {

    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, InventoryResultActivity::class.java)
            context.startActivity(intent)
        }

    }
}