package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

data class PlockAllCancel(
    val area_list: List<Area>,
    val lock_type: Int,
    val site_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.SITE_ID)
)

data class Area(
    val ware_area: String,
    val ware_area_id: Int
)