package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityInventoryCountBinding
import cn.ryanliu.jycz.util.UserUtil
import cn.ryanliu.jycz.viewmodel.InventoryCountVM
import com.xql.loading.TipDialog

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:库存盘点
 */
class InventoryCountActivity : BaseActivity<ActivityInventoryCountBinding, InventoryCountVM>() {
    // 提示窗对象
    private var tipDialog: TipDialog? = null
    private var invent_id: String = ""

    private lateinit var mSoundPool: SoundPool
    private val soundID = HashMap<Int, Int>()
    override fun layoutId(): Int = R.layout.activity_inventory_count

    override fun initView() {
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        soundID[0] = mSoundPool.load(this, R.raw.scan_0, 1);
        soundID[1] = mSoundPool.load(this, R.raw.error_1, 1);
        soundID[2] = mSoundPool.load(this, R.raw.repeat_2, 1);
        soundID[3] = mSoundPool.load(this, R.raw.complete_3, 1);

        invent_id = intent.getStringExtra("invent_id").toString()

        initDialog();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "库存盘点"

        onClick()


    }

    private fun onClick() {
        mDatabind.btnTj.setOnClickListener {
            mViewModel.scanMCode("库存盘点", mDatabind.etSmtm.text.toString())
        }
        mDatabind.etSmtm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                mViewModel.scanMCode("库存盘点", mDatabind.etSmtm.text.toString())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        mDatabind.ysxmTv.setOnClickListener {
//            XMListActivity.launch(this)
        }

        mDatabind.xsTv.setOnClickListener {
//            XMListActivity.launch(this)
        }

        mDatabind.khddhTv.setOnClickListener {
//            XMListActivity.launch(this)

        }

        mDatabind.qrpdwnBtn.setOnClickListener {
            mViewModel.getInventResult(invent_id)
        }
        mDatabind.qxbcpdBtn.setOnClickListener {
            showTipDialog("确认取消盘点？", {
                UserUtil.setPanDian("")

            }, {})
        }
    }

    /**
     * 初始化各种Dialog
     */
    private fun initDialog() {
        if (tipDialog == null) {
            tipDialog = TipDialog(this)
        }
    }

    /**
     * 显示提示类型Dialog
     */
    fun showTipDialog(
        msg: String,
        subbtn: TipDialog.SubmitListener?,
        canbtn: TipDialog.CancelListener
    ): TipDialog? {
        if (tipDialog != null && !tipDialog!!.isShowing) {
            tipDialog!!.setMessage(msg).isShowCancel(true).setSubmitListener(subbtn)
                .setCancelListener(canbtn).show()
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

                mDatabind.xmtmhTv.text = it?.scan_code
                mDatabind.mddTv.text = it?.rec_area
                mDatabind.shrTv.text = it?.rec_man
                mDatabind.shdwTv.text = it?.rec_unit
                mDatabind.smlxTv.text = it?.scan_type
                mDatabind.xsTv.text = it?.tp_num.toString()
                mDatabind.khddhTv.text = it?.py_order_code
            }

            mViewModel.mInventResult.observe(this) {
                if (it != null) {
                    InventoryResultActivity.launch(this, it, invent_id)
                }
            }
        }
    }

    companion object {
        fun launch(context: Context, invent_id: String?) {
            val intent = Intent(context, InventoryCountActivity::class.java)
            intent.putExtra("invent_id", invent_id)
            context.startActivity(intent)
        }

    }
}