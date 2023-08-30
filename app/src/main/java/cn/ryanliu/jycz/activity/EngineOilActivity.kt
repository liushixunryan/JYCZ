package cn.ryanliu.jycz.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.text.InputFilter
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.booth.BTActivity
import cn.ryanliu.jycz.adapter.EngineOilAdapter
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.*
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityEngineOilBinding
import cn.ryanliu.jycz.util.EditNoEnterFilter
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.EngineOilVM
import com.blankj.utilcode.util.KeyboardUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import com.tbruyelle.rxpermissions.RxPermissions
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:机油标签规格查询
 */
class EngineOilActivity : BaseActivity<ActivityEngineOilBinding, EngineOilVM>() {
    lateinit var mAdapter: EngineOilAdapter

    var selectBean: MutableList<searchOilModel>? = null

    lateinit var bean1: List<getProjectList>
    var xmmcid: Int = -1
    var isPrintnum: Int = 0

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

    override fun layoutId(): Int = R.layout.activity_engine_oil

    override fun initView() {
        mViewModel.getProjectList()
        mViewModel.getOilFactory()
        mDatabind.loadingLayout.showEmpty()

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "机油标签规格查询"

        mAdapter = EngineOilAdapter()
        mDatabind.enguneoilRv.adapter = mAdapter


        mDatabind.btnCx.setOnClickListener {
            if (mDatabind.etCj.text.toString().isNullOrEmpty()) {
                ToastUtilsExt.info("没有厂家信息")
                return@setOnClickListener
            }
            if (mDatabind.etXmmc.text.toString().isNullOrEmpty()) {
                ToastUtilsExt.info("没有项目信息")
                return@setOnClickListener
            }

            mViewModel.searchOilModel(
                mDatabind.etCj.text.toString(),
                mDatabind.etZtjs.text.toString(),
                xmmcid.toString()
            )
        }

        mDatabind.btnPrinttm.setOnClickListener {
            try {
                val isSelect = arrayListOf<Int>()
                for (i in selectBean!!.indices) {
                    if (selectBean!![i].isselect == 1) {
                        val popupView = XPopup.Builder(this)
                            .isDestroyOnDismiss(true) //对于只使用一次的弹窗对象，推荐设置这个
                            .isViewMode(true).asInputConfirm(
                                "请输入机油标签打印数量:", null, "1", "请在此处输入"
                            ) {
                                if (it.isNullOrEmpty()) {
                                    if (it.toInt() >= 1) {
                                        ToastUtilsExt.info("您输入的数字必须大于1")
                                        return@asInputConfirm
                                    }
                                    ToastUtilsExt.info("您为输入数据")
                                    return@asInputConfirm

                                }
                                Log.e("sansuiban", "initView: ${it}")
                                isPrintnum = it.toInt()

                                mViewModel.createoillabel(
                                    selectBean!![i].bar_code,
                                    selectBean!![i].label_model,
                                    selectBean!![i].brand_name,
                                    1
                                )

                            }
//                    commonItemView2.content = it
//                    listener?.onConfirm(it)

                        popupView.show()
                        popupView.post {
                            popupView.editText.inputType = EditorInfo.TYPE_CLASS_NUMBER
                            popupView.editText.filters =
                                arrayOf(
                                    InputFilter.LengthFilter(2),
                                    EditNoEnterFilter(popupView.editText)
                                )
                            popupView.editText.setSelection(popupView.editText.text.toString().length)
                            popupView.editText.imeOptions = EditorInfo.IME_ACTION_DONE
                            KeyboardUtils.showSoftInput(popupView.editText)
                        }


                    } else {
                        isSelect.add(i)
                    }
                }

                if (isSelect.size == selectBean!!.size) {
                    ToastUtilsExt.info("您未选中任何数据")
                    return@setOnClickListener
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

        mDatabind.btnClose.setOnClickListener {
            onBackPressed()
        }
    }

    lateinit var bean: List<getOilFactory>
    override fun createObserver() {
        mViewModel.mSelect.observe(this) {
            if (it.isNullOrEmpty()) {
                ToastUtilsExt.info("暂无项目信息")
                return@observe
            }
            bean1 = it
            val array = bean1.map { it.project_name }.toTypedArray()
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
                            for (i in bean1!!.indices) {
                                if (text == bean1[i].project_name) {
                                    xmmcid = bean1[i].project_id
                                }
                            }
                        },
                        0,
                        0 /*, Gravity.LEFT*/
                    )
                attachPopupView.show()
            }
        }


        mViewModel.mCode.observe(this) {
            for (i in 1..isPrintnum) {
                val printTM = PrintBCCodeType.PrintJYBQ(
                    it.img_data
                )

                if (printTM != -1) {
                    //切纸
                    Print.GotoNextLabel()
                } else {
                    ToastUtilsExt.info("打印错误")
                    connectionBluetooth()
                }
            }
        }


        mViewModel.mOilList.observe(this) {
            if (it.isNullOrEmpty()) {
                ToastUtilsExt.info("暂无机油标签厂家信息")
                return@observe
            }
            bean = it
            val array = bean.map { it.brand_name }.toTypedArray()
            mDatabind.etCj.text = it[0].brand_name
            //设备状态
            mDatabind.etCj.setOnClickListener { v ->
                //创建一个xpopupview
                val attachPopupView: AttachPopupView = XPopup.Builder(context)
                    .hasShadowBg(false)
                    .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                    .popupWidth(mDatabind.etCj.width ?: 0)
                    .isCenterHorizontal(true) //是否与目标水平居中对齐
                    .popupPosition(PopupPosition.Bottom) //手动指定弹窗的位置
                    .atView(v) // 依附于所点击的View，内部会自动判断在上方或者下方显示
                    .asAttachList(
                        array,
                        intArrayOf(),
                        { position, text ->
                            Log.e("sansuiban", "onCreate: ${position},${text}")
                            mDatabind.etCj.text = text
                        },
                        0,
                        0 /*, Gravity.LEFT*/
                    )
                attachPopupView.show()
            }
        }

        mViewModel.mList.observe(this) {
            if (it.isNullOrEmpty()) {
                mDatabind.loadingLayout.showEmpty()
            } else {
                mAdapter.setList(it)
                selectBean = it
                if (mAdapter.data.isEmpty()) {
                    mDatabind.loadingLayout.showEmpty()
                } else {
                    mDatabind.loadingLayout.showContent()
                }
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
                            MmkvHelper.getInstance()
                                .putBoolean(Constant.MmKv_KEY.ISCONNECT, true)

                            ToastUtilsExt.info("连接成功")
                            val setJustification = Print.SetJustification(2)
                            if (setJustification != -1) {

                            } else {
                                ToastUtilsExt.info("打印机设置失败")
                            }

                        } else {
                            MmkvHelper.getInstance()
                                .putBoolean(Constant.MmKv_KEY.ISCONNECT, false)
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
            val intent = Intent(context, EngineOilActivity::class.java)
            context.startActivity(intent)
        }

    }
}