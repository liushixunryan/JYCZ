package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

/**
 * @Author: lsx
 * @Date: 2023/7/3
 * @Description:
 */
data class PcreateBoxCode1(
    val box_code_num: Int,
    val tp_code: String,
    val project_id: String
)