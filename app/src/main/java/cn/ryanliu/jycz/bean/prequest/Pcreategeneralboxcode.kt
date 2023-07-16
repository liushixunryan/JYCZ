package cn.ryanliu.jycz.bean.prequest

/**
 * @Author: lsx
 * @Date: 2023/7/15
 * @Description:
 */
data class Pcreategeneralboxcode(
    var boxcode_list: MutableList<Boxcode>
)

data class Boxcode(
    val boxcode: String
)