package cn.ryanliu.jycz.utillog

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Process

class AppTool {

    companion object {

        fun getVersionName(context: Context): String? {
            return try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (e: java.lang.Exception) {
                ""
            }
        }

        fun getAppName(context: Context, packageName: String): String? {
            return if (isNullString(packageName)) null else try {
                val pm = context.packageManager
                val pi = pm.getPackageInfo(packageName, 0)
                pi?.applicationInfo?.loadLabel(pm)?.toString()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        fun getAppName(context: Context): String? {
            val appName: String? = getAppName(context, context.packageName)
            return if (isNullString(appName)) {
                ""
            } else {
                appName
            }
        }

        fun getPackageName(context: Context): String? {
            val appName = context.packageName
            return if (isNullString(appName)) {
                ""
            } else {
                appName
            }
        }


        /**
         * 获取App图标
         *
         * @param context 上下文
         * @return App图标
         */
        @JvmStatic
        fun getAppIcon(context: Context): Drawable? {
            return getAppIcon(context, context.packageName)
        }

        /**
         * 获取App图标
         *
         * @param context     上下文
         * @param packageName 包名
         * @return App图标
         */
        @JvmStatic
        fun getAppIcon(context: Context, packageName: String): Drawable? {
            return if (isNullString(packageName)) null else try {
                val pm = context.packageManager
                val pi = pm.getPackageInfo(packageName, 0)
                pi?.applicationInfo?.loadIcon(pm)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }


        fun getDeviceModelName(): String? {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

        fun capitalize(s: String?): String? {
            if (s == null || s.length == 0) {
                return ""
            }
            val first = s[0]
            return if (Character.isUpperCase(first)) {
                s
            } else {
                first.uppercaseChar().toString() + s.substring(1)
            }
        }

        fun isNullString(str: String?): Boolean {
            return str == null || str.length == 0 || "null" == str
        }

        fun killCurrentProcess() {
            Process.killProcess(Process.myPid())
            System.exit(10)
        }
    }
}