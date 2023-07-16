package cn.ryanliu.jycz.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.booth.BTActivity
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityProjectBinding
import cn.ryanliu.jycz.util.DialogUtil
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.ProjectVM
import com.tbruyelle.rxpermissions.RxPermissions
import print.Print


/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:项目预约
 */
class ProjectActivity : BaseActivity<ActivityProjectBinding, ProjectVM>() {
    var carNum = ""
    var areaId = ""
    var areaName = ""
    var smxs: Int = 0
    private var pageModel: Int = 0
    private var hand_task_id: Int = 0
    var isconnect = false
    private lateinit var mSoundPool: SoundPool
    private val soundID = HashMap<Int, Int>()

    override fun layoutId(): Int = R.layout.activity_project

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        mDatabind.etSmxm.setOnTouchListener(OnTouchListener { v, event ->
            val inType: Int = mDatabind.etSmxm.getInputType()
            mDatabind.etSmxm.setInputType(InputType.TYPE_NULL)
            mDatabind.etSmxm.onTouchEvent(event)
            mDatabind.etSmxm.setInputType(inType)
            val text: CharSequence = mDatabind.etSmxm.getText()
            if (text is Spannable) {
                val spanText = text as Spannable
                Selection.setSelection(spanText, text.length)
            }
            true
        })
        mDatabind.etSmxm.requestFocus();

        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        soundID[0] = mSoundPool.load(this, R.raw.scan_0, 1);
        soundID[1] = mSoundPool.load(this, R.raw.error_1, 1);
        soundID[2] = mSoundPool.load(this, R.raw.repeat_2, 1);
        soundID[3] = mSoundPool.load(this, R.raw.complete_3, 1);


        mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：${smxs}"
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        pageModel = intent.getIntExtra("edit", 0)
        if (pageModel == Constant.PageModel.XIECHE) {
            mDatabind.inNavBar.tvNavTitle.text = "扫码卸车"
            mDatabind.inNavBar.tvNavCenter.visibility = View.VISIBLE
            mDatabind.inNavBar.tvNavRight.visibility = View.VISIBLE
        } else {
            mDatabind.inNavBar.tvNavTitle.text = "扫码装车"
            mDatabind.inNavBar.tvNavCenter.visibility = View.VISIBLE
            mDatabind.inNavBar.tvNavRight.visibility = View.GONE
            mDatabind.qrxcwcBtn.text = "确认装车完成"
        }

        carNum = intent.getStringExtra("carnumber").toString()
        areaId = intent.getStringExtra("areaid").toString()
        areaName = intent.getStringExtra("areaname").toString()


        mViewModel.getScanOrders(
            carNum, if (pageModel == Constant.PageModel.XIECHE) {
                "卸车"
            } else {
                "装车"
            }, "项目预约", if (pageModel == Constant.PageModel.XIECHE) {
                "卸车任务"
            } else {
                "装车任务"
            }
        )

        onClick()
    }

    var order_id = ""
    private fun onClick() {
        mDatabind.bdtmbqBtn.setOnClickListener {
            isconnect = MmkvHelper.getInstance().getBoolean(Constant.MmKv_KEY.ISCONNECT)
            if (isconnect) {
                DialogUtil.showSelectDialog(
                    this,
                    "条码打印",
                    arrayOf("仅打印一个【托码】标签", "扫箱码后补打【托码】标签", "补打【箱码】标签", "打印机油标签")
                ) { position, text ->
                    when (position) {
                        0 -> {
                            OnlyPrintTMActivity.launch(this)
                        }
                        1 -> {
                            ScanBoxTMActivity.launch(this)
                        }

                        2 -> {
                            PatchworkXMActivity.launch(this, "")
                        }
                        3 -> {
                            EngineOilActivity.launch(this)
                        }
                    }
                }
            } else {
                connectionBluetooth()
            }
        }

        //点击订单数量
        mDatabind.indentNumTv.setOnClickListener {
            IndentNumActivity.launch(
                this@ProjectActivity,
                Constant.PageModel.XIECHE,
                hand_task_id,
                carNum
            )
        }
        //如果是托码就能点击
        mDatabind.xsTv.setOnClickListener {
            if (mDatabind.smlxTv.text.toString() == "托码") {
                XMListActivity.launch(
                    this,
                    order_id.toInt()
                )
            } else {
                ToastUtilsExt.info("只有托码可以查看详情")
            }
        }
        mDatabind.wtdhTv.setOnClickListener {
            XMListActivity.launch(
                this,
                order_id.toInt()
            )
        }
        mDatabind.etSmxm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                mViewModel.getScanInCode(
                    hand_task_id, if (pageModel == Constant.PageModel.XIECHE) {
                        "卸车"
                    } else {
                        "装车"
                    }, mDatabind.etSmxm.text.toString(), carNum, "项目预约", areaId
                )
                mDatabind.etSmxm.setText("")
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        //点击提交
        mDatabind.btnTj.setOnClickListener {
            mViewModel.getScanInCode(
                hand_task_id, if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }, mDatabind.etSmxm.text.toString(), carNum, "项目预约", areaId
            )
            mDatabind.etSmxm.setText("")
        }

        //确认卸车完成
        mDatabind.qrxcwcBtn.setOnClickListener {
            mViewModel.submitSaveIn(
                hand_task_id,
                if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                },
            )
        }
    }

    override fun createObserver() {
        mViewModel.mData.observe(this) {
            if (it.toString() != "null") {
                mDatabind.inNavBar.tvNavRight.text = "库区：${areaName}"

                mDatabind.carDataTv.text = "${it?.car_number}    项目预约数量：${it?.reservation_num}"
                mDatabind.indentNumTv.text = "${it?.order_num}"
                mDatabind.zxsTv.text = "${it?.goods_num}"
                mDatabind.yszxsTv.text = "${it?.yes_scan_num}"
                mDatabind.wsxsTv.text = "${it?.no_scan_num}"
                hand_task_id = it!!.hand_task_id

            }
        }

        mViewModel.mDatacode.observe(this) {
            if (it != null) {

                when (it!!.voice_flag) {
                    0 -> {
                        mSoundPool.play(soundID[0]!!, 1F, 1F, 0, 0, 1F);
                    }
                    1 -> {
                        mSoundPool.play(soundID[1]!!, 1F, 1F, 0, 0, 1F);
                    }
                    2 -> {
                        mSoundPool.play(soundID[2]!!, 1F, 1F, 0, 0, 1F);
                    }
                    3 -> {
                        mSoundPool.play(soundID[3]!!, 1F, 1F, 0, 0, 1F);
                    }
                    else -> {

                    }
                }


                smxs = smxs + it?.fact_scan_ok_count!!
                mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：${smxs}"
                order_id = it?.order_id.toString()
                mDatabind.iswarnTv.text = it?.scan_tips
                if (it?.scan_tips == "正常") {
                    mDatabind.iswarnImg.setImageResource(R.mipmap.suc)
                    mDatabind.iswarnTv.setTextColor(Color.parseColor("#333333"))
                } else {
                    mDatabind.iswarnImg.setImageResource(R.mipmap.warn)
                    mDatabind.iswarnTv.setTextColor(Color.parseColor("#FF0000"))
                }
                mDatabind.xmtmhTv.text = it?.scan_code.toString()
                mDatabind.mddTv.text = it?.rec_area.toString()
                mDatabind.smlxTv.text = it?.scan_type.toString()
                mDatabind.xsTv.text = it?.tp_num.toString()
                mDatabind.wtdhTv.text = it?.py_order_code.toString()

                mDatabind.yszxsTv.text = "${it?.yes_scan_num}"
                mDatabind.wsxsTv.text = "${it?.no_scan_num}"

            }

            mViewModel.mBackList.observe(this) {
                ToastUtilsExt.info("提交成功")
                MainActivity.launchClear(this)
            }
        }
    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val strIsConnected: String?
            when (resultCode) {
                RESULT_CANCELED -> {
                    MmkvHelper.getInstance().putString(
                        Constant.MmKv_KEY.BTmac,
                        data!!.getStringExtra("SelectedBDAddress")
                    )
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
                            MmkvHelper.getInstance().putBoolean(Constant.MmKv_KEY.ISCONNECT, true)
                            MmkvHelper.getInstance()
                                .putBoolean(Constant.MmKv_KEY.ISCONNECT, true)
                            ToastUtilsExt.info("连接成功")
                            val setJustification = Print.SetJustification(2)
                            if (setJustification != -1) {

                            } else {
                                ToastUtilsExt.info("打印机设置失败")
                            }

                        } else {
                            MmkvHelper.getInstance().putBoolean(Constant.MmKv_KEY.ISCONNECT, false)
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
        fun launch(
            context: Context,
            carnumber: String,
            pageModel: Int,
            areaid: String,
            areaname: String
        ) {
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra("carnumber", carnumber)
            intent.putExtra("edit", pageModel)
            intent.putExtra("areaid", areaid)
            intent.putExtra("areaname", areaname)
            context.startActivity(intent)
        }

    }
}