package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

/**
 * @Author: lsx
 * @Date: 2023/7/3
 * @Description:
 */
data class PgetOilFactory(
    val site_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.SITE_ID)
)