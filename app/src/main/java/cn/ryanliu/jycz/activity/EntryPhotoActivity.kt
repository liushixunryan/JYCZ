package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityEntryPhotoBinding
import cn.ryanliu.jycz.viewmodel.EntryPhotoVM

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:入场交接 - 拍照上传交接单
 */
class EntryPhotoActivity : BaseActivity<ActivityEntryPhotoBinding, EntryPhotoVM>() {

    override fun layoutId(): Int = R.layout.activity_entry_photo

    override fun initView() {
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, EntryPhotoActivity::class.java)
            context.startActivity(intent)
        }

    }
}