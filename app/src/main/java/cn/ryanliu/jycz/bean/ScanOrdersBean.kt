package cn.ryanliu.jycz.bean

/**
 * @Author: lsx
 * @Date: 2023/6/19
 * @Description:
 */
data class ScanOrdersBean(
    val car_number: String,
    val goods_num: Int,
    val hand_task_id: Int,
    val no_scan_num: Int,
    val order_num: Int,
    val reservation_num: Int,
    val ware_area: Int,
    val ware_area_name: String,
    val yes_scan_num: Int
)