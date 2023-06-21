package cn.ryanliu.jycz.api

import cn.ryanliu.jycz.basic.BaseResponse
import cn.ryanliu.jycz.bean.*
import cn.ryanliu.jycz.bean.prequest.*
import com.zwl.common.network.RetrofitManager
import okhttp3.MultipartBody
import retrofit2.http.*

// 1.定义请求接口
interface ApiService {
    @Multipart
    @POST("/api/APP/upload_photo")
    suspend fun uploadList(
        @Part parts: List<MultipartBody.Part>
    ): BaseResponse<Map<String, String>?>

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

    //订单数量 按钮 点击时查看 订单扫描情况
    @POST("/api/APP/search_car_orders")
    suspend fun searchCarOrders(@Body request: PCarOrders): BaseResponse<IndentNumBean>

    //通过委托单号查询对应的箱码明细/api/APP/get_boxcode_list
    @POST("/api/APP/get_boxcode_list")
    suspend fun getBoxcodeList(@Body request: PBoxcodeList): BaseResponse<MutableList<XMListBean>>

    //入场交接列表
    @POST("/api/APP/search_task")
    suspend fun searchTask(@Body request: PsearchTask): BaseResponse<MutableList<EntryHandoverBean>>

    //上传图片
    @Multipart
    @POST("/api/APP/upload_photo")
    suspend fun upload_photo(@Part parts: List<MultipartBody.Part>): BaseResponse<String?>

    //完成出场交接
    @POST("/api/APP/confirm_task")
    suspend fun confirmTask(@Body request: PconfirmTask): BaseResponse<Any?>

    //分拣码放 / 库位调整 / 库存盘点 扫描
    @POST("/api/APP/scan_m_code")
    suspend fun scanMCode(@Body request: PscanMCode): BaseResponse<scanMCode>

    //调整库区
    @POST("/api/APP/chang_ware_area")
    suspend fun changWareArea(@Body request: PchangWareArea): BaseResponse<Any?>

    //分拣码放 拆托
    @POST("/api/APP/do_split_tmp")
    suspend fun doSplitTmp(@Body request: PdoSplitTmp): BaseResponse<Any?>

    //拼托 扫描、
    @POST("/api/APP/scan_fj_code")
    suspend fun scanFjCode(@Body request: PscanFjCode): BaseResponse<scanFjCode>

    //分拣码放 拼托 创建一个托码
    @POST("/api/APP/create_tbox_code_1")
    suspend fun createTboxCode1(@Body request: PcreateTboxCode1): BaseResponse<String>



    companion object {
        // 4.通过动态代理获取到所定义的接口
        val apiService = RetrofitManager.retrofit.create(ApiService::class.java)
    }
}