package cn.ryanliu.jycz.bean

import java.io.Serializable

data class SelectAreaBean(
    val areaname: String?,
    val isuse: Int? = 0,
    var isselect: Int? = 0,
) : Serializable
