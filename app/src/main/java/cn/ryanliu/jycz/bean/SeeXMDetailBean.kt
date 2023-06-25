package cn.ryanliu.jycz.bean

import java.io.Serializable

data class SeeXMDetailBean(
    val rec_area: String,
    val scan_code: String,
    val scan_time: String
) : Serializable
