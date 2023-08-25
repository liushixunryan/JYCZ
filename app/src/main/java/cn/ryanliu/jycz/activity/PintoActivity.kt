package cn.ryanliu.jycz.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.booth.BTActivity
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityPintoBinding
import cn.ryanliu.jycz.databinding.ActivitySortingStackBinding
import cn.ryanliu.jycz.util.DialogUtil
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.PintoVM
import cn.ryanliu.jycz.viewmodel.SortingStackVM
import com.tbruyelle.rxpermissions.RxPermissions
import com.xql.loading.TipDialog
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:拼码
 */
class PintoActivity : BaseActivity<ActivityPintoBinding, PintoVM>() {
    var AreaName = ""
    var AreaNameID = ""
    var isconnect = false

    private lateinit var mSoundPool: SoundPool
    private val soundID = HashMap<Int, Int>()

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

    override fun layoutId(): Int = R.layout.activity_pinto

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        soundID[0] = mSoundPool.load(this, R.raw.scan_0, 1);
        soundID[1] = mSoundPool.load(this, R.raw.error_1, 1);
        soundID[2] = mSoundPool.load(this, R.raw.repeat_2, 1);
        soundID[3] = mSoundPool.load(this, R.raw.complete_3, 1);

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

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "分拣码放 - 拼托"
        AreaName = intent.getStringExtra("AreaName").toString()
        AreaNameID = intent.getStringExtra("AreaNameID").toString()

        mDatabind.kqTv.text = AreaName

        onClick();
    }

    private fun onClick() {
        mDatabind.etSmtm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                if (mDatabind.tmTv.text.toString() == "") {
                    ToastUtilsExt.info("请先生成托码")
                    return@setOnEditorActionListener false
                } else {
                    mDatabind.etSmtm.setFocusable(true);
                    mDatabind.etSmtm.setFocusableInTouchMode(true);
                    mDatabind.etSmtm.requestFocus();
                    mViewModel.scanFjCode(
                        mDatabind.etSmtm.text.toString(),
                        mDatabind.tmTv.text.toString(),
                        "拼托",
                        mDatabind.zjtsTv.text.toString().toInt(),
                        AreaNameID
                    )
                    mDatabind.etSmtm.setText("")
                }
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        mDatabind.btnTj.setOnClickListener {
            if (mDatabind.tmTv.text.toString() == "") {
                ToastUtilsExt.info("请先生成托码")
                return@setOnClickListener
            } else {
                mViewModel.scanFjCode(
                    mDatabind.etSmtm.text.toString(),
                    mDatabind.tmTv.text.toString(),
                    "拼托",
                    mDatabind.zjtsTv.text.toString().toInt(),
                    AreaNameID
                )
            }
        }

        mDatabind.bdxmBtn.setOnClickListener {
            isconnect = MmkvHelper.getInstance().getBoolean(Constant.MmKv_KEY.ISCONNECT)
            if (isconnect) {
                PatchworkXMActivity.launch(this, "ispt")
            } else {
                connectionBluetooth()
            }

        }


        mDatabind.cxzdkqTv.setOnClickListener {
            val intent = Intent(this@PintoActivity, SelectAreaActivity::class.java)
            startActivityForResult(intent, SelectAreaActivity.REQUEST_CODE_XXKQ)
        }

        mDatabind.sctmTv.setOnClickListener {
            if (mDatabind.zjtsTv.text.toString().toInt() > 1) {
                mViewModel.createTboxCode1(mDatabind.zjtsTv.text.toString().toInt())
            } else {
                ToastUtilsExt.info("数量必须大于1")
            }
        }
    }

    override fun createObserver() {
        mViewModel.mSelectCar.observe(this) {
            when (it!!.voice_flag) {
                0 -> {
//                    SPD9202307W01G000122
//                    SPD9202307W01G000125
//                    SPD9202307W01G000126
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
                    showTipDialog(
                        "拼托扫描完成",
                        "提示"
                    ) {
                        onBackPressed()
                    }
                }
                else -> {

                }
            }

            mDatabind.xmtmhTv.text = it.box_code
            mDatabind.mddTv.text = it.rec_area
            mDatabind.shrTv.text = it.rec_man
            mDatabind.shdwTv.text = it.rec_unit


            mDatabind.etSmtm.setFocusable(true);
            mDatabind.etSmtm.setFocusableInTouchMode(true);
            mDatabind.etSmtm.requestFocus();
        }

        mViewModel.mtpNum.observe(this) {
            mDatabind.tmTv.text = it.toString()

            mDatabind.cxzdkqTv.setBackgroundColor(Color.parseColor("#CCCCCC"))
            mDatabind.cxzdkqTv.isEnabled = false
            mDatabind.zjtsTv.setBackgroundColor(Color.parseColor("#CCCCCC"))
            mDatabind.zjtsTv.isEnabled = false
            mDatabind.sctmTv.setBackgroundColor(Color.parseColor("#CCCCCC"))
            mDatabind.sctmTv.isEnabled = false

            mDatabind.etSmtm.setFocusable(true);
            mDatabind.etSmtm.setFocusableInTouchMode(true);
            mDatabind.etSmtm.requestFocus();
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            try {
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

            if (SelectAreaActivity.REQUEST_CODE_XXKQ == requestCode) {
                mDatabind.kqTv.setText(data?.getStringExtra("areaName") ?: "")
                AreaNameID = data?.getIntExtra("areaId", 0).toString()
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



    /**
     * 显示提示类型Dialog
     */
    fun showTipDialog(
        msg: String,
        title: String,
        subbtn: TipDialog.SubmitListener?
    ): TipDialog? {
        if (tipDialog != null && !tipDialog!!.isShowing) {
            tipDialog!!.setMessage(msg).isShowCancel(false).setSubmitListener(subbtn).show()
        }
        return tipDialog
    }

    /**
     * 隐藏提示类型Dialog
     */
    fun hideTipDialog() {
        if (tipDialog != null && tipDialog!!.isShowing) {
            tipDialog!!.dismiss()
        }
    }

    companion object {
        fun launch(context: Context, AreaName: String, AreaNameID: String) {
            val intent = Intent(context, PintoActivity::class.java)
            intent.putExtra("AreaName", AreaName)
            intent.putExtra("AreaNameID", AreaNameID)
            context.startActivity(intent)
        }

    }
}