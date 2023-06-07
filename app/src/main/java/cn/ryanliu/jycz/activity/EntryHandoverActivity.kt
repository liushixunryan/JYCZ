package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.EntryHandoverAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.EntryHandoverBean
import cn.ryanliu.jycz.databinding.ActivityEntryHandoverBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.EntryHandoverVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:入场交接
 */
class EntryHandoverActivity : BaseActivity<ActivityEntryHandoverBinding, EntryHandoverVM>() {
    lateinit var mAdapter: EntryHandoverAdapter
    lateinit var selectBean: MutableList<EntryHandoverBean>

    var reservationId = 0
    override fun layoutId(): Int = R.layout.activity_entry_handover
    override fun initView() {
        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "入场交接 - 选择卸车任务"

        mAdapter = EntryHandoverAdapter()
        mDatabind.entryhandiverRv.adapter = mAdapter

        var a = EntryHandoverBean(0, "京A96M56", 0)
        var b = EntryHandoverBean(1, "京AQW322W", 0)
        var d = EntryHandoverBean(2, "京A226MQW", 0)
        var c = EntryHandoverBean(3, "京A43QWW", 0)
        selectBean.add(a)
        selectBean.add(b)
        selectBean.add(c)
        selectBean.add(d)

        mAdapter.setList(selectBean)

        onClick();
    }

    private fun onClick() {
        mDatabind.etYylx.setOnClickListener {
            val isyesorno = listOf("车牌号", "扫描人")
            //创建一个xpopupview
            val attachPopupView: AttachPopupView = XPopup.Builder(context)
                .hasShadowBg(false)
                .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                .popupWidth(mDatabind.etYylx.width ?: 0)
                .isCenterHorizontal(true) //是否与目标水平居中对齐
                .popupPosition(PopupPosition.Bottom) //手动指定弹窗的位置
                .atView(it) // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(
                    isyesorno.toTypedArray(),
                    intArrayOf(),
                    { position, text ->
                        Log.e("sansuiban", "onCreate: ${position},${text}")
                        mDatabind.etYylx.text = text
                        reservationId = position
                    },
                    0,
                    0 /*, Gravity.LEFT*/
                )
            attachPopupView.show()
        }

        mDatabind.btnSureselect.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View?) {
                val isSelect = arrayListOf<Int>()
                for (i in selectBean.indices) {
                    if (selectBean[i].isselect == 1) {
                        Log.e("sansuiaban", "onSingleClick: ${selectBean[i].id}")
                        EntryPhotoActivity.launch(this@EntryHandoverActivity)
                    } else {
                        isSelect.add(i)
                    }
                }

                if (isSelect.size == selectBean.size) {
                    ToastUtilsExt.info("您未选中任何车牌号")
                    return
                }


            }
        })
    }

    override fun createObserver() {

    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, EntryHandoverActivity::class.java)
            context.startActivity(intent)
        }

    }
}