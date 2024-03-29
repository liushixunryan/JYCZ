package cn.ryanliu.jycz.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.booth.BTActivity
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.*
import cn.ryanliu.jycz.bean.prequest.Boxcode
import cn.ryanliu.jycz.bean.prequest.Pcreategeneralboxcode
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityOnlyPrintTmactivityBinding
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.OnlyPrintTMVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import com.tbruyelle.rxpermissions.RxPermissions
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:仅仅打印托码标签
 */
class OnlyPrintTMActivity : BaseActivity<ActivityOnlyPrintTmactivityBinding, OnlyPrintTMVM>() {
    lateinit var mAdapter: TMBQAdapter
    lateinit var list: Pcreategeneralboxcode
    lateinit var boxlist: MutableList<Boxcode>

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

    override fun layoutId(): Int = R.layout.activity_only_print_tmactivity

    override fun initView() {
        mViewModel.getProjectList()

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "仅打印一个【托码】标签"

        mAdapter = TMBQAdapter();
        mDatabind.tmRv.adapter = mAdapter

        onClick()
    }

    private fun onClick() {
        mDatabind.btnSctm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (mDatabind.etZtjs.text.toString()
                        .isNullOrEmpty()
                ) {
                    ToastUtilsExt.info("您必须输入大于0的数字")
                } else {
                    if (mDatabind.etZtjs.text.toString().toInt() > 0) {
                        mViewModel.createTCode1(mDatabind.etZtjs.text.toString(), xmmcid.toString())

                    }
                }
            }

        })

        mDatabind.btnClose.setOnClickListener {
            finish()
        }

        mDatabind.btnPrinttm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                if (mAdapter.data != null) {
                    boxlist = ArrayList()
                    boxlist.add(Boxcode(mAdapter.data[0].box_code))

                    list = Pcreategeneralboxcode(boxlist)

                    mViewModel.creategeneraltpcode(
                        mAdapter.data[0].box_code,
                        mDatabind.etZtjs.text.toString().toInt()
                    )

                }
            }

        })

    }

    lateinit var bean: List<getProjectList>
    var xmmcid: Int = -1
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
            mAdapter.setList(null)
            mAdapter.addData(0, BoxCode(it!!))
        }

        mViewModel.mCode.observe(this) {
            try {

                val printTM = PrintBCCodeType.PrintTM(it!!.img_data)
                if (printTM != -1) {
                    //切纸
                    Print.GotoNextLabel()
                } else {
                    ToastUtilsExt.info("打印错误")
                    connectionBluetooth()
                }

            } catch (e: java.lang.Exception) {
                ToastUtilsExt.info("打印机未连接")
                Log.e(
                    "SDKSample",
                    java.lang.StringBuilder("Activity_Main --> onClickWIFI ").append(e.message)
                        .toString()
                )
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val strIsConnected: String?
            when (resultCode) {
                RESULT_CANCELED -> {
                    MmkvHelper.getInstance().putString(Constant.MmKv_KEY.BTmac,data!!.getStringExtra("SelectedBDAddress"))

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
            val intent = Intent(context, OnlyPrintTMActivity::class.java)
            context.startActivity(intent)
        }

    }
}