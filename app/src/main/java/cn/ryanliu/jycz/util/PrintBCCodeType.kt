package cn.ryanliu.jycz.util

import print.Print

/**
 * @Author: lsx
 * @Date: 2023/6/14
 * @Description:
 */
object PrintBCCodeType {

    //仅打印一个【托码】标签/扫箱码后补打【托码】标签
    @Throws(java.lang.Exception::class)
    fun PrintTM(qtext: String, code: String): Int {
        Print.PrintText(
            "\n\n${qtext}\n", 2, 0, 0
        )
        return Print.PrintBarCode(
            Print.BC_CODE128,
            "${code}", 2, 10, 2, 1
        )
    }

    //补打箱码
    @Throws(java.lang.Exception::class)
    fun PrintXM(code: String): Int {
        return Print.PrintBarCode(
            Print.BC_CODE128,
            "BOXLBJ01202304110000001", 2, 10, 2, 1
        )
    }

    // 机油标签规格查询
    @Throws(java.lang.Exception::class)
    fun PrintJYBQ(Q: String, code: String, xh: String, mc: String): Int {
        Print.PrintText(
            "\n\nQ: $Q\n", 2, 0, 0
        )

        Print.PrintBarCode(
            Print.BC_CODE128,
            "$code", 2, 10, 2, 1
        )
        Print.PrintText(
            "\n型号: $xh\n", 0, 0, 0
        )

        return Print.PrintText(
            "\n名称: $mc\n", 0, 0, 0
        )
    }

    @Throws(java.lang.Exception::class)
    fun Printcs(): Int {

        return Print.PrintBarCode(
            Print.BC_CODE128,
            "{BS/N:{C\u000c\u0022\u0038\u004e{A3", 2, 10, 2, 1
        )
    }


}