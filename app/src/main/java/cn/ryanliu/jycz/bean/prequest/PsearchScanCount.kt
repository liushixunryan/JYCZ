package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

data class PsearchScanCount(
    val oper_flag: String,
    val site_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.SITE_ID)
)