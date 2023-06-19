package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityInventoryCountBinding
import cn.ryanliu.jycz.viewmodel.InventoryCountVM
import com.xql.loading.TipDialog

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:库存盘点
 */
class InventoryCountActivity : BaseActivity<ActivityInventoryCountBinding, InventoryCountVM>() {
    // 提示窗对象
    private var tipDialog: TipDialog? = null
    override fun layoutId(): Int = R.layout.activity_inventory_count

    override fun initView() {
        initDialog();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库存盘点"

        onClick()


    }

    private fun onClick() {
        mDatabind.ysxmTv.setOnClickListener {
//            XMListActivity.launch(this)
        }

        mDatabind.xsTv.setOnClickListener {
//            XMListActivity.launch(this)
        }

        mDatabind.khddhTv.setOnClickListener {
//            XMListActivity.launch(this)

        }

        mDatabind.qrpdwnBtn.setOnClickListener {
            InventoryResultActivity.launch(this)
        }
        mDatabind.qxbcpdBtn.setOnClickListener {
            showTipDialog("确认取消盘点？", {
                Log.e("sansuiban", "onClick:确认 ")

            }, {})
        }
    }

    /**
     * 初始化各种Dialog
     */
    private fun initDialog() {
        if (tipDialog == null) {
            tipDialog = TipDialog(this)
        }
    }

    /**
     * 显示提示类型Dialog
     */
    fun showTipDialog(
        msg: String,
        subbtn: TipDialog.SubmitListener?,
        canbtn: TipDialog.CancelListener
    ): TipDialog? {
        if (tipDialog != null && !tipDialog!!.isShowing) {
            tipDialog!!.setMessage(msg).isShowCancel(true).setSubmitListener(subbtn)
                .setCancelListener(canbtn).show()
        }
        return tipDialog
    }

    /**
     * 隐藏提示类型Dialog
     */
    fun hideTipDialog() {
        if (tipDialog != null && tipDialog!!.isShowing) {
            tipDialog!!.dismiss()
        }
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, InventoryCountActivity::class.java)
            context.startActivity(intent)
        }

    }
}