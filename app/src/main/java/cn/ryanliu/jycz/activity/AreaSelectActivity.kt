package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.AreaSelectAdapter
import cn.ryanliu.jycz.adapter.SelectAreaAdapter
import cn.ryanliu.jycz.adapter.SelectListAdapter
import cn.ryanliu.jycz.adapter.XMListAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.AreaSelectBean
import cn.ryanliu.jycz.bean.SelectListBean
import cn.ryanliu.jycz.databinding.ActivityAreaSelectBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.AreaSelectVM
import com.xql.loading.TipDialog

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:库区选择 - 选择盘点区域
 */
class AreaSelectActivity : BaseActivity<ActivityAreaSelectBinding, AreaSelectVM>() {
    // 提示窗对象
    private var tipDialog: TipDialog? = null

    lateinit var mAdapter: AreaSelectAdapter

    lateinit var mAdapter1: SelectListAdapter

    lateinit var selectBean: MutableList<AreaSelectBean>
    lateinit var bean: MutableList<SelectListBean>

    override fun layoutId(): Int = R.layout.activity_area_select

    override fun initView() {
        initDialog();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库区选择 - 选择盘点区域"

        selectBean = ArrayList();
        bean = ArrayList();

        mAdapter = AreaSelectAdapter()
        mDatabind.rvArea.adapter = mAdapter

        mAdapter1 = SelectListAdapter()
        mDatabind.selectareaRv.adapter = mAdapter1

        onClick();
    }

    private fun onClick() {
        mDatabind.btnSelectarea.setOnClickListener {
            var a = AreaSelectBean("A05", 0)
            var b = AreaSelectBean("A06", 0)
            var d = AreaSelectBean("A18", 0)
            var c = AreaSelectBean("D15", 0)
            var e = AreaSelectBean("D11", 0)
            var f = AreaSelectBean("D12", 0)
            var g = AreaSelectBean("D13", 0)
            selectBean.add(a)
            selectBean.add(b)
            selectBean.add(c)
            selectBean.add(d)
            selectBean.add(g)
            selectBean.add(e)
            selectBean.add(f)

            mAdapter.setList(selectBean)
        }
        //添加
        mDatabind.addAreaTv.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                bean.clear()
                mAdapter1.setNewInstance(null)
                val isSelect = arrayListOf<Int>()
                for (i in selectBean.indices) {
                    if (selectBean[i].isselect == 1) {
                        bean.add(SelectListBean(i, selectBean[i].areaname.toString()))
                    } else {
                        isSelect.add(i)
                    }
                }

                mAdapter1.setNewInstance(bean)
                if (isSelect.size == selectBean.size) {
                    ToastUtilsExt.info("您未选中任何库区")
                    return
                }
            }
        })

        //选择的盘点
        mDatabind.btnSelectpd.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                showTipDialog("确认是否开始盘点？点击 确认 后将冻结当前库存、同时锁定操作：扫码卸车、入场交接、分拣码放、扫码装车、出场交接、库位调整 等操作。直至确认盘点完成。",
                    "提示",
                    {
                        InventoryCountActivity.launch(this@AreaSelectActivity)

                    },
                    {})
            }
        })

        mDatabind.btnQkpd.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                showTipDialog("确认是否开始盘点？点击 确认 后将冻结当前库存、同时锁定操作：扫码卸车、入场交接、分拣码放、扫码装车、出场交接、库位调整 等操作。直至确认盘点完成。",
                    "提示",
                    {
                        InventoryCountActivity.launch(this@AreaSelectActivity)

                    },
                    {})
            }
        })
    }

    override fun createObserver() {
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
        title: String,
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

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, AreaSelectActivity::class.java)
            context.startActivity(intent)
        }

    }
}