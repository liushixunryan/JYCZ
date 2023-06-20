package cn.ryanliu.jycz.bean.prequest

import cn.ryanliu.jycz.common.constant.Constant
import cn.ryanliu.jycz.util.MmkvHelper

/**
 * @Author: lsx
 * @Date: 2023/6/20
 * @Description:
 */
data class PconfirmTask(
    val hand_task_id: Int,
    val oper_flag: String,
    val scan_hand_img1: String?,
    val scan_hand_img2: String?,
    val scan_hand_img3: String?,
    val scan_hand_img4: String?,
    val scan_hand_img5: String?,
    val scan_hand_img6: String?,
    val scan_hand_user_id: String = MmkvHelper.getInstance().getString(Constant.MmKv_KEY.USER_ID)
)