package cn.ryanliu.jycz.adapter

import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.AreajustListBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class AreaAdjustListAdapter :
    BaseQuickAdapter<AreajustListBean, BaseViewHolder>(R.layout.detail_item_areajust) {
    override fun convert(holder: BaseViewHolder, item: AreajustListBean) {
        holder.setText(R.id.xm_tv, item.box_code)
        holder.setText(R.id.tzsj_tv, item.create_time)
        holder.setText(R.id.tzr_tv, item.create_user)
        holder.setText(R.id.mdd_tv, item.rec_area)
        holder.setText(R.id.ykq_tv, item.old_ware_area_name)
        holder.setText(R.id.xkq_tv, item.new_ware_area_name)

    }
}