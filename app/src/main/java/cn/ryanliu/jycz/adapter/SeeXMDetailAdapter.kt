package cn.ryanliu.jycz.adapter

import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.LoadingListBean
import cn.ryanliu.jycz.bean.SeeXMDetailBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class SeeXMDetailAdapter :
    BaseQuickAdapter<SeeXMDetailBean, BaseViewHolder>(R.layout.detail_item_seexmdetail) {


    override fun convert(holder: BaseViewHolder, item: SeeXMDetailBean) {
        holder.setText(R.id.xm_tv, item.indentnum)

    }
}