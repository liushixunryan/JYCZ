package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.XMListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.InventResult
import cn.ryanliu.jycz.bean.XMListBean
import cn.ryanliu.jycz.bean.lockAllCancel
import cn.ryanliu.jycz.databinding.ActivityInventoryCountBinding
import cn.ryanliu.jycz.databinding.ActivityInventoryResultBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.InventoryResultVM

/**
 * @Author: lsx
 * @Date: 2023/6/9
 * @Description:盘点结果
 */
class InventoryResultActivity : BaseActivity<ActivityInventoryResultBinding, InventoryResultVM>() {
    lateinit var mAdapter: XMListAdapter
    private var invent_id: String = ""
    private lateinit var lockAllCancel: lockAllCancel
    lateinit var inventResult: InventResult

    override fun layoutId(): Int = R.layout.activity_inventory_result

    override fun initView() {
        inventResult = intent.getSerializableExtra("inventResult") as InventResult
        invent_id = intent.getStringExtra("invent_id").toString()
        mDatabind.pdqxTv.text = inventResult.ware_area
        mDatabind.xtkcTv.text = inventResult.sys_ware_num.toString()
        mDatabind.pdkcTv.text = inventResult.invent_num.toString()

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "盘点结果"

        mAdapter = XMListAdapter();
        mDatabind.cylistRv.adapter = mAdapter

        if (inventResult.scan_code_list.isNotEmpty()){
            mAdapter.setNewInstance(inventResult.scan_code_list)
        }


        onClick();

    }

    private fun onClick() {
        mDatabind.jxpdBtn.setOnClickListener {
            InventoryCountActivity.launch(this, lockAllCancel(invent_id.toInt()))
        }

        mDatabind.bcpdBtn.setOnClickListener {
            mViewModel.saveInvent(invent_id)
        }
    }

    override fun createObserver() {
        mViewModel.mBackList.observe(this) {
            ToastUtilsExt.info("保存成功")
            MainActivity.launchClear(this)
        }
    }

    companion object {
        fun launch(context: Context, inventResult: InventResult, invent_id: String?) {
            val intent = Intent(context, InventoryResultActivity::class.java)
            intent.putExtra("inventResult", inventResult)
            intent.putExtra("invent_id", invent_id)
            context.startActivity(intent)
        }

    }
}