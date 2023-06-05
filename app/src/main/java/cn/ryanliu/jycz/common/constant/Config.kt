package com.zwl.common.constant



object Config {

    @JvmField
    var sPlatformServerIP = "39.106.138.228"//服务器

    @JvmField
    var sPlatformServerPort = "8080"

    @JvmField
    var sBaseUrl: String = "http://${sPlatformServerIP}:${sPlatformServerPort}"//服务器


}