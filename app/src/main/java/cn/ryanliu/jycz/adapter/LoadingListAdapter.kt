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
        holder.setText(R.id.ddzt_tv, item.indentnum)

        holder.getView<Button>(R.id.btn_all).setOnClickListener {
            SeeXMDetalActivity.launch(context)
        }

    }
}