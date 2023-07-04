package cn.ryanliu.jycz.adapter

import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.BoxCode
import cn.ryanliu.jycz.bean.HomePDABean
import cn.ryanliu.jycz.bean.TMBQBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class TMBQAdapter : BaseQuickAdapter<BoxCode, BaseViewHolder>(R.layout.item_tmbq) {
    override fun convert(holder: BaseViewHolder, item: BoxCode) {
        holder.setText(R.id.tv_tmbq, item.box_code)
    }
}