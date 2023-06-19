package cn.ryanliu.jycz.util

import cn.ryanliu.jycz.common.constant.Constant
import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/14
 * @Description:
 */
object PrintBCCodeType {

    //打印托码
    @Throws(java.lang.Exception::class)
    fun PrintTM(): Int {
        Print.PrintText(
            "\n\nQ: 2787898970\n", 2, 0, 0
        )
        return Print.PrintBarCode(
            Print.BC_CODEBAR,
            "A40156B", 0, 0, 2, 1
        )
    }

    //补打箱码
    @Throws(java.lang.Exception::class)
    fun PrintXM(): Int {
        return Print.PrintBarCode(
            Print.BC_CODE128,
            "BOXLBJ01202304110000001", 2, 10, 2, 1
        )
    }
}