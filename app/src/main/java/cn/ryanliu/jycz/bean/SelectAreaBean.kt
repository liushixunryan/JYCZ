package cn.ryanliu.jycz.bean

import java.io.Serializable

data class SelectAreaBean(
    val ware_area_id: Int,
    val ware_area_name: String,
    val isuse: Int? = 0,
    var isselect: Int? = 0,
) : Serializable
