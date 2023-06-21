package cn.ryanliu.jycz.bean.prequest

data class PsearchTaskList(
    val box_code: String,
    val car_number: String,
    val driver_name: String,
    val oper_flag: String,
    val order_state_in_scan: String,
    val order_state_scan_time1: String,
    val order_state_scan_time2: String,
    val rec_area: String,
    val scan_user_id: String,
    val tp_code: String
)