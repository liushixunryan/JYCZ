package cn.ryanliu.jycz.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.booth.BTActivity
import cn.ryanliu.jycz.activity.detail.*
import cn.ryanliu.jycz.adapter.HomeCARAdapter
import cn.ryanliu.jycz.adapter.HomePDAAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.basic.BaseApplication
import cn.ryanliu.jycz.bean.HomeCARBean
import cn.ryanliu.jycz.bean.HomePDABean
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.common.constant.Constant.MmKv_KEY
import cn.ryanliu.jycz.databinding.ActivityMainBinding
import cn.ryanliu.jycz.dialog.CommitImgDialog
import cn.ryanliu.jycz.util.*
import cn.ryanliu.jycz.view.GridSpaceItemDecoration
import cn.ryanliu.jycz.viewmodel.MainVM
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.tbruyelle.rxpermissions.RxPermissions
import print.Print
import java.io.File

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    var YCImg1: String = ""
    var YCImg2: String = ""
    var YCImg3: String = ""
    var YCImg4: String = ""


    lateinit var mPdaAdapter: HomePDAAdapter
    lateinit var mCarAdapter: HomeCARAdapter

    var isconnect = false


    private fun connectionBluetooth() {
        //获取蓝牙动态权限
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).subscribe {
            if (true) {
                val intent = Intent(this, BTActivity::class.java)
                intent.putExtra("TAG", 0)
                startActivityForResult(intent, 0)
            }
        }
    }

    override fun layoutId(): Int = R.layout.activity_main

    @SuppressLint("SetTextI18n")
    override fun initView() {
//        mDatabind.ivHead.setOnClickListener(object : OnSingleClickListener() {
//            override fun onSingleClick(view: View?) {
//                CommitImgDialog.Builder(this@MainActivity)
//                    .setOnConfirmListener(object : CommitImgDialog.OnConfirmListener {
//                        override fun onClick(dialog: Dialog,input:EditText) {
//                            ToastUtilsExt.info("点击取消")
//                            dialog.dismiss()
//                        }
//                    })
//                    .setOnCancelListener(object : CommitImgDialog.OnCancelListener {
//                        override fun onClick(dialog: Dialog,input:EditText) {
//                            ToastUtilsExt.info("点击保存")
//                            mViewModel.saveAbor(
//                                "123", input.text.toString(), YCImg1,
//                                YCImg2,
//                                YCImg3,
//                                YCImg4
//                            )
//                            dialog.dismiss()
//                        }
//
//                    })
//                    .setOnOneListener(object : CommitImgDialog.OnoneImageListener {
//                        override fun onClick(dialog: Dialog, img: ImageView, gonell: LinearLayout) {
//                            PictureSelector.create(this@MainActivity)
//                                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
//                                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
//                                .setImageEngine(GlideEngine.createGlideEngine())
//                                .forResult(object : OnResultCallbackListener<LocalMedia?> {
//                                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
//                                        mViewModel.upload(File(result[0]!!.realPath), 1)
//                                        Glide.with(this@MainActivity)
//                                            .load(result[0]!!.realPath)
//                                            .centerCrop()
//                                            .placeholder(R.color.app_color_f6)
//                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                            .into(img)
//                                        gonell.visibility = View.GONE
//                                        img.visibility = View.VISIBLE
//                                        showLoading()
//                                    }
//
//                                    override fun onCancel() {
//                                        hideLoading()
//                                    }
//                                })
//                        }
//
//                    })
//                    .setOnTwolListener(object : CommitImgDialog.OnTwoImageListener {
//                        override fun onClick(dialog: Dialog, img: ImageView, gonell: LinearLayout) {
//                            PictureSelector.create(this@MainActivity)
//                                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
//                                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
//                                .setImageEngine(GlideEngine.createGlideEngine())
//                                .forResult(object : OnResultCallbackListener<LocalMedia?> {
//                                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
//                                        mViewModel.upload(File(result[0]!!.realPath), 2)
//                                        Glide.with(this@MainActivity)
//                                            .load(result[0]!!.realPath)
//                                            .centerCrop()
//                                            .placeholder(R.color.app_color_f6)
//                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                            .into(img)
//                                        gonell.visibility = View.GONE
//                                        img.visibility = View.VISIBLE
//                                        showLoading()
//
//                                    }
//
//                                    override fun onCancel() {
//                                        hideLoading()
//                                    }
//                                })
//                        }
//
//                    })
//                    .setOnThreelListener(object : CommitImgDialog.OnThreeImageListener {
//                        override fun onClick(dialog: Dialog, img: ImageView, gonell: LinearLayout) {
//                            PictureSelector.create(this@MainActivity)
//                                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
//                                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
//                                .setImageEngine(GlideEngine.createGlideEngine())
//                                .forResult(object : OnResultCallbackListener<LocalMedia?> {
//                                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
//                                        mViewModel.upload(File(result[0]!!.realPath), 3)
//                                        Glide.with(this@MainActivity)
//                                            .load(result[0]!!.realPath)
//                                            .centerCrop()
//                                            .placeholder(R.color.app_color_f6)
//                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                            .into(img)
//                                        gonell.visibility = View.GONE
//                                        img.visibility = View.VISIBLE
//                                        showLoading()
//
//                                    }
//
//                                    override fun onCancel() {
//                                        hideLoading()
//                                    }
//                                })
//                        }
//
//                    })
//                    .setOnFourListener(object : CommitImgDialog.OnFourImageListener {
//                        override fun onClick(dialog: Dialog, img: ImageView, gonell: LinearLayout) {
//                            PictureSelector.create(this@MainActivity)
//                                .openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1)
//                                .setQueryOnlyMimeType("image/jpeg", "image/png", "image/jpg")
//                                .setImageEngine(GlideEngine.createGlideEngine())
//                                .forResult(object : OnResultCallbackListener<LocalMedia?> {
//                                    override fun onResult(result: java.util.ArrayList<LocalMedia?>) {
//                                        mViewModel.upload(File(result[0]!!.realPath), 4)
//                                        Glide.with(this@MainActivity)
//                                            .load(result[0]!!.realPath)
//                                            .centerCrop()
//                                            .placeholder(R.color.app_color_f6)
//                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                            .into(img)
//                                        gonell.visibility = View.GONE
//                                        img.visibility = View.VISIBLE
//                                        showLoading()
//
//                                    }
//
//                                    override fun onCancel() {
//                                        hideLoading()
//                                    }
//                                })
//                        }
//
//                    })
//                    .create()
//                    .show()
//            }
//        })


        LogUtils.getInstance().init(BaseApplication.getInstance().getMediaTApplication())


        mDatabind.zzTv.text = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.UNIT_NAME)
        mDatabind.czTv.text = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.SITE_NAME)
        mDatabind.xmTv.text = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.USER_NAME) + "(${
            MmkvHelper.getInstance().getString(Constant.MmKv_KEY.LOGIN_ACOUNT)
        })"
        mDatabind.sjTv.text = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.PHONE)
        mDatabind.dayinjiImg.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                isconnect = MmkvHelper.getInstance().getBoolean(MmKv_KEY.ISCONNECT)
//                isconnect = true
                if (isconnect) {
                    DialogUtil.showSelectDialog(
                        this@MainActivity,
                        "条码打印",
                        arrayOf(
                            "仅打印一个【托码】标签",
                            "扫箱码后补打【托码】标签",
                            "补打【箱码】标签",
                            "打印机油标签",
                            "补打破损标签"
                        )
                    ) { position, text ->
                        when (position) {
                            0 -> {
                                OnlyPrintTMActivity.launch(this@MainActivity)
                            }

                            1 -> {
                                ScanBoxTMActivity.launch(this@MainActivity)
                            }

                            2 -> {
                                PatchworkXMActivity.launch(this@MainActivity, "")
                            }

                            3 -> {
                                EngineOilActivity.launch(this@MainActivity)
                            }

                            4 -> {
                                RepairDamagedActivity.launch(this@MainActivity)
                            }
                        }
                    }
                } else {
                    connectionBluetooth()
                }

            }

        })

        mPdaAdapter = HomePDAAdapter()
        mCarAdapter = HomeCARAdapter()

        mDatabind.rvPda.adapter = mPdaAdapter
        mDatabind.rvCar.adapter = mCarAdapter

        mDatabind.rvPda.addItemDecoration(GridSpaceItemDecoration(4, 10, 16))
        mDatabind.rvCar.addItemDecoration(GridSpaceItemDecoration(4, 10, 16))

        //PDA点击事件
        mPdaAdapter.setOnItemClickListener { adapter, view, position ->
            when (mPdaAdapter.data[position].id) {
                Constant.PDAType.SAOMAXIECHE -> {
                    //跳转到model中
                    ScanUnloadingActivity.launch(this)
                }

                Constant.PDAType.RUCHANGJIAOJIE -> {
                    //跳转到model中
                    EntryHandoverActivity.launch(this)
                }

                Constant.PDAType.SAOMAZHUANGCHE -> {
                    //跳转到model中
                    ScanloadingActivity.launch(this)
                }

                Constant.PDAType.CHUCHANGJIAOJIE -> {
                    //跳转到model中
                    ExitHandoverActivity.launch(this)
                }

                Constant.PDAType.FENJIANMAFANG -> {
                    //跳转到model中
                    SortingStackActivity.launch(this)
                }

                Constant.PDAType.KUQUTIAOZHENG -> {
                    //跳转到model中
                    SelectAreaActivity.launch(this, 1)
                }

                Constant.PDAType.KUCUNPANDIAN -> {
                    //跳转到model中
                    AreaSelectActivity.launch(this)
                }
            }
        }

        //CAR点击事件
        mCarAdapter.setOnItemClickListener { adapter, view, position ->
            when (mCarAdapter.data[position].id) {
                Constant.CARType.XIECHEMINGXI -> {
                    //跳转到model中
                    UnLoadingDetailActivity.launch(this)
                }

                Constant.CARType.ZHUANGCHEMINGXI -> {
                    //跳转到model中
                    LoadingDetailActivity.launch(this)
                }

                Constant.CARType.ZHUANGXIECHEHUZONG -> {
                    //跳转到model中
                    OrderDetailActivity.launch(this)
                }

                Constant.CARType.PANDIANCHAXUN -> {
                    //跳转到model中
                    InventoryDetailActivity.launch(this)
                }

                Constant.CARType.KUQUTIAOZHENGMINGXI -> {
                    //跳转到model中
                    AreaAdjustDetailActivity.launch(this)
                }
            }
        }

        initPDAData()
        initCARData()
    }


    private fun initPDAData() {
        val trainList = arrayListOf<HomePDABean>()
//        if (AuthorUtil.isInRole(AuthorUtil.studyCourse)) {
        trainList.add(
            HomePDABean(
                Constant.PDAType.SAOMAXIECHE,
                "扫码卸车",
                R.mipmap.saomaxieche
            )
        )
//        }

        trainList.add(
            HomePDABean(
                Constant.PDAType.RUCHANGJIAOJIE,
                "入场交接",
                R.mipmap.ruchangjiaojie
            )
        )
        trainList.add(
            HomePDABean(
                Constant.PDAType.SAOMAZHUANGCHE,
                "扫码装车",
                R.mipmap.saomazhuangche
            )
        )
        trainList.add(
            HomePDABean(
                Constant.PDAType.CHUCHANGJIAOJIE,
                "出场交接",
                R.mipmap.chuchangjiaojie
            )
        )
        trainList.add(
            HomePDABean(
                Constant.PDAType.FENJIANMAFANG,
                "分拣码放",
                R.mipmap.fenjianmafang
            )
        )
        trainList.add(
            HomePDABean(
                Constant.PDAType.KUQUTIAOZHENG,
                "库区调整",
                R.mipmap.kuqutiaozheng
            )
        )
        trainList.add(
            HomePDABean(
                Constant.PDAType.KUCUNPANDIAN,
                "库存盘点",
                R.mipmap.kucunpandian
            )
        )

        mPdaAdapter.addData(trainList)
    }


    private fun initCARData() {
        val trainList = arrayListOf<HomeCARBean>()
//        if (AuthorUtil.isInRole(AuthorUtil.studyCourse)) {
        trainList.add(
            HomeCARBean(
                Constant.CARType.XIECHEMINGXI,
                "卸车明细",
                R.mipmap.xiechemingxi
            )
        )
//        }

        trainList.add(
            HomeCARBean(
                Constant.CARType.ZHUANGCHEMINGXI,
                "装车明细",
                R.mipmap.zhuangchemingxi
            )
        )
        trainList.add(
            HomeCARBean(
                Constant.CARType.ZHUANGXIECHEHUZONG,
                "订单查询",
                R.mipmap.dingdanchaxun
            )
        )
        trainList.add(
            HomeCARBean(
                Constant.CARType.PANDIANCHAXUN,
                "盘点查询",
                R.mipmap.pandianchaxun
            )
        )

        trainList.add(
            HomeCARBean(
                Constant.CARType.KUQUTIAOZHENGMINGXI,
                "库区调整明细",
                R.mipmap.kuqutiaozhengmingxi
            )
        )


        mCarAdapter.addData(trainList)
    }

    override fun createObserver() {
        mViewModel.mUrl.observe(this) { url ->
            mViewModel.mIndex.observe(this) { index ->
                when (index) {
                    1 -> {
                        YCImg1 =  url
                        hideLoading()

                    }

                    2 -> {
                        YCImg2 =  url
                        hideLoading()

                    }

                    3 -> {
                        YCImg3=  url
                        hideLoading()

                    }

                    4 -> {
                        YCImg4 =  url
                        hideLoading()

                    }

                    else -> {
                        hideLoading()

                    }
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val strIsConnected: String?
            when (resultCode) {
                RESULT_CANCELED -> {
                    MmkvHelper.getInstance()
                        .putString(MmKv_KEY.BTmac, data!!.getStringExtra("SelectedBDAddress"))

                    connectBT(data!!.getStringExtra("SelectedBDAddress"))
                }
            }
        } catch (e: Exception) {
            Log.e(
                "SDKSample", StringBuilder("Activity_Main --> onActivityResult ")
                    .append(e.message).toString()
            )
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //连接蓝牙
    private fun connectBT(BTmac: String?) {
        if (TextUtils.isEmpty(BTmac)) return
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("connect")
        progressDialog.show()
        object : Thread() {
            override fun run() {
                super.run()
                try {
                    val result = Print.PortOpen(context, "Bluetooth,$BTmac")
                    runOnUiThread {
                        if (result == 0) {
                            MmkvHelper.getInstance().putBoolean(MmKv_KEY.ISCONNECT, true)

                            ToastUtilsExt.info("连接成功")
                            val setJustification = Print.SetJustification(2)
                            if (setJustification != -1) {

                            } else {
                                ToastUtilsExt.info("打印机设置失败")
                            }

                        } else {
                            MmkvHelper.getInstance().putBoolean(MmKv_KEY.ISCONNECT, false)
                            ToastUtilsExt.info("连接失败" + result)
                        }

                    }
                    progressDialog.dismiss()
                } catch (e: Exception) {
                    progressDialog.dismiss()
                }
            }
        }.start()
    }


    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        fun launchClear(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }

    }
}