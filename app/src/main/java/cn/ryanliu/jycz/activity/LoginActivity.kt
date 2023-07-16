package cn.ryanliu.jycz.activity

import android.Manifest
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.booth.BTActivity
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.common.constant.Constant.MmKv_KEY
import cn.ryanliu.jycz.databinding.ActivityLoginBinding
import cn.ryanliu.jycz.util.DialogUtil
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.PublicAction
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.LoginVM
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.tbruyelle.rxpermissions.RxPermissions
import print.Print

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginVM>() {
    //打印测试类
    private var PAct: PublicAction? = null

    private var screenHeight = 0 //屏幕高度
    private var keyHeight = 0 //软件盘弹起后所占高度
    private val REGEX_LOGIN = "[A-Za-z0-9]+"
    override fun layoutId(): Int = R.layout.activity_login

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


    @SuppressLint("SetTextI18n")
    override fun initView() {
        Requestspermissions()
        mDatabind.etMobile.setText(MmkvHelper.getInstance().getString(Constant.MmKv_KEY.user))
        mDatabind.etPassword.setText(MmkvHelper.getInstance().getString(Constant.MmKv_KEY.psd))
        MmkvHelper.getInstance().getString(Constant.MmKv_KEY.TOKEN)

        PAct = PublicAction(context)
        mDatabind.versionnum.text = "版本号：v${AppUtils.getAppVersionName()}"
        mDatabind.versionnum.setOnClickListener {
            connectionBluetooth()
        }



        screenHeight = this.resources.displayMetrics.heightPixels //获取屏幕高度
        keyHeight = screenHeight / 3 //弹起高度为屏幕高度的1/3

        mDatabind.etMobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(s) && mDatabind.ivCleanPhone.visibility == View.GONE) {
                    mDatabind.ivCleanPhone.visibility = View.VISIBLE
                } else if (TextUtils.isEmpty(s)) {
                    mDatabind.ivCleanPhone.visibility = View.GONE
                }
            }
        })
        mDatabind.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(s) && mDatabind.cleanPassword.visibility == View.GONE) {
                    mDatabind.cleanPassword.visibility = View.VISIBLE
                } else if (TextUtils.isEmpty(s)) {
                    mDatabind.cleanPassword.visibility = View.GONE
                }
                if (s.toString().isEmpty()) {
                    return
                }
                if (!s.toString().matches(Regex(REGEX_LOGIN))) {
                    val temp = s.toString()
                    Toast.makeText(
                        this@LoginActivity, "请输入数字或字母", Toast.LENGTH_SHORT
                    ).show()
                    s.delete(temp.length - 1, temp.length)
                    mDatabind.etPassword.setSelection(s.length)
                }
            }
        })

        mDatabind.btnLogin.setOnClickListener {
            try {
                val result = Print.PortOpen(
                    context,
                    "Bluetooth,${MmkvHelper.getInstance().getString(MmKv_KEY.BTmac)}"
                )
                runOnUiThread {
                    if (result == 0) {
                        MmkvHelper.getInstance()
                            .putBoolean(Constant.MmKv_KEY.ISCONNECT, true)
                    } else {
                        MmkvHelper.getInstance().putBoolean(Constant.MmKv_KEY.ISCONNECT, false)
                    }
                }
            } catch (e: Exception) {
            }

            KeyboardUtils.hideSoftInput(this)
            if (mDatabind.etMobile.text.isEmpty()) {
                ToastUtilsExt.info("请输入用户名")
                return@setOnClickListener
            }
            if (mDatabind.etPassword.text.isEmpty()) {
                ToastUtilsExt.info("请输入密码")
                return@setOnClickListener
            }
            mViewModel.login(
                mDatabind.etMobile.text.toString(),
                mDatabind.etPassword.text.toString()
            )
        }

        mDatabind.ivCleanPhone.setOnClickListener { mDatabind.etMobile.setText("") }
        mDatabind.cleanPassword.setOnClickListener { mDatabind.etPassword.setText("") }
        mDatabind.ivShowPwd.setOnClickListener {
            if (mDatabind.etPassword.inputType != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                mDatabind.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                mDatabind.ivShowPwd.setImageResource(R.mipmap.pass_visuable)
            } else {
                mDatabind.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                mDatabind.ivShowPwd.setImageResource(R.mipmap.pass_gone)
            }
            val pwd = mDatabind.etPassword.text.toString()
            if (!TextUtils.isEmpty(pwd)) mDatabind.etPassword.setSelection(pwd.length)
        }

    }

    override fun createObserver() {
        mViewModel.loginResponseLV.observe(this) {
            MmkvHelper.getInstance()
                .putString(Constant.MmKv_KEY.user, mDatabind.etMobile.text.toString() ?: "")
            MmkvHelper.getInstance()
                .putString(Constant.MmKv_KEY.psd, mDatabind.etPassword.text.toString() ?: "")

            MainActivity.launchClear(this)
            finish()
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
                            MmkvHelper.getInstance()
                                .putBoolean(Constant.MmKv_KEY.ISCONNECT, true)
                        } else {
                            MmkvHelper.getInstance()
                                .putBoolean(Constant.MmKv_KEY.ISCONNECT, false)
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
     * 权限申请
     */
    @SuppressLint("WrongConstant")
    private fun Requestspermissions() {
        //获取蓝牙动态权限
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(
            WRITE_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION,
            READ_EXTERNAL_STORAGE, CAMERA,
            BLUETOOTH_ADMIN,
            BLUETOOTH
        ).subscribe {
            if (it) {

            } else {
                DialogUtil.showNotifyDialog(
                    this@LoginActivity, "权限申请", "程序运行需要权限，请到应用设置中开启。", "确定"
                ) {
                    finish()
                }
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

    }
}