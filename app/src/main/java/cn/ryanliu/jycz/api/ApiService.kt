package cn.ryanliu.jycz.api

import com.zwl.common.network.RetrofitManager

// 1.定义请求接口
interface ApiService {


    companion object {
        // 4.通过动态代理获取到所定义的接口
        val apiService = RetrofitManager.retrofit.create(ApiService::class.java)
    }
}