package cn.ryanliu.jycz.bean

/**
 * @Author: lsx
 * @Date: 2023/6/23
 * @Description:
 */
data class InventResult(
    val invent_num: Int,
    val scan_code_list: MutableList<XMListBean>,
    val sys_ware_num: Int,
    val ware_area: String
):java.io.Serializable
