package cn.ryanliu.jycz.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.adapter.EntryHandoverAdapter
import cn.ryanliu.jycz.adapter.ExitHandoverAdapter
import cn.ryanliu.jycz.basic.BaseActivity
import cn.ryanliu.jycz.bean.EntryHandoverBean
import cn.ryanliu.jycz.bean.ExitHandoverBean
import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.databinding.ActivityExitHandoverBinding
import cn.ryanliu.jycz.util.ToastUtilsExt
import cn.ryanliu.jycz.viewmodel.ExitHandoverVM
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition

/**
 * @Author: lsx
 * @Date: 2023/6/8
 * @Description:出场交接
 */

class ExitHandoverActivity : BaseActivity<ActivityExitHandoverBinding, ExitHandoverVM>() {
    lateinit var mAdapter: EntryHandoverAdapter
    lateinit var selectBean: MutableList<EntryHandoverBean>

    var reservationId = 0
    override fun layoutId(): Int = R.layout.activity_exit_handover

    override fun initView() {
        selectBean = ArrayList();

        mDatabind.inNavBar.ivNavBack.visibility = View.VISIBLE
        mDatabind.inNavBar.ivNavBack.setOnClickListener {
            onBackPressed()
        }
        mDatabind.inNavBar.tvNavTitle.text = "出场交接 - 选择装车任务"

        mDatabind.etYylx.text = "车牌号"

        mAdapter = EntryHandoverAdapter()
        mDatabind.entryhandiverRv.adapter = mAdapter


        onClick();
    }

    private fun onClick() {
        mDatabind.btnSelect.setOnClickListener {
            mDatabind.loadingLayout.showLoading()
            mViewModel.searchTask(
                if (reservationId == 0) {
                    mDatabind.etGjz.text.toString()
                } else {
                    ""
                },
                Constant.onINModel.CHUCHANGJAOJIE,
                if (reservationId == 1) {
                    mDatabind.etGjz.text.toString()
                } else {
                    ""
                },
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
                ExitPhotoActivity.launch(
                    this@ExitHandoverActivity,
                    Constant.onINModel.CHUCHANGJAOJIE,
                    null
                )
                val isSelect = arrayListOf<Int>()
                for (i in selectBean.indices) {
                    if (selectBean[i].isselect == 1) {
                        ExitPhotoActivity.launch(
                            this@ExitHandoverActivity,
                            Constant.onINModel.CHUCHANGJAOJIE,
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
            if (reservationId == 0) {
                mDatabind.etYylx.text.toString()
            } else {
                ""
            },
            Constant.onINModel.CHUCHANGJAOJIE,
            if (reservationId == 1) {
                mDatabind.etYylx.text.toString()
            } else {
                ""
            },
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
            val intent = Intent(context, ExitHandoverActivity::class.java)
            context.startActivity(intent)
        }

    }
}