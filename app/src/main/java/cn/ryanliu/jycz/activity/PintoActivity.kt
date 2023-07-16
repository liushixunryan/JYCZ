package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityPintoBinding
import cn.ryanliu.jycz.databinding.ActivitySortingStackBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.PintoVM
import cn.ryanliu.jycz.viewmodel.SortingStackVM

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:拼码
 */
class PintoActivity : BaseActivity<ActivityPintoBinding, PintoVM>() {
    var AreaName = ""
    var AreaNameID = ""

    override fun layoutId(): Int = R.layout.activity_pinto

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
                        1,
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
                    1,
                    mDatabind.zjtsTv.text.toString().toInt(),
                    AreaNameID
                )
            }
        }

        mDatabind.bdxmBtn.setOnClickListener {
            PatchworkXMActivity.launch(this,"ispt")
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
            mDatabind.xmtmhTv.text = it?.box_code
            mDatabind.mddTv.text = it?.rec_area
            mDatabind.shrTv.text = it?.rec_man
            mDatabind.shdwTv.text = it?.rec_unit
        }

        mViewModel.mtpNum.observe(this) {
            mDatabind.tmTv.text = it.toString()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (SelectAreaActivity.REQUEST_CODE_XXKQ == requestCode) {
                mDatabind.kqTv.setText(data?.getStringExtra("areaName") ?: "")
                AreaNameID = data?.getIntExtra("areaId", 0).toString()
            }
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