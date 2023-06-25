package cn.ryanliu.jycz.bean

/**
 * @Author: lsx
 * @Date: 2023/6/25
 * @Description:
 */
data class SearchOrderCount(
    val today_in_scan_num: Int,
    val today_out_scan_num: Int,
    val yesterday_in_num: Int,
    val yesterday_out_num: Int
)