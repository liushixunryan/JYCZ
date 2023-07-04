package cn.ryanliu.jycz.bean

/**
 * @Author: lsx
 * @Date: 2023/7/3
 * @Description:
 */
data class createBoxCode1(
    val box_code_list: List<BoxCode>,
    val tp_code: String
)

data class BoxCode(
    val box_code: String
)