package cn.ryanliu.jycz.common.constant


object Config {
    @JvmField
    var sPlatformServerIP = "jms.bcsyt.cn"//服务器

    @JvmField
    var sPlatformServerPort = "33251"

    @JvmField
    var sBaseUrl: String = "https://$sPlatformServerIP:$sPlatformServerPort"//服务器


}