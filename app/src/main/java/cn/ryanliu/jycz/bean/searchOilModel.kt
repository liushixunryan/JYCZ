package cn.ryanliu.jycz.bean

/**
 * @Author: lsx
 * @Date: 2023/7/3
 * @Description:
 */
data class searchOilModel(
    val bar_code: String,
    val brand_name: String,
    val create_time: String,
    val create_userid: Int,
    val dstate: Int,
    val iid: Int,
    val label_model: String,
    val `package`: String,
    val remark: String,
    val site_id: Int,
    val sku: String,
    val unit_id: Int,
    val update_time: String,
    var isselect: Int? = 0,
)