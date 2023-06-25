package cn.ryanliu.jycz.bean.prequest

/**
 * @Author: lsx
 * @Date: 2023/6/25
 * @Description:
 */
data class PsearchOrderDetails(
    val box_code: String,
    val car_number: String,
    val driver_name: String,
    val order_state_in_scan: String,
    val order_time1: String,
    val order_time2: String,
    val rec_area: String,
    val task_type: String,
    val tp_code: String
):java.io.Serializable