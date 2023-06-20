package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.SelectCarAdapter
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.SelectCarBean
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivitySelectCarBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.SelectCarVM

/**
 * @Author: lsx
 * @Date: 2023/6/6
 * @Description:选择车辆
 */
class SelectCarActivity : BaseActivity<ActivitySelectCarBinding, SelectCarVM>() {
    private var pageModel: Int = 0
    lateinit var mAdapter: SelectCarAdapter
    var selectBean: MutableList<SelectCarBean>? = null
    override fun layoutId(): Int = R.layout.activity_select_car
    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "选择车辆信息"
        pageModel = intent.getIntExtra("edit", 0)

        mAdapter = SelectCarAdapter()
        mDatabind.rvCarnum.adapter = mAdapter

        onClick();
    }

    private fun onClick() {
        mDatabind.btnSelectarea.setOnClickListener {
            mViewModel.getCarInfoIn1(
                mDatabind.etKq.text.toString(), if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }
            )
        }

        mDatabind.btnConfirm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                val isSelect = arrayListOf<Int>()
                for (i in selectBean!!.indices) {
                    if (selectBean!![i].isselect == 1) {
                        val intent = Intent(this@SelectCarActivity, SelectCarActivity::class.java)
                        intent.putExtra("carNum", selectBean!![i].car_number)
                        setResult(RESULT_OK, intent);
                        finish()
                    } else {
                        isSelect.add(i)
                    }
                }

                if (isSelect.size == selectBean!!.size) {
                    ToastUtilsExt.info("您未选中任何车牌号")
                    return
                }


            }

        })
    }

    override fun createObserver() {
        mViewModel.mSelectCar.observe(this) {
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
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getCarInfoIn1(
            mDatabind.etKq.text.toString(), if (pageModel == Constant.PageModel.XIECHE) {
                "卸车"
            } else {
                "装车"
            }
        )
    }

    companion object {
        const val REQUEST_CODE_XXCL = 9
        fun launch(context: Context) {
            val intent = Intent(context, SelectCarActivity::class.java)
            context.startActivity(intent)
        }

    }
}