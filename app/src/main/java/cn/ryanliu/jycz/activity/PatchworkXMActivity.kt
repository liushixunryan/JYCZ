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
import cn.ryanliu.jycz.bean.getProjectList
import cn.ryanliu.jycz.bean.prequest.Boxcode
import cn.ryanliu.jycz.bean.prequest.Pcreategeneralboxcode
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityPatchworkXmactivityBinding
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.PatchworkXMVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:补打【箱码】标签
 */
class PatchworkXMActivity : BaseActivity<ActivityPatchworkXmactivityBinding, PatchworkXMVM>() {
    lateinit var mAdapter: TMBQAdapter

    lateinit var list: Pcreategeneralboxcode
    lateinit var boxlist: MutableList<Boxcode>
    lateinit var bean: List<getProjectList>
    var xmmcid: Int = -1
    override fun layoutId(): Int = R.layout.activity_patchwork_xmactivity

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        mViewModel.getProjectList()
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
        mDatabind.inNavBar.tvNavTitle.text = "补打【箱码】标签"
        mDatabind.etZtjs.setText("1")


        mAdapter = TMBQAdapter();
        mDatabind.tmRv.adapter = mAdapter



        onClick()
    }


    private fun onClick() {


        mDatabind.etSmtm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                if (!mDatabind.etSmtm.text.toString().isNullOrEmpty()) {

                    mDatabind.xmtmhTv.text = mDatabind.etSmtm.text
                    mDatabind.etSmtm.setText("")
                    mDatabind.etSmtm.setFocusable(true);
                    mDatabind.etSmtm.setFocusableInTouchMode(true);
                    mDatabind.etSmtm.requestFocus();
                    return@setOnEditorActionListener true
                } else {
                    mDatabind.etSmtm.setText("")
                    mDatabind.etSmtm.setFocusable(true);
                    mDatabind.etSmtm.setFocusableInTouchMode(true);
                    mDatabind.etSmtm.requestFocus();
                    return@setOnEditorActionListener true
                }

            }

            return@setOnEditorActionListener false
        }

        mDatabind.btnTj.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mDatabind.xmtmhTv.text = mDatabind.etSmtm.text
            }

        })

        mDatabind.btnScxm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                mViewModel.createTCode2(
                    mDatabind.etZtjs.text.toString().toInt(),
                    mDatabind.xmtmhTv.text.toString(), xmmcid.toString()
                )

            }

        })
        mDatabind.btnPrinttm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (!mAdapter.data.isNullOrEmpty()) {
                    boxlist = ArrayList()
                    for (i in mAdapter.data.indices) {
                        boxlist.add(Boxcode(mAdapter.data[i].box_code))
                    }


                    list = Pcreategeneralboxcode(boxlist)

                    mViewModel.creategeneralboxcode(
                        list
                    )
                } else {
                    ToastUtilsExt.info("暂无箱码信息")
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

        mViewModel.mBackList.observe(this) {
            if (it != null) {
                mAdapter.setList(it.box_code_list)
            }
        }

        mViewModel.mCode.observe(this) {
            try {
                for (i in mAdapter.data.indices) {
                    val printTM = PrintBCCodeType.PrintXM(it[i].img_data)
                    if (printTM != -1) {
                        //切纸
                        Print.GotoNextLabel()
                    } else {
                        ToastUtilsExt.info("打印错误")
                    }
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
        fun launch(context: Context, ispt: String) {
            val intent = Intent(context, PatchworkXMActivity::class.java)
            intent.putExtra("ispt", ispt)
            context.startActivity(intent)
        }


    }
}