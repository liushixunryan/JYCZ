package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityAreaAdjustBinding
import cn.ryanliu.jycz.viewmodel.AreaAdjustVM

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:库区调整
 */
class AreaAdjustActivity : BaseActivity<ActivityAreaAdjustBinding, AreaAdjustVM>() {

    override fun layoutId(): Int = R.layout.activity_area_adjust

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库区调整"

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
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, AreaAdjustActivity::class.java)
            context.startActivity(intent)
        }

    }
}