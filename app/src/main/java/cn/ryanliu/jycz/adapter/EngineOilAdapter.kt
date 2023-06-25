package cn.ryanliu.jycz.adapter

import android.graphics.Color
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.EngineOilBean
import cn.ryanliu.jycz.bean.HomePDABean
import cn.ryanliu.jycz.bean.TMBQBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class EngineOilAdapter : BaseQuickAdapter<EngineOilBean, BaseViewHolder>(R.layout.item_engineoil) {
    //选择的位置(-1则代表默认没有选中)
    private var selPosition = -1
    override fun convert(holder: BaseViewHolder, item: EngineOilBean) {
        holder.setText(R.id.txm_tv, "05100-00451NB")
        holder.setText(R.id.xh_tv, "5W-30")
        holder.setText(R.id.mc_tv, "SM机油")
        holder.setText(R.id.bz_tv, "28箱/托，12桶/箱")
        holder.setText(R.id.beiz_tv, "成托装货，成托发运")
        holder.setText(R.id.typ_tv, "SKU")
        holder.setText(R.id.dw_tv, "箱")

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