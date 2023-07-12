package cn.ryanliu.jycz.bean

import java.io.Serializable

data class XMListBean(
    val color_flag: Int,
    val scan_code: String
//{"scan_code":"BOX-001-004","color_flag":0}
) : Serializable
