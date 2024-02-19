package cn.ryanliu.jycz.common.constant


object Config {
    @JvmField
    var sPlatformServerIP = "118.186.19.35"  //鹏远服务器  118.186.19.35

    @JvmField
    var sPlatformServerPort = "33254"   //测试 33254   正式 33251

    @JvmField
    var sBaseUrl: String = "http://$sPlatformServerIP:$sPlatformServerPort"//服务器
}