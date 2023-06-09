package cn.ryanliu.jycz.adapter

import android.graphics.Color
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.XMListBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class XMListAdapter : BaseQuickAdapter<XMListBean, BaseViewHolder>(R.layout.item_xmlist) {


    override fun convert(holder: BaseViewHolder, item: XMListBean) {
        holder.setText(R.id.tv_tmbq, item.scan_code)
        if (item.color_flag == 0) {
            holder.setTextColor(R.id.tv_tmbq, Color.parseColor("#333333"))
        } else {
            holder.setTextColor(R.id.tv_tmbq,Color.parseColor("#FF0000"))
        }

    }
}