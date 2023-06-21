package cn.ryanliu.jycz.bean.prequest

data class PscanFjCode(
    val box_code: String,
    val new_tp_code: String,
    val oper_type: Int,
    val tp_num: Int,
    val ware_area: String
)