package cn.ryanliu.jycz.adapter

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
    override fun convert(holder: BaseViewHolder, item: EngineOilBean) {
        holder.setText(R.id.txm_tv, "05100-00451NB")
        holder.setText(R.id.xh_tv, "5W-30")
        holder.setText(R.id.mc_tv, "SM机油")
        holder.setText(R.id.bz_tv, "28箱/托，12桶/箱")
        holder.setText(R.id.beiz_tv, "成托装货，成托发运")
        holder.setText(R.id.typ_tv, "SKU")
        holder.setText(R.id.dw_tv, "箱")
    }
}