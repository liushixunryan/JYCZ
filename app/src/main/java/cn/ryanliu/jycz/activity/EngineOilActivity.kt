package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.EngineOilAdapter
import cn.ryanliu.jycz.adapter.TMBQAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.SelectCarBean
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.bean.getOilFactory
import cn.ryanliu.jycz.bean.searchOilModel
import cn.ryanliu.jycz.databinding.ActivityEngineOilBinding
import cn.ryanliu.jycz.util.PrintBCCodeType
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.EngineOilVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/5
 * @Description:机油标签规格查询
 */
class EngineOilActivity : BaseActivity<ActivityEngineOilBinding, EngineOilVM>() {
    lateinit var mAdapter: EngineOilAdapter

    var selectBean: MutableList<searchOilModel>? = null
    override fun layoutId(): Int = R.layout.activity_engine_oil

    override fun initView() {
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
            mViewModel.searchOilModel(
                mDatabind.etCj.text.toString(),
                mDatabind.etZtjs.text.toString()
            )
        }

        mDatabind.btnPrinttm.setOnClickListener {
            try {
                val isSelect = arrayListOf<Int>()
                for (i in selectBean!!.indices) {
                    if (selectBean!![i].isselect == 1) {
                        val printTM = PrintBCCodeType.PrintJYBQ(
                            selectBean!![i].`package`,
                            selectBean!![i].bar_code,
                            selectBean!![i].label_model,
                            selectBean!![i].brand_name
                        )

                        if (printTM == 1) {
                            //切纸
                            Print.GotoNextLabel()
                        } else {
                            ToastUtilsExt.info("打印错误")
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
        mViewModel.mOilList.observe(this) {
            bean = it!!
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

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, EngineOilActivity::class.java)
            context.startActivity(intent)
        }

    }
}