package cn.ryanliu.jycz.bean

import java.io.Serializable

data class HomeCARBean(
    val id:Int,
    val title: String,
    var icon: Int
) : Serializable
