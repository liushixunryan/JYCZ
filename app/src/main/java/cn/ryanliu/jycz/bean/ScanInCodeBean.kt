package cn.ryanliu.jycz.bean

/**
 * @Author: lsx
 * @Date: 2023/6/19
 * @Description:
 */
data class ScanInCodeBean(

    val hand_task_id: Int,
    val no_scan_num: Int,
    val order_id: Int,
    val py_order_code: String,
    val rec_area: String,
    val scan_code: String,
    val scan_tips: String,
    val scan_type: String,
    val tp_num: Int,
    val yes_scan_num: Int,
    val voice_flag: Int
)