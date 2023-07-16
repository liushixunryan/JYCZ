package cn.ryanliu.jycz.activity

import android.annotation.SuppressLint
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
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityDriverBinding
import cn.ryanliu.jycz.databinding.ActivityProjectBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.DriverVM
import cn.ryanliu.jycz.viewmodel.ProjectVM

class DriverActivity : BaseActivity<ActivityDriverBinding, DriverVM>() {
    var carNum = ""
    var areaId = ""
    var areaName = ""
    private var pageModel: Int = 0
    private var hand_task_id: Int = 0

    var smxs: Int = 0

    private lateinit var mSoundPool: SoundPool
    private val soundID = HashMap<Int, Int>()
    override fun layoutId(): Int = R.layout.activity_driver

    override fun initView() {
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

        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        soundID[0] = mSoundPool.load(this, R.raw.scan_0, 1);
        soundID[1] = mSoundPool.load(this, R.raw.error_1, 1);
        soundID[2] = mSoundPool.load(this, R.raw.repeat_2, 1);
        soundID[3] = mSoundPool.load(this, R.raw.complete_3, 1);

        mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：${smxs}"
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }

        pageModel = intent.getIntExtra("edit", 0)
        if (pageModel == Constant.PageModel.XIECHE) {
            mDatabind.inNavBar.tvNavTitle.text = "扫码卸车"
            mDatabind.inNavBar.tvNavCenter.visibility = View.VISIBLE
            mDatabind.inNavBar.tvNavRight.visibility = View.VISIBLE
        } else {
            mDatabind.inNavBar.tvNavTitle.text = "扫码装车"
            mDatabind.inNavBar.tvNavCenter.visibility = View.VISIBLE
            mDatabind.inNavBar.tvNavRight.visibility = View.GONE
            mDatabind.qrxcwcBtn.text = "确认装车完成"
        }




        carNum = intent.getStringExtra("carnumber").toString()
        areaId = intent.getStringExtra("areaid").toString()
        areaName = intent.getStringExtra("areaname").toString()

        mViewModel.getScanOrders(
            carNum, if (pageModel == Constant.PageModel.XIECHE) {
                "卸车"
            } else {
                "装车"
            }, "司机预约", if (pageModel == Constant.PageModel.XIECHE) {
                "卸车任务"
            } else {
                "装车任务"
            }
        )

        onClick()
    }

    private fun onClick() {
        mDatabind.etSmxm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                if (!mDatabind.etSmxm.text.toString().isNullOrEmpty()){
                    mViewModel.getScanInCode(
                        hand_task_id, if (pageModel == Constant.PageModel.XIECHE) {
                            "卸车"
                        } else {
                            "装车"
                        }, mDatabind.etSmxm.text.toString(), carNum, "司机预约", areaId
                    )
                    mDatabind.etSmxm.setText("")
                    return@setOnEditorActionListener true
                }else{
                    
                }

            }

            return@setOnEditorActionListener false
        }

        mDatabind.btnTj.setOnClickListener {
            mViewModel.getScanInCode(
                hand_task_id, if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                }, mDatabind.etSmxm.text.toString(), carNum, "司机预约", areaId
            )
            mDatabind.etSmxm.setText("")
        }
        //确认卸车完成
        mDatabind.qrxcwcBtn.setOnClickListener {
            mViewModel.submitSaveIn(
                hand_task_id,
                if (pageModel == Constant.PageModel.XIECHE) {
                    "卸车"
                } else {
                    "装车"
                },
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {
        mViewModel.mData.observe(this) {
            if (it.toString() != "null") {
                mDatabind.inNavBar.tvNavRight.text = "库区：${areaName}"

                mDatabind.carDataTv.text = "${it?.car_number}    项目预约数量：${it?.reservation_num}"
                mDatabind.ddslTv.text = "1"
                mDatabind.zxsTv.text = "${it?.goods_num}"
                mDatabind.yszxsTv.text = "${it?.yes_scan_num}"
                hand_task_id = it!!.hand_task_id

            }
        }

        mViewModel.mDatacode.observe(this) {
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

                smxs = smxs + it?.fact_scan_ok_count!!
                mDatabind.inNavBar.tvNavCenter.text = "扫描箱数：${smxs}"
                mDatabind.iswarnTv.text = it?.scan_tips
                if (it?.scan_tips == "正常") {
                    mDatabind.iswarnImg.setImageResource(R.mipmap.suc)
                    mDatabind.iswarnTv.setTextColor(Color.parseColor("#333333"))
                } else {
                    mDatabind.iswarnImg.setImageResource(R.mipmap.warn)
                    mDatabind.iswarnTv.setTextColor(Color.parseColor("#FF0000"))
                }
                mDatabind.etSmxm.setText("")
                mDatabind.xmtmhTv.text = it?.scan_code.toString()

                mDatabind.yszxsTv.text = "${it?.yes_scan_num}"

            }

            mViewModel.mBackList.observe(this) {
                ToastUtilsExt.info("提交成功")
                MainActivity.launchClear(this)
            }
        }
    }

    companion object {
        fun launch(
            context: Context,
            carnumber: String,
            pageModel: Int,
            areaid: String,
            areaname: String
        ) {
            val intent = Intent(context, DriverActivity::class.java)
            intent.putExtra("carnumber", carnumber)
            intent.putExtra("edit", pageModel)
            intent.putExtra("areaid", areaid)
            intent.putExtra("areaname", areaname)
            context.startActivity(intent)
        }

    }
}