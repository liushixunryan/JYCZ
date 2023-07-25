package cn.ryanliu.jycz.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.BoxCode
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.bean.getProjectList
import cn.ryanliu.jycz.bean.prequest.PcreateTCode2
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityScanBoxTmactivityBinding
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.ScanBoxTMVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:扫箱码后补打【托码】标签
 */
class ScanBoxTMActivity : BaseActivity<ActivityScanBoxTmactivityBinding, ScanBoxTMVM>() {
    lateinit var mAdapter: TMBQAdapter
    lateinit var mXMAdapter: TMBQAdapter

    lateinit var bean: List<getProjectList>
    var xmmcid: Int = -1
    override fun layoutId(): Int = R.layout.activity_scan_box_tmactivity

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        mViewModel.getProjectList()

        mDatabind.etSmxm.setOnTouchListener(View.OnTouchListener { v, event ->
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

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "扫箱码后补打【托码】标签"

        mAdapter = TMBQAdapter();
        mDatabind.dyxmlbRv.adapter = mAdapter

        mXMAdapter = TMBQAdapter();
        mDatabind.tmRv.adapter = mXMAdapter

        onClick()
    }

    private fun onClick() {
        var p: MutableList<PcreateTCode2> = ArrayList();

        mDatabind.etSmxm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                mDatabind.etSmxm.setFocusable(true);
                mDatabind.etSmxm.setFocusableInTouchMode(true);
                mDatabind.etSmxm.requestFocus();
                if (!mDatabind.etSmxm.text.toString().isNullOrEmpty()){
                    if (xmmcid!=-1){
                        mAdapter.addData(BoxCode(mDatabind.etSmxm.text.toString()))
                        p.add(PcreateTCode2(mDatabind.etSmxm.text.toString(),xmmcid.toString()))
                        mDatabind.etZtjs.setText("${mAdapter.data.size}")

                        mDatabind.etSmxm.setText("")
                    }else{
                        mDatabind.etSmxm.setText("")
                        ToastUtilsExt.info("需要先选择项目名称")
                    }

                }else{
                    mDatabind.etSmxm.setText("")
                }

                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        mDatabind.btnTj.setOnClickListener(object : OnSingleClickListener() {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(view: View?) {

            }

        })


        mDatabind.btnSctm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mViewModel.createTCode2(p)
            }

        })

        mDatabind.btnPrinttm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (mDatabind.etZtjs.text.toString().isNullOrEmpty()) {
                    ToastUtilsExt.info("整件托数不能为空")
                } else {
                    if (mDatabind.etZtjs.text.toString().toInt() > 0) {
                        if (!mXMAdapter.data.isEmpty()) {
                            mViewModel.creategeneraltpcode(
                                mXMAdapter.data[0].box_code,
                                mDatabind.etZtjs.text.toString().toInt()
                            )
                        } else {
                            ToastUtilsExt.info("托码不能为空")
                        }

                    } else {
                        ToastUtilsExt.info("整件托数不能需要大于0")
                    }

                }


            }

        })

        mDatabind.btnClose.setOnClickListener {
            finish()
        }


    }

    override fun createObserver() {
        mViewModel.mSelect.observe(this) {
            if (it.isNullOrEmpty()){
                ToastUtilsExt.info("暂无项目信息")
                return@observe
            }
            bean = it!!
            val array = bean.map { it.project_name }.toTypedArray()
            mDatabind.etXmmc.text = it[0].project_name
            xmmcid = it[0].project_id
            mDatabind.etXmmc.setOnClickListener { v ->
                //创建一个xpopupview
                val attachPopupView: AttachPopupView = XPopup.Builder(context)
                    .hasShadowBg(false)
                    .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                    .popupWidth(mDatabind.etXmmc.width ?: 0)
                    .isCenterHorizontal(true) //是否与目标水平居中对齐
                    .popupPosition(PopupPosition.Bottom) //手动指定弹窗的位置
                    .atView(v) // 依附于所点击的View，内部会自动判断在上方或者下方显示
                    .asAttachList(
                        array,
                        intArrayOf(),
                        { position, text ->
                            mDatabind.etXmmc.text = text
                            for (i in bean!!.indices) {
                                if (text == bean[i].project_name) {
                                    xmmcid = bean[i].project_id
                                }
                            }
                        },
                        0,
                        0 /*, Gravity.LEFT*/
                    )
                attachPopupView.show()
            }
        }

        var a: MutableList<BoxCode> = ArrayList();
        mViewModel.mBackList.observe(this) {
            mXMAdapter.setNewInstance(null)
            a.clear()
            a.add(BoxCode(it.toString()))
            mXMAdapter.setList(a)
        }

        mViewModel.mCode.observe(this) {
            try {

                val printTM = PrintBCCodeType.PrintTM(it!!.img_data)
                if (printTM != -1) {
                    //切纸
                    Print.GotoNextLabel()
                } else {
                    ToastUtilsExt.info("打印错误")
                }

            } catch (e: java.lang.Exception) {
                Log.e(
                    "SDKSample",
                    java.lang.StringBuilder("Activity_Main --> onClickWIFI ").append(e.message)
                        .toString()
                )
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, ScanBoxTMActivity::class.java)
            context.startActivity(intent)
        }

    }
}