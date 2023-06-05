package cn.ryanliu.jycz.bean

import java.io.Serializable

data class HomePDABean(
    val id:Int,
    val title: String,
    var icon: Int
) : Serializable
