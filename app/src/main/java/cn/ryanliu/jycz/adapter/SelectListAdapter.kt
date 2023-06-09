package cn.ryanliu.jycz.adapter

import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.SelectListBean
import cn.ryanliu.jycz.bean.XMListBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class SelectListAdapter : BaseQuickAdapter<SelectListBean, BaseViewHolder>(R.layout.item_xmlist) {


    override fun convert(holder: BaseViewHolder, item: SelectListBean) {
        holder.setText(R.id.tv_tmbq, item.bqname)

    }
}