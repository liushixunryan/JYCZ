package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityPintoBinding
import cn.ryanliu.jycz.databinding.ActivitySortingStackBinding
import cn.ryanliu.jycz.viewmodel.PintoVM
import cn.ryanliu.jycz.viewmodel.SortingStackVM

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:拼码
 */
class PintoActivity : BaseActivity<ActivityPintoBinding, PintoVM>() {
    override fun layoutId(): Int = R.layout.activity_pinto

    override fun initView() {
        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "分拣码放 - 拼托"

        onClick();
    }

    private fun onClick() {
        mDatabind.bdxmBtn.setOnClickListener {
            PatchworkXMActivity.launch(this)
        }

        mDatabind.cxzdkqTv.setOnClickListener {
            val intent = Intent(this@PintoActivity, SelectAreaActivity::class.java)
            startActivityForResult(intent, SelectAreaActivity.REQUEST_CODE_XXKQ)
        }
    }

    override fun createObserver() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (SelectAreaActivity.REQUEST_CODE_XXKQ == requestCode) {
                mDatabind.kqTv.setText(data?.getStringExtra("areaName") ?: "")
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, PintoActivity::class.java)
            context.startActivity(intent)
        }

    }
}