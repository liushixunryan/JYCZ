package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

/**
 * @Author: lsx
 * @Date: 2023/6/19
 * @Description:
 */
data class PScanOrders(
    val car_number: String,
    val oper_flag: String,
    val reservation_type: String,
    val task_type: String,

    val scan_user_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.USER_ID),
    val site_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.SITE_ID)
)