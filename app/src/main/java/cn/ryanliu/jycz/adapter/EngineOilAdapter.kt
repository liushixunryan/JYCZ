package cn.ryanliu.jycz.adapter

import android.widget.LinearLayout
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.searchOilModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class EngineOilAdapter : BaseQuickAdapter<searchOilModel, BaseViewHolder>(R.layout.item_engineoil) {
    //选择的位置(-1则代表默认没有选中)
    private var selPosition = -1
    override fun convert(holder: BaseViewHolder, item: searchOilModel) {
        holder.setText(R.id.txm_tv, item.bar_code)
        holder.setText(R.id.xh_tv, item.label_model)
        holder.setText(R.id.mc_tv, item.brand_name)
        holder.setText(R.id.bz_tv, item.`package`)
        holder.setText(R.id.beiz_tv, item.remark)
        holder.setText(R.id.typ_tv, "SKU")
        holder.setText(R.id.dw_tv, item.sku)

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
                selPosition = -1
                item.isselect = -1
            }
            notifyDataSetChanged()
        }
    }
}