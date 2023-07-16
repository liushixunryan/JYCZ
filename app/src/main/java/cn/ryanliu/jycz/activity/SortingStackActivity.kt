package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivitySortingStackBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.SortingStackVM

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:分拣码放
 */
class SortingStackActivity : BaseActivity<ActivitySortingStackBinding, SortingStackVM>() {
    var AreaID = ""


    private lateinit var mSoundPool: SoundPool
    private val soundID = HashMap<Int, Int>()
    override fun layoutId(): Int = R.layout.activity_sorting_stack

    override fun initView() {
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool = SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
        soundID[0] = mSoundPool.load(this, R.raw.scan_0, 1);
        soundID[1] = mSoundPool.load(this, R.raw.error_1, 1);
        soundID[2] = mSoundPool.load(this, R.raw.repeat_2, 1);
        soundID[3] = mSoundPool.load(this, R.raw.complete_3, 1);

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "分拣码放"

        onClick()
    }

    private fun onClick() {
        //点击搜索
        mDatabind.btnTj.setOnClickListener {
            mViewModel.scanMCode("分拣码放", mDatabind.etSmtm.text.toString())
        }
        mDatabind.etSmtm.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                mViewModel.scanMCode("分拣码放", mDatabind.etSmtm.text.toString())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
        //点击托码
        mDatabind.ctBtn.setOnClickListener {
            if (mDatabind.smlxTv.text == "托码") {
                mViewModel.doSplitTmp(mDatabind.xmtmhTv.text.toString(), "0")
            } else {
                ToastUtilsExt.info("类型不是托码")
            }

        }

        mDatabind.xsTv.setOnClickListener {
            XMListActivity.launch(this, 1, 0, mDatabind.wtdhTv.text.toString(), "全部")
        }

        mDatabind.wtdhTv.setOnClickListener {
            XMListActivity.launch(this, 1, 0, mDatabind.wtdhTv.text.toString(), "全部")

        }

        mDatabind.cxzdkqTv.setOnClickListener {
            val intent = Intent(this@SortingStackActivity, SelectAreaActivity::class.java)
            startActivityForResult(intent, SelectAreaActivity.REQUEST_CODE_XXKQ)
        }

        mDatabind.ptBtn.setOnClickListener {
            if (!mDatabind.xmtmhTv.text.isNullOrEmpty()) {
                PintoActivity.launch(
                    this,
                    mDatabind.kqTv.text.toString(),
                    AreaID
                )
            } else {
                ToastUtilsExt.info("请先扫码")
            }
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
                mDatabind.wtdhTv.text = it?.py_order_code
                mDatabind.kqTv.text = it?.ware_area_name
                AreaID = it?.ware_area.toString()
            }

            mViewModel.mBackList.observe(this) {
                if (it == "拆托成功") {
                    ToastUtilsExt.info("拆托成功")
                } else {
                    mDatabind.kqTv.setText(areaName)
                }
            }
        }
    }

    var areaName = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (SelectAreaActivity.REQUEST_CODE_XXKQ == requestCode) {
                mViewModel.changWareArea(
                    "分拣码放",
                    mDatabind.xmtmhTv.text.toString(),
                    (data?.getStringExtra("areaName") ?: "")
                )
                areaName = data?.getStringExtra("areaName") ?: ""
                AreaID = data?.getIntExtra("areaId", 0).toString()
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, SortingStackActivity::class.java)
            context.startActivity(intent)
        }

    }
}