package cn.ryanliu.jycz.bean

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:
 */
class EntryHandoverBean(
    val car_number: String,
    val hand_task_id: Int,
    val order_state_scan_time: String,
    val scan_num: Int,
    val scan_user_name: String,
    var isselect: Int? = 0,
):java.io.Serializable