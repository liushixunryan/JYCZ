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
        holder.setText(R.id.ddzt_tv, item.indentnum)

    }
}