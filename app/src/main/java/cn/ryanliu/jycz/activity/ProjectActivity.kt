package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityProjectBinding
import cn.ryanliu.jycz.viewmodel.ProjectVM

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:项目预约
 */
class ProjectActivity : BaseActivity<ActivityProjectBinding, ProjectVM>() {
    var carNum = ""
    private var pageModel: Int = 0
    override fun layoutId(): Int = R.layout.activity_project

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "扫码卸货"

        mDatabind.inNavBar.tvNavCenter.visibility = View.VISIBLE
        mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：15"
        mDatabind.inNavBar.tvNavRight.visibility = View.VISIBLE
        mDatabind.inNavBar.tvNavRight.text = "库区：A06"

        pageModel = intent.getIntExtra("edit", 0)
        carNum = intent.getStringExtra("carnumber").toString()

        onClick()
    }

    private fun onClick() {
        mDatabind.indentNumTv.setOnClickListener {
            IndentNumActivity.launch(this@ProjectActivity)
        }
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context, carnumber: String, pageModel: Int) {
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra("carnumber", carnumber)
            intent.putExtra("edit", pageModel)
            context.startActivity(intent)
        }

    }
}