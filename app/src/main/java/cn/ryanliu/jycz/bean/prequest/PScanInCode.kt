package cn.ryanliu.jycz.bean.prequest

/**
 * @Author: lsx
 * @Date: 2023/6/19
 * @Description:
 */
data class PScanInCode(
    val hand_task_id: Int,
    val oper_flag: String,
    val scan_code: String,

    val car_number: String,
    val reservation_type: String,
    val ware_area: String

)