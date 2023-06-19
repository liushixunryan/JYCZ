package cn.ryanliu.jycz.bean

/**
 * @Author: lsx
 * @Date: 2023/6/7
 * @Description:
 */
class LoginResponse(
    val login_acount: String,
    val phone: String,
    val site_code: String,
    val site_id: String,
    val site_name: String,
    val token: String,
    val unit_code: String,
    val unit_id: String,
    val unit_name: String,
    val user_id: String,
    val user_name: String,
    val userinfo: Userinfo
)

class Userinfo(
)
