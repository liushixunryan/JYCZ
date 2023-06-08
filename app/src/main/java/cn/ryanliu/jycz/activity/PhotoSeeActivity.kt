package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityPhotoSeeBinding
import cn.ryanliu.jycz.viewmodel.PhotoSeeVM

class PhotoSeeActivity : BaseActivity<ActivityPhotoSeeBinding, PhotoSeeVM>() {
    override fun layoutId(): Int = R.layout.activity_photo_see

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "照片查看"

        onClick()

    }

    private fun onClick() {
        mDatabind.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, PhotoSeeActivity::class.java)
            context.startActivity(intent)
        }

    }
}