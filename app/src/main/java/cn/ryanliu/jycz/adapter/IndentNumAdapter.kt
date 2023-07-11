package cn.ryanliu.jycz.adapter

import android.widget.Button
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.HomePDABean
import cn.ryanliu.jycz.bean.IndentNumBean
import cn.ryanliu.jycz.bean.List1
import cn.ryanliu.jycz.bean.TMBQBean
import cn.ryanliu.jycz.interence.IOnXMListItemListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class IndentNumAdapter : BaseQuickAdapter<List1, BaseViewHolder>(R.layout.item_indentnum) {
    //全部
    private var mallIOnXMListItemListener: IOnXMListItemListener? = null

    //已读
    private var mreadIOnXMListItemListener: IOnXMListItemListener? = null

    //未读
    private var munreadIOnXMListItemListener: IOnXMListItemListener? = null

    //全部
    fun setOnAllDeviceProcureClickListener(l: IOnXMListItemListener?) {
        mallIOnXMListItemListener = l
    }

    //已读
    fun setOnReadDeviceProcureClickListener(l: IOnXMListItemListener?) {
        mreadIOnXMListItemListener = l
    }

    //未读
    fun setOnUnreadDeviceProcureClickListener(l: IOnXMListItemListener?) {
        munreadIOnXMListItemListener = l
    }

    override fun convert(holder: BaseViewHolder, item: List1) {
        holder.setText(R.id.wtdh_tv, item.py_order_code.toString())
        holder.setText(R.id.xs_tv, item.goods_num.toString())
        holder.setText(R.id.ddmdd_tv, item.rec_area.toString())
        holder.setText(R.id.ddzt_tv, item.order_state_scan)

        holder.setText(R.id.btn_read, "已扫(${item.yes_scan_num})")
        holder.setText(R.id.btn_unall, "未扫(${item.no_scan_num})")


        //点击全部
        holder.getView<Button>(R.id.btn_all).setOnClickListener {
            mallIOnXMListItemListener?.onXMListItemListener(item)
        }

        //点击已读
        holder.getView<Button>(R.id.btn_read).setOnClickListener {
            mreadIOnXMListItemListener?.onXMListItemListener(item)
        }

        //点击未读
        holder.getView<Button>(R.id.btn_unall).setOnClickListener {
            munreadIOnXMListItemListener?.onXMListItemListener(item)

        }
    }
}