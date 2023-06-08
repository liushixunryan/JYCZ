package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityEntryPhotoBinding
import cn.ryanliu.jycz.util.GlideEngine
import cn.ryanliu.jycz.viewmodel.EntryPhotoVM
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener


/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:入场交接 - 拍照上传交接单
 */
class EntryPhotoActivity : BaseActivity<ActivityEntryPhotoBinding, EntryPhotoVM>() {

    override fun layoutId(): Int = R.layout.activity_entry_photo

    override fun initView() {

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "入场交接 - 拍照上传交接单"

        onClick()

    }

    private fun onClick() {
        mDatabind.uploadimg1.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
                .setCameraImageFormat(PictureMimeType.ofPNG())
                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
                        mDatabind.img1.visibility = View.VISIBLE
                        mDatabind.uploadimg1.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(result[0]?.availablePath)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mDatabind.img1)
                    }

                    override fun onCancel() {}
                })
        }
        mDatabind.uploadimg2.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
                .setCameraImageFormat(PictureMimeType.ofPNG())
                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
                        mDatabind.img2.visibility = View.VISIBLE
                        mDatabind.uploadimg2.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(result[0]?.availablePath)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mDatabind.img2)
                    }

                    override fun onCancel() {}
                })
        }
        mDatabind.uploadimg3.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
                .setCameraImageFormat(PictureMimeType.ofPNG())
                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
                        mDatabind.img3.visibility = View.VISIBLE
                        mDatabind.uploadimg3.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(result[0]?.availablePath)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mDatabind.img3)
                    }

                    override fun onCancel() {}
                })
        }
        mDatabind.uploadimg4.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
                .setCameraImageFormat(PictureMimeType.ofPNG())
                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
                        mDatabind.img4.visibility = View.VISIBLE
                        mDatabind.uploadimg4.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(result[0]?.availablePath)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mDatabind.img4)
                    }

                    override fun onCancel() {}
                })
        }
        mDatabind.uploadimg5.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
                .setCameraImageFormat(PictureMimeType.ofPNG())
                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
                        mDatabind.img5.visibility = View.VISIBLE
                        mDatabind.uploadimg5.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(result[0]?.availablePath)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mDatabind.img5)
                    }

                    override fun onCancel() {}
                })
        }
        mDatabind.uploadimg6.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
                .setCameraImageFormat(PictureMimeType.ofPNG())
                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
                        mDatabind.img6.visibility = View.VISIBLE
                        mDatabind.uploadimg6.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(result[0]?.availablePath)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mDatabind.img6)
                    }

                    override fun onCancel() {}
                })
        }


        mDatabind.img1.setOnClickListener {
            PhotoSeeActivity.launch(this)
        }
        mDatabind.img2.setOnClickListener {
            PhotoSeeActivity.launch(this)
        }
        mDatabind.img3.setOnClickListener {
            PhotoSeeActivity.launch(this)
        }
        mDatabind.img4.setOnClickListener {
            PhotoSeeActivity.launch(this)
        }
        mDatabind.img5.setOnClickListener {
            PhotoSeeActivity.launch(this)
        }
        mDatabind.img6.setOnClickListener {
            PhotoSeeActivity.launch(this)
        }


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