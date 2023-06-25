package cn.ryanliu.jycz.bean.prequest

/**
 * @Author: lsx
 * @Date: 2023/6/25
 * @Description:
 */
data class PsearchInventList(
    val invent_time1: String,
    val invent_time2: String,
    val invent_user_name: String,
    val scan_code: String
) : java.io.Serializable