package com.zwl.common.network

import android.util.Log
import cn.ryanliu.jycz.basic.BaseApplication
import com.zwl.common.constant.Config.sBaseUrl
import com.zwl.common.constant.Config.sPlatformServerIP
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {
    // 2.构建 OkHttpClient 对象
    @JvmField
    val imgBaseUrl: String = "$sBaseUrl/v1/file/getImages/"//服务器
    val apkBaseUrl: String = "http://${sPlatformServerIP}:9999/file/"
    val fileBaseUrl: String = "$sBaseUrl/v1/file/getZipFile/"//doc
    val filemd5BaseUrl: String = "$sBaseUrl/v1/file/getZipFileByMd5List/"//doc
    val filemagagerfilePath: String = "sdcard/download/"//在线文档管理下载路径

    // 设置拦截器
    val httpLoggingInterceptor = HttpLoggingInterceptor() {
//        LogUtil.e("okhttp.OkHttpClient",it)
        Log.e("RetrofitManager", it )
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val cacheDir = BaseApplication.sInstance.externalCacheDir ?: BaseApplication.sInstance.cacheDir
    val okHttpClient = OkHttpClient().newBuilder()
        // 设置连接超时为 10 秒
        .connectTimeout(10L, TimeUnit.SECONDS)
        // 设置文件读取超时为 60 秒
        .readTimeout(60L, TimeUnit.SECONDS)
        // 设置用于读取和写入缓存响应的响应缓存为 10M
        .cache(Cache(cacheDir, 10240 * 1024))
        // 设置 http 日志拦截器
        // 使用 addInterceptor() 也可以，即为第一层自定义拦截器
//        .addInterceptor(Interceptor { chain -> /*获取token */
//            val token = UserUtil.getUserToken()
//            if ("false" == chain.request().header("isToken")) {
//                val request = chain.request().newBuilder().removeHeader("Authorization")
//                    .removeHeader("isToken").build()
//                chain.proceed(request)
//            } else {
//                val request =
//                    chain.request().newBuilder().addHeader("Authorization", ("Bearer $token") ?: "")
//                        .addHeader("Online-Uuid", SystemUtil.getGuid() ?: "")
//                        .removeHeader("isToken").build()
//                chain.proceed(request)
//            }
//        })
        // 使用 addNetworkInterceptor() 也可，即为第六层非网页网络拦截拦截器
        .addInterceptor(httpLoggingInterceptor)
//        .addInterceptor(TokenInterceptor()) //添加判断token是否过期的拦截器
        .build()

    // 3.构建 Retrofit 对象
    val retrofit: Retrofit = Retrofit.Builder().baseUrl(sBaseUrl).client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())// 基本属性转换器
        .addConverterFactory(GsonConverterFactory.create())// Gson 数据转换器
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())// RxJava 适配器
        .build()
}