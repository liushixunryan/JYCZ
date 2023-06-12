package cn.ryanliu.jycz.adapter

import android.widget.Button
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.XMListActivity
import cn.ryanliu.jycz.activity.detail.SeeXMDetalActivity
import cn.ryanliu.jycz.bean.LoadingListBean
import cn.ryanliu.jycz.bean.OrderListBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class OrderListAdapter :
    BaseQuickAdapter<OrderListBean, BaseViewHolder>(R.layout.detail_item_order) {


    override fun convert(holder: BaseViewHolder, item: OrderListBean) {
        holder.setText(R.id.ddzt_tv, item.indentnum)

        holder.getView<Button>(R.id.btn_all).setOnClickListener {
            XMListActivity.launch(context)
        }

    }
}