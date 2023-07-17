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
import cn.ryanliu.jycz.bean.SelectAreaBean
import cn.ryanliu.jycz.bean.SelectListBean
import cn.ryanliu.jycz.bean.prequest.Area
import cn.ryanliu.jycz.databinding.ActivityAreaSelectBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.util.UserUtil
import cn.ryanliu.jycz.view.GridSpaceItemDecoration
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

    lateinit var selectBean: MutableList<SelectAreaBean>
    lateinit var bean: MutableList<Area>

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
        mDatabind.rvArea.addItemDecoration(GridSpaceItemDecoration(3, 10, 16))
        mDatabind.rvArea.adapter = mAdapter

        mAdapter1 = SelectListAdapter()
        mDatabind.selectareaRv.adapter = mAdapter1

        onClick();
    }

    private fun onClick() {
        mDatabind.btnSelectarea.setOnClickListener {
            mViewModel.getWareArea(mDatabind.etKq.text.toString(), "卸车")
        }
        //添加
        mDatabind.addAreaTv.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                bean.clear()
                mAdapter1.setNewInstance(null)
                val isSelect = arrayListOf<Int>()
                for (i in selectBean.indices) {
                    if (selectBean[i].isselect == 1) {
                        bean.add(
                            Area(
                                selectBean[i].ware_area_name.toString(),
                                selectBean[i].ware_area_id
                            )
                        )
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
                if (mAdapter1.data.isEmpty()) {
                    ToastUtilsExt.info("您未选中区域")
                    return
                }

                showTipDialog("确认是否开始盘点？",
                    "提示",
                    {
                        mViewModel.lockAllCancel(bean, "指定区域")
                    },
                    {})
            }
        })

        mDatabind.btnQkpd.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
//                点击 确认 后将冻结当前库存、同时锁定操作：扫码卸车、入场交接、分拣码放、扫码装车、出场交接、库位调整 等操作。直至确认盘点完成。
                showTipDialog("确认是否开始盘点？",
                    "提示",
                    {
                        mViewModel.lockAllCancel(bean, "全库")
                    },
                    {})
            }
        })
    }

    override fun createObserver() {
        mViewModel.mSelectArea.observe(this) {
            if (it.isNullOrEmpty()) {
                mDatabind.loadingLayout.showEmpty()
            } else {
                mAdapter.setList(it)
                selectBean = it
                if (mAdapter.data.isEmpty()) {
                    mDatabind.loadingLayout.showEmpty()
                } else {
                    mDatabind.loadingLayout.showContent()
                }
            }
        }

        mViewModel.mSurepd.observe(this) {
            if (it.toString() == "创建盘点任务单失败") {
            } else {
                InventoryCountActivity.launch(this@AreaSelectActivity, it)
            }
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

    override fun onResume() {
        super.onResume()
        mViewModel.getWareArea(mDatabind.etKq.text.toString(), "卸车")
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, AreaSelectActivity::class.java)
            context.startActivity(intent)
        }

    }
}