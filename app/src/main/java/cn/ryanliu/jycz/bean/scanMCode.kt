package cn.ryanliu.jycz.bean

data class scanMCode(
    val py_order_code: String,
    val rec_area: String,
    val rec_man: String,
    val rec_unit: String,
    val scan_code: String,
    val scan_type: String,
    val sort_list_id: Int,
    val tp_num: Int,
    val ware_area: Int,
    val voice_flag: Int,
    val order_id: Int,
    val ware_area_name: String
)