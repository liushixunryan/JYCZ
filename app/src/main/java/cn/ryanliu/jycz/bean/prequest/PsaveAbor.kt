package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

/**
 * @Author: lsx
 * @Date: 2023/6/20
 * @Description:
 */
data class PsaveAbor(
    val bar_code: String,
    val abor_txt: String,
    val abor_img1: String?,
    val abor_img2: String?,
    val abor_img3: String?,
    val abor_img4: String?,
    val scan_hand_user_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.USER_ID)
)