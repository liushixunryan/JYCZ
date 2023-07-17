package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

data class PscanMCode(
    var oper_flag: String?,
    var scan_code: String?,
    var invent_id:String? = "",
    var scan_user_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.USER_ID),
    var site_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.SITE_ID)
)