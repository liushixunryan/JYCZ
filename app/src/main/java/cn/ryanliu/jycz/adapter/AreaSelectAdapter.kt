package cn.ryanliu.jycz.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.bean.AreaSelectBean
import cn.ryanliu.jycz.bean.SelectAreaBean
import cn.ryanliu.jycz.bean.SelectCarBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class AreaSelectAdapter :
    BaseQuickAdapter<SelectAreaBean, BaseViewHolder>(R.layout.item_selectcar) {
    //选择的位置(-1则代表默认没有选中)
    private var selPosition = -1


    @SuppressLint("NotifyDataSetChanged")
    override fun convert(holder: BaseViewHolder, item: SelectAreaBean) {
        holder.setText(R.id.tv_carplant, item.ware_area_name)
        if (item.isuse == 1) {
            holder.setTextColor(R.id.tv_carplant, Color.RED)
        }

//        if (selPosition != holder.bindingAdapterPosition) {
//            item.isselect = -1
//        } else {
//            item.isselect = 1
//        }

        if (item.isselect != 1) {
            holder.getView<TextView>(R.id.tv_carplant)
                .setBackgroundResource(R.drawable.bg_select_tv)
        } else {
            holder.getView<TextView>(R.id.tv_carplant)
                .setBackgroundResource(R.drawable.bg_selecttrue_tv)
        }


        holder.getView<TextView>(R.id.tv_carplant).setOnClickListener {
            if (item.isselect != 1) {
                item.isselect = 1
            } else {
                Log.e("sansuiban", "点击:$holder.bindingAdapterPosition ")
                selPosition = -1
                item.isselect = -1
            }
            notifyDataSetChanged()
        }
    }
}