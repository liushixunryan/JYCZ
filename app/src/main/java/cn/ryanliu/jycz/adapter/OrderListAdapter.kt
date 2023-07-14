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
        if (item.task_type == "卸车任务单") {
            holder.setText(R.id.tv_xcwcsj, "卸车完成时间")
            holder.setText(R.id.tv_rcjjsj, "入场交接时间")
            holder.setText(R.id.tv_xcsmxs, "卸车扫描箱数")
        } else {
            holder.setText(R.id.tv_xcwcsj, "装车完成时间")
            holder.setText(R.id.tv_rcjjsj, "出场交接时间")
            holder.setText(R.id.tv_xcsmxs, "装车扫描箱数")
        }



        holder.setText(R.id.wtdh_tv, item.py_order_code)
        holder.setText(R.id.rwd_tv, item.task_type)
        holder.setText(R.id.ddzt_tv, item.order_state_scan)
        holder.setText(R.id.ddzxs_tv, item.order_num)
        holder.setText(R.id.ddsj_tv, item.order_time)
        holder.setText(R.id.mdd_tv, item.rec_area)
        holder.setText(R.id.shr_tv, item.rec_man)

        holder.setText(R.id.xcwcsj_tv, item.order_state_scan_time)
        holder.setText(R.id.xcsmxs_tv, item.scan_num)
        holder.setText(R.id.rcjjsj_tv, item.order_state_scan_hand_time)
        holder.setText(R.id.cph_tv, item.car_number)
        holder.setText(R.id.ddzxs_tv, item.order_num)
        holder.setText(R.id.smr_tv, item.scan_hand_user_name)
        holder.setText(R.id.kq_tv, item.ware_area)


        holder.getView<Button>(R.id.btn_all).setOnClickListener {
            XMListActivity.launch(context,item.order_id.toInt())
        }

    }
}