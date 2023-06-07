package com.zwl.common.constant


object Config {
    @JvmField
    var sPlatformServerIP = "47.97.245.87"//服务器

    @JvmField
    var sPlatformServerPort = "33251"

    @JvmField
    var sBaseUrl: String = "http://${sPlatformServerIP}:${sPlatformServerPort}"//服务器


}