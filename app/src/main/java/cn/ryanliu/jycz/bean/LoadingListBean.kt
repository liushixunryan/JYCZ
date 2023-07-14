package cn.ryanliu.jycz.bean

import java.io.Serializable

data class LoadingListBean(
    val car_number: String,
    val driver_name: String,
    val hand_task_id: Int,
    val order_state_scan: String,
    val order_state_scan_hand_time: String,
    val order_state_scan_time: String,
    val scan_num: Int,
    val scan_user_name: String,
    var mx:String

) : Serializable
