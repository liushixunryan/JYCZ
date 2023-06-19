package cn.ryanliu.jycz.api

import cn.ryanliu.jycz.basic.BaseResponse
import cn.ryanliu.jycz.bean.*
import cn.ryanliu.jycz.bean.prequest.*
import com.zwl.common.network.RetrofitManager
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// 1.定义请求接口
interface ApiService {
    @Headers("isToken: false")
    @POST("/api/APP/pda_login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    //扫扫码卸/装车 选择车辆信息 查询 已入场待卸车
    @POST("/api/APP/get_car_info_1")
    suspend fun getCarInfoIn1(@Body request: PCarInfoIn1): BaseResponse<MutableList<SelectCarBean>>

    // 扫码卸/装车 根据车牌号 查询
    @POST("/api/APP/get_car_info_2")
    suspend fun getCarInfoIn2(@Body request: PCarInfoIn1): BaseResponse<MutableList<SelectCarBean>>

    // 扫码卸/装车 根据车牌号 查询
    @POST("/api/APP/get_ware_area")
    suspend fun getWareArea(@Body request: PWareArea): BaseResponse<MutableList<SelectAreaBean>>

    //扫码卸/装车 D界面 进入时默认查询未完成的 卸车任务
    @POST("/api/APP/get_scan_orders")
    suspend fun getScanOrders(@Body request: PScanOrders): BaseResponse<ScanOrdersBean>

    //扫码卸/装车 D界面 扫描调用
    @POST("/api/APP/scan_code")
    suspend fun getScanInCode(@Body request: PScanInCode): BaseResponse<ScanInCodeBean>

    //确认卸/装车完成
    @POST("/api/APP/submit_save")
    suspend fun submitSaveIn(@Body request: PSubmitSaveIn): BaseResponse<Any?>

    companion object {
        // 4.通过动态代理获取到所定义的接口
        val apiService = RetrofitManager.retrofit.create(ApiService::class.java)
    }
}