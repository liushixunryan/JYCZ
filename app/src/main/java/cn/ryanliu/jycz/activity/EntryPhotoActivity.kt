package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.EntryHandoverBean
import cn.ryanliu.jycz.databinding.ActivityEntryPhotoBinding
import cn.ryanliu.jycz.util.GlideEngine
import cn.ryanliu.jycz.util.UploadUtil
import cn.ryanliu.jycz.viewmodel.EntryPhotoVM
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import java.io.File


/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:入场交接 - 拍照上传交接单
 */
class EntryPhotoActivity : BaseActivity<ActivityEntryPhotoBinding, EntryPhotoVM>() {
    private var crc: String = ""
    private var entyyHandover: EntryHandoverBean? = null
    override fun layoutId(): Int = R.layout.activity_entry_photo

    override fun initView() {

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "入场交接 - 拍照上传交接单"

        crc = intent.getStringExtra("edit").toString()
        //没数据 TODO：先暂时注释
//        entyyHandover = intent.getSerializableExtra("entyyHandover") as EntryHandoverBean

        if (entyyHandover != null) {
            mDatabind.cphTv.text = entyyHandover!!.car_number
            mDatabind.xcwcsjTv.text = entyyHandover!!.order_state_scan_time
            mDatabind.smrTv.text = entyyHandover!!.scan_user_name
            mDatabind.xcsmxsTv.text = entyyHandover!!.scan_num.toString()
        }

        onClick()

    }

    private fun onClick() {
        mDatabind.btnSureselect.setOnClickListener {
            mViewModel.confirmTask(
                entyyHandover!!.hand_task_id,
                "入场交接",
                mDatabind.text1.text.toString(),
                mDatabind.text2.text.toString(),
                mDatabind.text3.text.toString(),
                mDatabind.text4.text.toString(),
                mDatabind.text5.text.toString(),
                mDatabind.text6.text.toString()
            )
        }

        mDatabind.uploadimg1.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
                .setCameraImageFormat(PictureMimeType.ofPNG())
                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
                        mViewModel.upload(File(result[0]!!.realPath), 1)
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
                        mViewModel.upload(File(result[0]!!.realPath), 2)
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
                        mViewModel.upload(File(result[0]!!.realPath), 3)
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
                        mViewModel.upload(File(result[0]!!.realPath), 4)
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
                        mViewModel.upload(File(result[0]!!.realPath), 5)
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
                        mViewModel.upload(File(result[0]!!.realPath), 6)
                    }

                    override fun onCancel() {}
                })
        }


        mDatabind.img1.setOnClickListener {
            val intent = Intent(this@EntryPhotoActivity, PhotoSeeActivity::class.java)
            intent.putExtra("url", mDatabind.text1.text.toString())
            intent.putExtra("index", 1)
            startActivityForResult(intent, PhotoSeeActivity.REQUEST_CODE_FHZP)
        }
        mDatabind.img2.setOnClickListener {
            val intent = Intent(this@EntryPhotoActivity, PhotoSeeActivity::class.java)
            intent.putExtra("url", mDatabind.text2.text.toString())
            intent.putExtra("index", 2)
            startActivityForResult(intent, PhotoSeeActivity.REQUEST_CODE_FHZP)

        }
        mDatabind.img3.setOnClickListener {
            val intent = Intent(this@EntryPhotoActivity, PhotoSeeActivity::class.java)
            intent.putExtra("url", mDatabind.text3.text.toString())
            intent.putExtra("index", 3)
            startActivityForResult(intent, PhotoSeeActivity.REQUEST_CODE_FHZP)
        }
        mDatabind.img4.setOnClickListener {
            val intent = Intent(this@EntryPhotoActivity, PhotoSeeActivity::class.java)
            intent.putExtra("url", mDatabind.text4.text.toString())
            intent.putExtra("index", 4)
            startActivityForResult(intent, PhotoSeeActivity.REQUEST_CODE_FHZP)
        }
        mDatabind.img5.setOnClickListener {
            val intent = Intent(this@EntryPhotoActivity, PhotoSeeActivity::class.java)
            intent.putExtra("url", mDatabind.text5.text.toString())
            intent.putExtra("index", 5)
            startActivityForResult(intent, PhotoSeeActivity.REQUEST_CODE_FHZP)
        }
        mDatabind.img6.setOnClickListener {
            val intent = Intent(this@EntryPhotoActivity, PhotoSeeActivity::class.java)
            intent.putExtra("url", mDatabind.text6.text.toString())
            intent.putExtra("index", 6)
            startActivityForResult(intent, PhotoSeeActivity.REQUEST_CODE_FHZP)
        }


    }

    override fun createObserver() {
        mViewModel.mBackList.observe(this){
            onBackPressed()
        }

        mViewModel.mUrl.observe(this) { url ->
            mViewModel.mIndex.observe(this) { index ->
                when (index) {
                    1 -> {
                        mDatabind.img1.visibility = View.VISIBLE
                        mDatabind.text1.text = url
                        mDatabind.uploadimg1.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mDatabind.img1)
                    }
                    2 -> {
                        mDatabind.img2.visibility = View.VISIBLE
                        mDatabind.text2.text = url
                        mDatabind.uploadimg2.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mDatabind.img2)
                    }
                    3 -> {
                        mDatabind.img3.visibility = View.VISIBLE
                        mDatabind.text3.text = url
                        mDatabind.uploadimg3.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mDatabind.img3)
                    }
                    4 -> {
                        mDatabind.img4.visibility = View.VISIBLE
                        mDatabind.text4.text = url
                        mDatabind.uploadimg4.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mDatabind.img4)
                    }
                    5 -> {
                        mDatabind.img5.visibility = View.VISIBLE
                        mDatabind.text5.text = url
                        mDatabind.uploadimg5.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mDatabind.img5)
                    }
                    else -> {
                        mDatabind.img6.visibility = View.VISIBLE
                        mDatabind.text6.text = url
                        mDatabind.uploadimg6.visibility = View.GONE

                        Glide.with(this@EntryPhotoActivity)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.color.app_color_f6)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mDatabind.img6)
                    }
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (PhotoSeeActivity.REQUEST_CODE_FHZP == requestCode) {
                when (data?.getIntExtra("indeximg", 0)) {
                    1 -> {
                        mDatabind.img1.visibility = View.GONE
                        mDatabind.text1.text = ""
                        mDatabind.uploadimg1.visibility = View.VISIBLE
                    }
                    2 -> {
                        mDatabind.img2.visibility = View.GONE
                        mDatabind.text2.text = ""
                        mDatabind.uploadimg2.visibility = View.VISIBLE
                    }
                    3 -> {
                        mDatabind.img3.visibility = View.GONE
                        mDatabind.text3.text = ""
                        mDatabind.uploadimg3.visibility = View.VISIBLE
                    }
                    4 -> {
                        mDatabind.img4.visibility = View.GONE
                        mDatabind.text4.text = ""
                        mDatabind.uploadimg4.visibility = View.VISIBLE
                    }
                    5 -> {
                        mDatabind.img5.visibility = View.GONE
                        mDatabind.text5.text = ""
                        mDatabind.uploadimg5.visibility = View.VISIBLE
                    }
                    else -> {
                        mDatabind.img6.visibility = View.GONE
                        mDatabind.text6.text = ""
                        mDatabind.uploadimg6.visibility = View.VISIBLE
                    }
                }
            }

        }
    }

    companion object {
        fun launch(context: Context, crc: String, entyyHandover: EntryHandoverBean?) {
            val intent = Intent(context, EntryPhotoActivity::class.java)
            intent.putExtra("edit", crc)
            intent.putExtra("entyyHandover", entyyHandover)
            context.startActivity(intent)
        }

    }
}