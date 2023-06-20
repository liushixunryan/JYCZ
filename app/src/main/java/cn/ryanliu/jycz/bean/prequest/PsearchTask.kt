package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

/**
 * @Author: lsx
 * @Date: 2023/6/20
 * @Description:
 */
data class PsearchTask(
    val car_number: String? = null,
    val oper_flag: String? = null,
    val scan_user_name: String? = null,
)