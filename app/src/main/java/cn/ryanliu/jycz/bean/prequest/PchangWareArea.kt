package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

data class PchangWareArea(
    val oper_flag: String,
    val scan_code: String,
    val ware_area: String,
    val site_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.SITE_ID)
)