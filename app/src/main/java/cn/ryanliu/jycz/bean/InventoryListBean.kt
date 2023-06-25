package cn.ryanliu.jycz.bean

import java.io.Serializable

data class InventoryListBean(
    val invent_id: Int,
    val invent_num: Int,
    val invent_time: String,
    val invent_user_name: String,
    val sys_ware_num: Int,
    val ware_area: String
) : Serializable
