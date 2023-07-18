package cn.ryanliu.jycz.bean

import java.io.Serializable

data class AreajustListBean(
    val box_code: String,
    val create_time: String,
    val create_user: String,
    val new_ware_area_name: String,
    val old_ware_area_name: String,
    val rec_area: String
) : Serializable
