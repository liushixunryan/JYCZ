package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.EngineOilAdapter
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.EngineOilBean
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.databinding.ActivityEngineOilBinding
import cn.ryanliu.jycz.viewmodel.EngineOilVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:机油标签规格查询
 */
class EngineOilActivity : BaseActivity<ActivityEngineOilBinding, EngineOilVM>() {
    lateinit var mAdapter: EngineOilAdapter
    override fun layoutId(): Int = R.layout.activity_engine_oil

    override fun initView() {

        //设备状态
        mDatabind.etCj.setOnClickListener { v ->
            val isyesorno = listOf("运行", "待机", "检修", "报废")
            //创建一个xpopupview
            val attachPopupView: AttachPopupView = XPopup.Builder(context)
                .hasShadowBg(false)
                .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                .popupWidth(mDatabind.etCj.width ?: 0)
                .isCenterHorizontal(true) //是否与目标水平居中对齐
                .popupPosition(PopupPosition.Bottom) //手动指定弹窗的位置
                .atView(v) // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(
                    isyesorno.toTypedArray(),
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

        mDatabind.btnCx.setOnClickListener {
            mAdapter.addData(EngineOilBean("dsda111"))
        }
        mAdapter = EngineOilAdapter()

        mDatabind.enguneoilRv.adapter = mAdapter
    }

    override fun createObserver() {
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, EngineOilActivity::class.java)
            context.startActivity(intent)
        }

    }
}