package cn.ryanliu.jycz.bean

import java.io.Serializable

data class SelectCarBean(
    val car_model: String,
    val car_number: String,
    val driver_name: String,
    val goods_info: String,
    val goods_num: Int,
    val in_report_type: String,
    val insite_time: String,
    val maybe_arrive_time: String,
    val order_time: String,
    val phone: String,
    val reservation_type: String,
    val site_name: String,
    val work_state: String,
    var isselect: Int? = 0,
) : Serializable
