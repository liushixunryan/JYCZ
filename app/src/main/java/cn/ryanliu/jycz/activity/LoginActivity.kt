package cn.ryanliu.jycz.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityLoginBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.LoginVM
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.KeyboardUtils

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginVM>() {

    private var screenHeight = 0 //屏幕高度
    private var keyHeight = 0 //软件盘弹起后所占高度
    private val REGEX_LOGIN = "[A-Za-z0-9]+"
    override fun layoutId(): Int = R.layout.activity_login

    @SuppressLint("SetTextI18n")
    override fun initView() {
        mDatabind!!.versionnum.text = "版本号：v${AppUtils.getAppVersionName()}"

        screenHeight = this.resources.displayMetrics.heightPixels //获取屏幕高度
        keyHeight = screenHeight / 3 //弹起高度为屏幕高度的1/3

        mDatabind!!.etMobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(s) && mDatabind!!.ivCleanPhone.visibility == View.GONE) {
                    mDatabind!!.ivCleanPhone.visibility = View.VISIBLE
                } else if (TextUtils.isEmpty(s)) {
                    mDatabind!!.ivCleanPhone.visibility = View.GONE
                }
            }
        })
        mDatabind!!.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(s) && mDatabind!!.cleanPassword.visibility == View.GONE) {
                    mDatabind!!.cleanPassword.visibility = View.VISIBLE
                } else if (TextUtils.isEmpty(s)) {
                    mDatabind!!.cleanPassword.visibility = View.GONE
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
                    mDatabind!!.etPassword.setSelection(s.length)
                }
            }
        })

        mDatabind!!.btnLogin.setOnClickListener {
            KeyboardUtils.hideSoftInput(this)
            if (mDatabind!!.etMobile.text.isEmpty()) {
                ToastUtilsExt.info("请输入用户名")
                return@setOnClickListener
            }
            if (mDatabind!!.etPassword.text.isEmpty()) {
                ToastUtilsExt.info("请输入密码")
                return@setOnClickListener
            }

            MainActivity.launch(this@LoginActivity)
//            mViewModel.login(
//                mDatabind!!.etMobile.text.toString(),
//                mDatabind!!.etPassword.text.toString()
//            )
        }

        mDatabind!!.ivCleanPhone.setOnClickListener { mDatabind!!.etMobile.setText("") }
        mDatabind!!.cleanPassword.setOnClickListener { mDatabind!!.etPassword.setText("") }
        mDatabind!!.ivShowPwd.setOnClickListener {
            if (mDatabind!!.etPassword.inputType != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                mDatabind!!.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                mDatabind!!.ivShowPwd.setImageResource(R.mipmap.pass_visuable)
            } else {
                mDatabind!!.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                mDatabind!!.ivShowPwd.setImageResource(R.mipmap.pass_gone)
            }
            val pwd = mDatabind!!.etPassword.text.toString()
            if (!TextUtils.isEmpty(pwd)) mDatabind!!.etPassword.setSelection(pwd.length)
        }

    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

    }
}