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

    //库存盘点，冻结所有库存 和 扫描操作
    @POST("/api/APP/lock_all_cancel")
    suspend fun lockAllCancel(@Body request: PlockAllCancel): BaseResponse<lockAllCancel?>

    //确认盘点
    @POST("/api/APP/get_invent_result")
    suspend fun getInventResult(@Body request: PInventResult): BaseResponse<InventResult?>

    //保存盘点
    @POST("/api/APP/save_invent")
    suspend fun saveInvent(@Body request: PInventResult): BaseResponse<Any?>

    //----------------------------明细----------------------------------
    //卸车明细 / 装车明细 获取当日和昨日统计数据
    @POST("/api/APP/search_scan_count")
    suspend fun searchScanCount(@Body request: PsearchScanCount): BaseResponse<searchScanCount>

    //卸车明细 / 装车明细 根据输入的查询条件查询装卸车任务单列表
    @POST("/api/APP/search_task_list")
    suspend fun searchTaskList(@Body request: PsearchTaskList): BaseResponse<MutableList<LoadingListBean>>

    //卸车明细 / 装车明细 获取每个任务单的扫描箱码明细数据
    @POST("/api/APP/search_task_details")
    suspend fun searchTaskDetails(@Body request: PSubmitSaveIn): BaseResponse<MutableList<SeeXMDetailBean>>

    //订单查询，界面加载汇总数据
    @POST("/api/APP/search_order_count")
    suspend fun searchOrderCount(@Body request: PsearchScanCount): BaseResponse<SearchOrderCount>


    @POST("/api/APP/search_order_details")
    suspend fun searchOrderDetails(@Body request: PsearchOrderDetails): BaseResponse<MutableList<OrderListBean>>


    @POST("/api/APP/search_order_boxcode_list")
    suspend fun searchOrderBoxcodeList(@Body request: PsearchOrderBoxcodeList): BaseResponse<MutableList<searchOrderBoxcodeList>>

    //库区调整明细
    @POST("/api/APP/search_ware_area_change_list")
    suspend fun searchWareAreaChangeList(@Body request: PsearchWareAreaChangeList): BaseResponse<MutableList<AreajustListBean>>

    //盘点明细
    @POST("/api/APP/search_invent_list")
    suspend fun searchInventList(@Body request: PsearchInventList): BaseResponse<MutableList<InventoryListBean>>

    @POST("/api/APP/search_invent_boxcode_list")
    suspend fun searchInventBoxcodeList(@Body request: PsearchInventBoxcodeList): BaseResponse<MutableList<XMListBean>>

    //取消盘点
    @POST("/api/APP/cancel_invent")
    suspend fun cancel_inventory(@Body request: Pcancel_inventory): BaseResponse<Any>

    //-----------------------------打印条码-----------------------------------
    //PgetOilFactory
    //获取任务
    @POST("/api/APP/get_project_list")
    suspend fun getProjectList(@Body request: PgetOilFactory): BaseResponse<MutableList<getProjectList>?>

    //单独打印一张托码（针对不可拆托的货物，只要一张托码即可，整托没有具体箱码
    @POST("/api/APP/create_t_code_1")
    suspend fun createTCode1(@Body request: PcreateTboxCode1): BaseResponse<String?>

    //扫描整托原有的箱码，补打一张托码（扫描原来的整托上的所有箱码，生成并打印一个托码，系统形成 托码-箱码 对应关系）（针对整托货有箱码，无托码的情况)
    @POST("/api/APP/create_t_code_2")
    suspend fun createTCode2(@Body request: MutableList<PcreateTCode2>): BaseResponse<String?>

    //针对货上没有箱码的，单独生成补打一个箱码（有可能是一件货没有箱码，如果是一托货上个别没有箱码，需要先扫描托码，再生成并打印指定数量的箱码，托码与新的箱码形成对应关系)
    @POST("/api/APP/create_box_code_1")
    suspend fun createBoxCode1(@Body request: PcreateBoxCode1): BaseResponse<createBoxCode1>

    //获取机油标签厂家信息
    @POST("/api/APP/get_oil_factory")
    suspend fun getOilFactory(@Body request: PgetOilFactory): BaseResponse<MutableList<getOilFactory>>

    //根据型号关键字查询机油标签设置信息
    @POST("/api/APP/search_oil_model")
    suspend fun searchOilModel(@Body request: PsearchOilModel): BaseResponse<MutableList<searchOilModel>>

    //生成箱码标签
    @POST("/api/APP/create_general_boxcode")
    suspend fun create_general_boxcode(@Body request: Pcreategeneralboxcode): BaseResponse<MutableList<creategeneralboxcode>>

    //生成托码标签
    @POST("/api/APP/create_general_tpcode")
    suspend fun create_general_tpcode(@Body request: Pcreategeneraltpcode): BaseResponse<creategeneraltpcode>

    //生成机油标签
    @POST("/api/APP/create_oil_label")
    suspend fun create_oil_label(@Body request: Pcreateoillabel): BaseResponse<createoillabel>


    //生成机油标签
    @GET("/appver.xml")
    suspend fun updateApp(): BaseResponse<String>

    //验证车牌号是否正确
    @POST("/api/APP/IsVehicleCarNumber")
    suspend fun IsVehicleCarNumber(@Body request: PIsVehicleCarNumber): BaseResponse<Any>

    //根据条码关键字后六位进行条码模糊查询
    @POST("/api/APP/search_barcode")
    suspend fun SearchBarcode(@Body request: PSearchbarcode): BaseResponse<searchbarcode>

    //补打条码
    @POST("/api/APP/rebar_code")
    suspend fun RebarCode(@Body request: PSearchbarcode): BaseResponse<createoillabel>

    companion object {
        // 4.通过动态代理获取到所定义的接口
        val apiService = RetrofitManager.retrofit.create(ApiService::class.java)
    }
}