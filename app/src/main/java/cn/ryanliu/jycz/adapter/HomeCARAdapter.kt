package cn.ryanliu.jycz.adapter

import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.HomeCARBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class HomeCARAdapter : BaseQuickAdapter<HomeCARBean, BaseViewHolder>(R.layout.item_home) {
    override fun convert(holder: BaseViewHolder, item: HomeCARBean) {
        holder.setImageResource(R.id.iv_icon1, item.icon)
        holder.setText(R.id.tv_name1, item.title)

    }
}