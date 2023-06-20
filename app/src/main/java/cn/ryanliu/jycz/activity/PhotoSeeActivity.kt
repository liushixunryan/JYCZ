package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityPhotoSeeBinding
import cn.ryanliu.jycz.viewmodel.PhotoSeeVM
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PhotoSeeActivity : BaseActivity<ActivityPhotoSeeBinding, PhotoSeeVM>() {
    private var url: String = ""
    private var index: Int = 0
    override fun layoutId(): Int = R.layout.activity_photo_see

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "照片查看"
        url = intent.getStringExtra("url").toString()
        index = intent.getIntExtra("index", 0)

        Glide.with(this@PhotoSeeActivity)
            .load(url)
            .centerCrop()
            .placeholder(R.color.app_color_f6)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mDatabind.photoImg)
        onClick()

    }

    private fun onClick() {
        mDatabind.btnDel.setOnClickListener {
            Log.e("sansuiban", "onClick: 点击删除index")
            val intent = Intent(this@PhotoSeeActivity, PhotoSeeActivity::class.java)
            intent.putExtra("indeximg", index)
            setResult(RESULT_OK, intent);
            finish()
        }

        mDatabind.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun createObserver() {
    }

    companion object {
        const val REQUEST_CODE_FHZP = 62

        fun launch(context: Context, url: String, index: Int) {
            val intent = Intent(context, PhotoSeeActivity::class.java)
            intent.putExtra("url", url)
            intent.putExtra("index", index)
            context.startActivity(intent)
        }

        fun launch(context: Context) {
            val intent = Intent(context, PhotoSeeActivity::class.java)
            context.startActivity(intent)
        }

    }
}