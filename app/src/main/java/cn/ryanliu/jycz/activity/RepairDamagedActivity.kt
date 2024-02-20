package cn.ryanliu.jycz.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.booth.BTActivity
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityPatchworkXmactivityBinding
import cn.ryanliu.jycz.databinding.ActivityRepairDamagedBinding
import cn.ryanliu.jycz.util.EditNoEnterFilter
import cn.ryanliu.jycz.util.MmkvHelper
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.PatchworkXMVM
import cn.ryanliu.jycz.viewmodel.RepairDamagedVM
import com.blankj.utilcode.util.KeyboardUtils
import com.lxj.xpopup.XPopup
import com.tbruyelle.rxpermissions.RxPermissions
import print.Print

/**
 * 补打破损标签
 */
class RepairDamagedActivity : BaseActivity<ActivityRepairDamagedBinding, RepairDamagedVM>() {

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

    override fun layoutId(): Int = R.layout.activity_repair_damaged

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "补打破损标签"

        //点击查询
        mDatabind.btnSctm.setOnClickListener {
            mViewModel.SearchBarcode(
                mDatabind.etTmgjz.text.toString()
            )
        }

        //点击生成条码
        mDatabind.btnPrinttm.setOnClickListener {
            try {
                mViewModel.RebarCode(
                    mDatabind.tmRv.text.toString(),
                )


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

    override fun createObserver() {

        mViewModel.mData.observe(this) {
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

        mViewModel.mCode.observe(this) {
            mDatabind.tmRv.setText(it.bar_code.toString())
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
            val intent = Intent(context, RepairDamagedActivity::class.java)
            context.startActivity(intent)
        }


    }
}