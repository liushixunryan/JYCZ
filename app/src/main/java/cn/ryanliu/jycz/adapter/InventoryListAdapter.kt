package cn.ryanliu.jycz.adapter

import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.AreajustListBean
import cn.ryanliu.jycz.bean.InventoryListBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class InventoryListAdapter :
    BaseQuickAdapter<InventoryListBean, BaseViewHolder>(R.layout.detail_item_inventory) {
    override fun convert(holder: BaseViewHolder, item: InventoryListBean) {
        holder.setText(R.id.pdsj_tv, item.indentnum)

    }
}