package cn.ryanliu.jycz.bean

import java.io.Serializable

data class IndentNumBean(
    val List1: MutableList<List1>,
    val goods_num: Int,
    val hand_task_id: Int,
    val no_scan_num: Int,
    val order_num: Int,
    val yes_scan_num: Int
) : Serializable

data class List1(
    val goods_num: Int,
    val no_scan_num: Int,
    val order_id: Int,
    val order_state_in_scan: String,
    val py_order_code: String,
    val rec_area: String,
    val yes_scan_num: Int
) : Serializable
