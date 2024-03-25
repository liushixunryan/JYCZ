package cn.ryanliu.jycz.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.ryanliu.jycz.R

/**
 * @Author: lsx
 * @Date: 2024/3/25
 * @Description:
 */
open class CommitImgDialog : Dialog {
    var tv_title:TextView? = null
    var tv_cancel:TextView? = null
    var tv_confirm:TextView? = null
    var et_input:EditText? = null

    var oneimg: LinearLayout? = null
    var twoimg:LinearLayout? = null
    var threeimg:LinearLayout? = null
    var fourimg:LinearLayout? = null

    var img1: ImageView? = null
    var img2: ImageView? = null
    var img3: ImageView? = null
    var img4: ImageView? = null
    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView() {
        setContentView(R.layout.my_commit_img_popup)
        setCanceledOnTouchOutside(false)
        tv_title = findViewById(R.id.tv_title)
        tv_cancel = findViewById(R.id.tv_cancel)
        tv_confirm = findViewById(R.id.tv_confirm)
        et_input = findViewById(R.id.et_input)

        oneimg = findViewById(R.id.oneimg)
        twoimg = findViewById(R.id.twoimg)
        threeimg = findViewById(R.id.threeimg)
        fourimg = findViewById(R.id.fourimg)
        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)
        img4 = findViewById(R.id.img4)
    }

    class Builder(val context:Context){
        var confirmListener: OnConfirmListener? = null
        var cancelListener: OnCancelListener? = null
        var oneListener: OnoneImageListener? = null
        var twoListener: OnTwoImageListener? = null
        var threeListener: OnThreeImageListener? = null
        var fourListener: OnFourImageListener? = null
        var title: String? = null
        var content: String? = null
        var btConfirmText: String? = null
        var tvCancelText: String? = null
        var cancelIsVisibility: Boolean? = true

        fun setOnConfirmListener(confirmListener: OnConfirmListener): Builder {
            this.confirmListener = confirmListener
            return this
        }

        fun setOnCancelListener(cancelListener: OnCancelListener): Builder {
            this.cancelListener = cancelListener
            return this
        }

        fun setOnOneListener(oneListener: OnoneImageListener): Builder {
            this.oneListener = oneListener
            return this
        }

        fun setOnTwolListener(twoListener: OnTwoImageListener): Builder {
            this.twoListener = twoListener
            return this
        }

        fun setOnThreelListener(threeListener: OnThreeImageListener): Builder {
            this.threeListener = threeListener
            return this
        }

        fun setOnFourListener(fourListener: OnFourImageListener): Builder {
            this.fourListener = fourListener
            return this
        }


        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }



        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        // 点击确定按钮的文字
        fun setConfirmText(btConfirmText: String): Builder {
            this.btConfirmText = btConfirmText
            return this
        }

        //取消按钮的文字
        fun setCancelText(tvCancelText: String): Builder {
            this.tvCancelText = tvCancelText
            return this
        }

        fun setCancelIconIsVisibility(cancelIsVisibility: Boolean): Builder {
            this.cancelIsVisibility = cancelIsVisibility
            return this
        }

        fun create(): CommitImgDialog {
            val dialog = CommitImgDialog(context, R.style.custom_dialog2)
            if (!TextUtils.isEmpty(title)) {
                dialog.tv_title ?.text = "异常货物拍照"
            } else {
                dialog.tv_title?.text = "异常货物拍照"
            }



            dialog.tv_confirm?.text = this.btConfirmText ?: "取消"
            if (this.cancelIsVisibility!!) {
                dialog.tv_cancel?.text = this.tvCancelText ?: "保存"
            } else {
                dialog.tv_cancel?.visibility = View.GONE
            }

            if (cancelListener != null) {
                dialog.tv_cancel?.setOnClickListener { v -> cancelListener!!.onClick(dialog,dialog.et_input!!) }
            }
            if (confirmListener != null) {
                dialog.tv_confirm?.setOnClickListener { v -> confirmListener!!.onClick(dialog,dialog.et_input!!) }
            }


            if (oneListener != null){
                dialog.oneimg?.setOnClickListener { v -> oneListener!!.onClick(dialog,
                    dialog.img1!!,dialog.oneimg!!
                ) }
            }

            if (twoListener != null){
                dialog.twoimg?.setOnClickListener { v -> twoListener!!.onClick(dialog,
                    dialog.img2!!,dialog.twoimg!!) }
            }

            if (threeListener != null){
                dialog.threeimg?.setOnClickListener { v -> threeListener!!.onClick(dialog,
                    dialog.img3!!,dialog.threeimg!!) }
            }

            if (fourListener != null){
                dialog.fourimg?.setOnClickListener { v -> fourListener!!.onClick(dialog,
                    dialog.img4!!,dialog.fourimg!!) }
            }

            return dialog
        }

    }

    // 点击弹窗取消按钮回调
    interface OnCancelListener {
        fun onClick(dialog: Dialog,input:EditText)
    }

    // 点击弹窗跳转回调
    interface OnConfirmListener {
        fun onClick(dialog: Dialog,input:EditText)
    }

    // 点击第一个图片回调
    interface OnoneImageListener {
        fun onClick(dialog: Dialog,img:ImageView,gonell:LinearLayout)
    }

    // 点击第二个图片回调
    interface OnTwoImageListener {
        fun onClick(dialog: Dialog,img:ImageView,gonell:LinearLayout)
    }

    // 点击第三个图片回调
    interface OnThreeImageListener {
        fun onClick(dialog: Dialog,img:ImageView,gonell:LinearLayout)
    }

    // 点击第四个图片回调
    interface OnFourImageListener {
        fun onClick(dialog: Dialog,img:ImageView,gonell:LinearLayout)
    }
}