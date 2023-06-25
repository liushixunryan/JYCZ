package cn.ryanliu.jycz.adapter

import android.widget.Button
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.activity.detail.SeeXMDetalActivity
import cn.ryanliu.jycz.bean.LoadingListBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: lsx
 * @Date: 2023/6/4
 * @Description:
 */
class LoadingListAdapter :
    BaseQuickAdapter<LoadingListBean, BaseViewHolder>(R.layout.detail_item_loading) {

    override fun convert(holder: BaseViewHolder, item: LoadingListBean) {
        holder.setText(R.id.ddzt_tv, item.order_state_in_scan)
        holder.setText(R.id.smzxs_tv, item.scan_num)
        holder.setText(R.id.zcwcsj_tv, item.order_state_scan_time)
        holder.setText(R.id.ccjjsj_tv, item.order_state_scan_hand_time)
        holder.setText(R.id.cph_tv, item.car_number)
        holder.setText(R.id.sjxm_tv, item.driver_name)
        holder.setText(R.id.smr_tv, item.scan_user_name)

        holder.getView<Button>(R.id.btn_all).setOnClickListener {
            SeeXMDetalActivity.launch(context, item.hand_task_id.toString())
        }

    }
}