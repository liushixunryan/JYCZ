package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.SelectCarAdapter
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.SelectCarBean
import cn.ryanliu.jycz.databinding.ActivitySelectCarBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.SelectCarVM

/**
 * @Author: lsx
 * @Date: 2023/6/6
 * @Description:选择车辆
 */
class SelectCarActivity : BaseActivity<ActivitySelectCarBinding, SelectCarVM>() {
    lateinit var mAdapter: SelectCarAdapter
    lateinit var selectBean: MutableList<SelectCarBean>
    override fun layoutId(): Int = R.layout.activity_select_car
    override fun initView() {

        selectBean = ArrayList();


        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "选择车辆信息"

        mAdapter = SelectCarAdapter()
        mDatabind.rvCarnum.adapter = mAdapter

        onClick();
    }

    private fun onClick() {
        mDatabind.btnSelectarea.setOnClickListener {
            var a = SelectCarBean("京A96M56", 0)
            var b = SelectCarBean("京AQW322W", 0)
            var d = SelectCarBean("京A226MQW", 0)
            var c = SelectCarBean("京A43QWW", 0)
            selectBean.add(a)
            selectBean.add(b)
            selectBean.add(c)
            selectBean.add(d)

            mAdapter.setList(selectBean)
        }

        mDatabind.btnConfirm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                val isSelect = arrayListOf<Int>()
                for (i in selectBean.indices) {
                    if (selectBean[i].isselect == 1) {
                        val intent = Intent(this@SelectCarActivity, SelectCarActivity::class.java)
                        intent.putExtra("carNum", selectBean[i].plant)
                        setResult(RESULT_OK, intent);
                        finish()
                    } else {
                        isSelect.add(i)
                    }
                }

                if (isSelect.size == selectBean.size) {
                    ToastUtilsExt.info("您未选中任何车牌号")
                    return
                }


            }

        })
    }

    override fun createObserver() {
    }

    companion object {
        const val REQUEST_CODE_XXCL = 9
        fun launch(context: Context) {
            val intent = Intent(context, SelectCarActivity::class.java)
            context.startActivity(intent)
        }

    }
}