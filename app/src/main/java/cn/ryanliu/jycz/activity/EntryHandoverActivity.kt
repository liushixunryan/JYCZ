package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.EntryHandoverAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.EntryHandoverBean
import cn.ryanliu.jycz.common.constant.Constant
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

        mDatabind.etYylx.text = "车牌号"

        mAdapter = EntryHandoverAdapter()
        mDatabind.entryhandiverRv.adapter = mAdapter


        onClick();
    }

    private fun onClick() {
        mDatabind.btnSelect.setOnClickListener {
            mDatabind.loadingLayout.showLoading()
            mViewModel.searchTask(
                Constant.onINModel.RUCHANGJIAOJIE,
                mDatabind.etGjz.text.toString(),mDatabind.etYylx.text.toString(),
            )
        }

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
                        EntryPhotoActivity.launch(
                            this@EntryHandoverActivity,
                            Constant.onINModel.RUCHANGJIAOJIE,
                            selectBean[i]
                        )
                    } else {
                        isSelect.add(i)
                    }
                }

                if (isSelect.size == selectBean.size) {
                    ToastUtilsExt.info("您未选中任何一条数据")
                    return
                }


            }
        })
    }

    override fun onResume() {
        super.onResume()
        mDatabind.loadingLayout.showLoading()
        mViewModel.searchTask(
            Constant.onINModel.RUCHANGJIAOJIE,
            mDatabind.etGjz.text.toString(),mDatabind.etYylx.text.toString()
        )

    }

    override fun createObserver() {
        mViewModel.mSelectCar.observe(this) {
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
            val intent = Intent(context, EntryHandoverActivity::class.java)
            context.startActivity(intent)
        }

    }
}