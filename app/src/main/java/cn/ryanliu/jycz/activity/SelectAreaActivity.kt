package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.SelectAreaAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.SelectAreaBean
import cn.ryanliu.jycz.databinding.ActivitySelectAreaBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.SelectAreaVM

class SelectAreaActivity : BaseActivity<ActivitySelectAreaBinding, SelectAreaVM>() {
    private var pageModel: Int = 0

    lateinit var mAdapter: SelectAreaAdapter
    lateinit var selectBean: MutableList<SelectAreaBean>
    override fun layoutId(): Int = R.layout.activity_select_area
    override fun initView() {
        selectBean = ArrayList();

        pageModel = intent.getIntExtra("edit", 0)

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "选择库区信息"

        mAdapter = SelectAreaAdapter()
        mDatabind.rvArea.adapter = mAdapter

        onClick();
    }

    private fun onClick() {
        mDatabind.btnSelectarea.setOnClickListener {
            mViewModel.getWareArea(
                mDatabind.etKq.text.toString(), if (pageModel == 1) {
                    "装车"
                } else {
                    "卸车"
                }
            )
        }

        mDatabind.btnConfirm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (pageModel == 1) {//首页进来的
                    AreaAdjustActivity.launch(this@SelectAreaActivity)
                    val isSelect = arrayListOf<Int>()
                    for (i in selectBean.indices) {
                        if (selectBean[i].isselect == 1) {
                            AreaAdjustActivity.launch(this@SelectAreaActivity)
                        } else {
                            isSelect.add(i)
                        }
                    }

                    if (isSelect.size == selectBean.size) {
                        ToastUtilsExt.info("您未选中任何库区")
                        return
                    }

                } else {
                    val isSelect = arrayListOf<Int>()
                    for (i in selectBean.indices) {
                        if (selectBean[i].isselect == 1) {
                            val intent =
                                Intent(this@SelectAreaActivity, SelectCarActivity::class.java)
                            intent.putExtra("areaName", selectBean[i].ware_area_name)
                            intent.putExtra("areaId", selectBean[i].ware_area_id)
                            setResult(RESULT_OK, intent);
                            finish()
                        } else {
                            isSelect.add(i)
                        }
                    }

                    if (isSelect.size == selectBean.size) {
                        ToastUtilsExt.info("您未选中任何库区")
                        return
                    }
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getWareArea(
            mDatabind.etKq.text.toString(), if (pageModel == 1) {
                "装车"
            } else {
                "卸车"
            }
        )
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

    }

    companion object {
        const val REQUEST_CODE_XXKQ = 8
        fun launch(context: Context, pageModel: Int) {
            val intent = Intent(context, SelectAreaActivity::class.java)
            intent.putExtra("edit", pageModel)
            context.startActivity(intent)
        }

    }
}