package cn.ryanliu.jycz.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.booth.BTActivity
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivitySortingStackBinding
import cn.ryanliu.jycz.util.DialogUtil
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.SortingStackVM
import com.tbruyelle.rxpermissions.RxPermissions
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:分拣码放
 */
class SortingStackActivity : BaseActivity<ActivitySortingStackBinding, SortingStackVM>() {
    var AreaID: Int = 0
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


    private lateinit var mSoundPool: SoundPool
    private val soundID = HashMap<Int, Int>()
    override fun layoutId(): Int = R.layout.activity_sorting_stack

    override fun initView() {
        mDatabind.etSmtm.setOnTouchListener(View.OnTouchListener { v, event ->
            val inType: Int = mDatabind.etSmtm.getInputType()
            mDatabind.etSmtm.setInputType(InputType.TYPE_NULL)
            mDatabind.etSmtm.onTouchEvent(event)
            mDatabind.etSmtm.setInputType(inType)
            val text: CharSequence = mDatabind.etSmtm.getText()
            if (text is Spannable) {
                val spanText = text as Spannable
                Selection.setSelection(spanText, text.length)
            }
            true
        })
        mDatabind.etSmtm.requestFocus();

        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        soundID[0] = mSoundPool.load(this, R.raw.scan_0, 1);
        soundID[1] = mSoundPool.load(this, R.raw.error_1, 1);
        soundID[2] = mSoundPool.load(this, R.raw.repeat_2, 1);
        soundID[3] = mSoundPool.load(this, R.raw.complete_3, 1);

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "分拣码放"

        onClick()
    }

    private fun onClick() {
        //点击搜索
        mDatabind.btnTj.setOnClickListener {
            mViewModel.scanMCode("分拣码放", mDatabind.etSmtm.text.toString())
        }
        mDatabind.etSmtm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                if (!mDatabind.etSmtm.text.toString().isNullOrEmpty()) {
                    mDatabind.etSmtm.setFocusable(true);
                    mDatabind.etSmtm.setFocusableInTouchMode(true);
                    mDatabind.etSmtm.requestFocus();
                    mViewModel.scanMCode("分拣码放", mDatabind.etSmtm.text.toString())

                    mDatabind.etSmtm.setText("")
                    return@setOnEditorActionListener true
                } else {

                }

            }

            return@setOnEditorActionListener false
        }
        //点击托码
        mDatabind.ctBtn.setOnClickListener {
            if (mDatabind.smlxTv.text == "托码") {
                mViewModel.doSplitTmp(mDatabind.xmtmhTv.text.toString(), "拆托")
            } else {
                ToastUtilsExt.info("类型不是托码")
            }

        }

        mDatabind.xsTv.setOnClickListener {
            XMListActivity.launch(this, 1, 0, mDatabind.wtdhTv.text.toString(), "全部")
        }

        mDatabind.wtdhTv.setOnClickListener {
            XMListActivity.launch(this, 1, 0, mDatabind.wtdhTv.text.toString(), "全部")

        }

        mDatabind.cxzdkqTv.setOnClickListener {
            val intent = Intent(this@SortingStackActivity, SelectAreaActivity::class.java)
            startActivityForResult(intent, SelectAreaActivity.REQUEST_CODE_XXKQ)
        }

        mDatabind.ptBtn.setOnClickListener {
            if (!mDatabind.xmtmhTv.text.isNullOrEmpty()) {
                PintoActivity.launch(
                    this,
                    mDatabind.kqTv.text.toString(),
                    AreaID.toString()
                )
            } else {
                ToastUtilsExt.info("请先扫码")
            }
        }

        mDatabind.dybqBtn.setOnClickListener {
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
    }

    override fun createObserver() {
        mViewModel.mSelectCar.observe(this) {
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

                mDatabind.xmtmhTv.text = it.scan_code
                mDatabind.mddTv.text = it.rec_area
                mDatabind.shrTv.text = it.rec_man
                mDatabind.shdwTv.text = it.rec_unit
                mDatabind.smlxTv.text = it.scan_type
                mDatabind.xsTv.text = it.tp_num.toString()
                mDatabind.wtdhTv.text = it.py_order_code
                mDatabind.kqTv.text = it.ware_area_name
                AreaID = it.ware_area
            }

            mViewModel.mBackList.observe(this) {
                if (it == "拆托成功") {
                    ToastUtilsExt.info("拆托成功")
                } else {
                    mDatabind.kqTv.setText(areaName)
                }
            }
        }
    }

    var areaName = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            val strIsConnected: String?
            when (resultCode) {
                AppCompatActivity.RESULT_CANCELED -> {
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
        if (RESULT_OK == resultCode) {
            if (SelectAreaActivity.REQUEST_CODE_XXKQ == requestCode) {
                mViewModel.changWareArea(
                    "分拣码放",
                    mDatabind.xmtmhTv.text.toString(),
                    (data?.getIntExtra("areaId", 0).toString())
                )
                areaName = data?.getStringExtra("areaName") ?: ""
                AreaID = data?.getIntExtra("areaId", 0)!!
            }
        }
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
        fun launch(context: Context) {
            val intent = Intent(context, SortingStackActivity::class.java)
            context.startActivity(intent)
        }

    }
}