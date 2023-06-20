package cn.ryanliu.jycz.util

import cn.ryanliu.jycz.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


object UploadUtil {
    //单独
    suspend fun upload(file: File):String? {
        try {
            val body = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file);

            val multipartBody1 = MultipartBody.Builder()
                .addFormDataPart(file.name, file.name, body)
                .setType(MultipartBody.FORM)
                .build();
            val res = ApiService.apiService.upload_photo(multipartBody1.parts)
            return res.data
        } catch (e: Exception) {
            e.printStackTrace()
            ToastUtilsExt.info("上传出错")
        }
        return null
    }


    //批量
    suspend fun fileuploadList(files: Map<String,File>?): Map<String, String>? {
        if (files.isNullOrEmpty())
            return null
        try {
            val builder = MultipartBody.Builder()
            files.entries.forEach {
                val body = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), it.value);
                builder.addFormDataPart(it.key, it.value.name, body)
            }

            val multipartBody1 = builder
                .setType(MultipartBody.FORM)
                .build();
            val res = ApiService.apiService.uploadList(multipartBody1.parts)
            return res.data
        } catch (e: Exception) {
            e.printStackTrace()
            ToastUtilsExt.info("上传出错")
        }
        return null
    }
}