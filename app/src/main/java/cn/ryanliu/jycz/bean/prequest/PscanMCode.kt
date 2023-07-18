package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

data class PscanMCode(
    var oper_flag: String?,
    var scan_code: String?,
    var invent_id:String? = "",
    val ware_area: Int? = null
)