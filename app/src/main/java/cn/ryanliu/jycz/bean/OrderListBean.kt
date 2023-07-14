package cn.ryanliu.jycz.bean

import java.io.Serializable

data class OrderListBean(
    val car_number: String,
    val driver_name: String,
    val order_id: Int,
    val order_num: Int,
    val order_state_scan: String,
    val order_state_scan_hand_time: String,
    val order_state_scan_time: String,
    val order_time: String,
    val py_order_code: String,
    val rec_area: String,
    val rec_man: String,
    val scan_hand_user_name: String,
    val scan_num: Int,
    val task_type: String,
    val ware_area: String
) : Serializable
