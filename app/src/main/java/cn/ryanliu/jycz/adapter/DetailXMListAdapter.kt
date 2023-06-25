package cn.ryanliu.jycz.adapter

import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.searchOrderBoxcodeList
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class DetailXMListAdapter : BaseQuickAdapter<searchOrderBoxcodeList, BaseViewHolder>(R.layout.item_xmlist) {


    override fun convert(holder: BaseViewHolder, item: searchOrderBoxcodeList) {
        holder.setText(R.id.tv_tmbq, item.box_code)

    }
}