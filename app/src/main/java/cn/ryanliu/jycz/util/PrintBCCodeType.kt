package cn.ryanliu.jycz.util

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import cn.ryanliu.jycz.util.ResUtils.getResources
import print.Print
import print.Print.PrintBitmap


/**
 * @Author: lsx
 * @Date: 2023/6/14
 * @Description:
 */
object PrintBCCodeType {
    //仅打印一个【托码】标签/扫箱码后补打【托码】标签
    @Throws(java.lang.Exception::class)
    fun PrintTM(detail: String): Int {
        //data:image/png;base64,
        return PrintBitmap(getBase64("$detail"), 0, 50)
    }

    //补打箱码
    @Throws(java.lang.Exception::class)
    fun PrintXM(code: String): Int {
        return PrintBitmap(getBase64("$code"), 0, 50)
    }

    // 机油标签规格查询
    @Throws(java.lang.Exception::class)
    fun PrintJYBQ(code: String): Int {


        return PrintBitmap(getBase64("$code"), 0, 50)
    }

    fun stringToUnicode(string: String): String? {
        val unicode = StringBuffer()
        for (i in 0 until string.length) {
            // 取出每一个字符
            val c = string[i]
            // 转换为unicode
            //"\\u只是代号，请根据具体所需添加相应的符号"
            unicode.append("\\u" + Integer.toHexString(c.code))
        }
        Log.d("sansuiban", "stringToUnicode: $unicode")
        return unicode.toString()
    }

    //文件转bitmap
    fun openImage(path: String): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }

    //资源文件转bitmap
    fun getResoures(path: Int): Bitmap? {
        return BitmapFactory.decodeResource(getResources(), path)
    }

    //base64转bitmap
    fun getBase64(base64: String): Bitmap? {
        val decode = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decode, 0, decode.size);

    }


}