package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

/**
 * @Author: lsx
 * @Date: 2023/6/19
 * @Description:
 */
data class PCarInfoIn1(
    val car_number: String? = null,
    val oper_flag: String? = null,
    val site_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.SITE_ID)
)