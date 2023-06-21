package cn.ryanliu.jycz.adapter

import android.util.Log
import android.widget.LinearLayout
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.EntryHandoverBean
import cn.ryanliu.jycz.bean.ExitHandoverBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class ExitHandoverAdapter :
    BaseQuickAdapter<ExitHandoverBean, BaseViewHolder>(R.layout.item_exithandover) {
    //选择的位置(-1则代表默认没有选中)
    private var selPosition = -1

    override fun convert(holder: BaseViewHolder, item: ExitHandoverBean) {
        holder.setText(R.id.xcsmxs_tv, item.indentnum)

        if (selPosition != holder.adapterPosition) {
            item.isselect = -1
        } else {
            item.isselect = 1
        }

        if (item.isselect != 1) {
            holder.getView<LinearLayout>(R.id.vg_ticket1)
                .setBackgroundResource(R.drawable.bg_select_tv)
        } else {
            holder.getView<LinearLayout>(R.id.vg_ticket1)
                .setBackgroundResource(R.drawable.bg_selecttrue_tv)
        }

        holder.getView<LinearLayout>(R.id.vg_ticket1).setOnClickListener {
            if (item.isselect != 1) {
                item.isselect = 1
                selPosition = holder.adapterPosition
            } else {
                Log.e("sansuiban", "点击:$holder.bindingAdapterPosition ")
                selPosition = -1
                item.isselect = -1
            }
            notifyDataSetChanged()
        }
    }
}