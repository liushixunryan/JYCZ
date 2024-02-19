package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.databinding.ActivityPatchworkXmactivityBinding
import cn.ryanliu.jycz.databinding.ActivityRepairDamagedBinding
import cn.ryanliu.jycz.viewmodel.PatchworkXMVM
import cn.ryanliu.jycz.viewmodel.RepairDamagedVM

/**
 * 补打破损标签
 */
class RepairDamagedActivity : BaseActivity<ActivityRepairDamagedBinding, RepairDamagedVM>() {


    override fun layoutId(): Int = R.layout.activity_repair_damaged

    override fun initView() {
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, RepairDamagedActivity::class.java)
            context.startActivity(intent)
        }


    }
}