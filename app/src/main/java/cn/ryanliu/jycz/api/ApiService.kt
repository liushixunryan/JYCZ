package cn.ryanliu.jycz.api

import cn.ryanliu.jycz.basic.BaseResponse
import cn.ryanliu.jycz.bean.LoginResponse
import cn.ryanliu.jycz.bean.prequest.LoginRequest
import com.zwl.common.network.RetrofitManager
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// 1.定义请求接口
interface ApiService {
    @Headers("isToken: false")
    @POST("/api/Pub_User//pda_login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    companion object {
        // 4.通过动态代理获取到所定义的接口
        val apiService = RetrofitManager.retrofit.create(ApiService::class.java)
    }
}