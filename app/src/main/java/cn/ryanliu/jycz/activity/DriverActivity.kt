package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityDriverBinding
import cn.ryanliu.jycz.databinding.ActivityProjectBinding
import cn.ryanliu.jycz.viewmodel.DriverVM
import cn.ryanliu.jycz.viewmodel.ProjectVM

class DriverActivity : BaseActivity<ActivityDriverBinding, DriverVM>() {


    override fun layoutId(): Int = R.layout.activity_driver

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
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, DriverActivity::class.java)
            context.startActivity(intent)
        }

    }
}