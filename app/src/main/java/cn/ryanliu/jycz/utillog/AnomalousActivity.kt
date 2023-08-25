package cn.ryanliu.jycz.utillog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.text.SpannableString
import android.text.Spanned
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import cn.ryanliu.jycz.R
import cn.ryanliu.jycz.utillog.Anomalous.Companion.getAllErrorDetailsFromIntent
import cn.ryanliu.jycz.utillog.AppTool.Companion.getAppIcon
import cn.ryanliu.jycz.utillog.AppTool.Companion.getAppName
import cn.ryanliu.jycz.utillog.AppTool.Companion.getVersionName
import com.blankj.utilcode.util.AppUtils
import java.io.File
import java.net.URLEncoder

class AnomalousActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anomalous)

        val restartButton = findViewById<Button>(R.id.bt_restart)
        val closeButton = findViewById<Button>(R.id.bt_close)
        val btOpen = findViewById<Button>(R.id.bt_open)
        val tvTitle = findViewById<TextView>(R.id.tv_nav_title)
        val tvVersion = findViewById<TextView>(R.id.tv_version)
        val ivIcon = findViewById<ImageView>(R.id.iv_icon)

        AppUtils.getAppName()
        tvTitle.text = getAppName(this)
        ivIcon.background = getAppIcon(this)
        tvVersion.text = "版本号: v" + getVersionName(this)

        restartButton.setOnClickListener {
            val intent = Intent(
                this@AnomalousActivity,
                Anomalous.guessRestartActivityClass(this@AnomalousActivity)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            startActivity(intent)
            finish()
        }
        closeButton.setOnClickListener { finish() }

        val message = getAllErrorDetailsFromIntent(this@AnomalousActivity, intent)
        val logPath = (getExternalFilesDir(null)?.absolutePath
            ?: filesDir.absolutePath) + File.separator + "log"
        val locateButton = findViewById<TextView>(R.id.crash_error_locate_more_info_button)
        locateButton.text = "${locateButton.text}\n${logPath}\n"
        val moreInfoButton = findViewById<Button>(R.id.crash_error_activity_more_info_button)
        moreInfoButton.setOnClickListener { //We retrieve all the error data and show it
            val dialog = AlertDialog.Builder(this@AnomalousActivity)
                .setTitle("异常信息")
                .setMessage(message)
                .setPositiveButton("关闭", null)
                .setNeutralButton("复制到剪贴板") { dialog, which -> copyErrorToClipboard() }
                .show()
        }

        btOpen.setOnClickListener {
            val file = File(logPath);
            if (!file.exists()) {
                return@setOnClickListener;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                val uri = Uri.parse(
                    "content://com.android.externalstorage.documents/document/primary:" + URLEncoder.encode(
                        FileProvider.getUriForFile(
                            this,
                            packageName + ".fileprovider",
                            file
                        ).path?.substring("/external_files".length)
                    )
                );
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setDataAndType(Uri.fromFile(file), "file/*");
                intent.setDataAndType(
                    FileProvider.getUriForFile(
                        this,
                        packageName + ".fileprovider",
                        file
                    ), "file/*"
                )
                startActivity(intent)

            }

        }

//        if(!BuildConfig.DEBUG) {
//            message?.let {
//                //发送邮件给管理员
//                val mail = Mail().apply {
//                    mailServerHost = "smtp.126.com"
//                    mailServerPort = "465"
//                    fromAddress = "syds_zwl@126.com"
//                    password = "IZATHKTPHYEWXCJY"
//                    toAddress = arrayListOf("syds_zwl@163.com")
//                    subject = AppUtils.getAppName()
//                    content = SpannableString(message)
//                    openSSL = true
////                            val logPath = (context.getExternalFilesDir(null)?.absolutePath
////                                ?: context.filesDir.absolutePath) + File.separator + "log"+ File.separator + "logs_0.csv"
////                            attachFiles = arrayListOf(File(logPath))
//                }
//                // 发送邮箱
//                MailSender.getInstance().sendMail(mail, object : MailSender.OnMailSendListener {
//                    override fun onError(e: Throwable) {
//                        Timber.e(e)
//                    }
//
//                    override fun onSuccess() {
//                    }
//
//                })
//            }
//        }

    }

    private fun copyErrorToClipboard() {
        val errorInformation = getAllErrorDetailsFromIntent(this@AnomalousActivity, intent)
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        //Are there any devices without clipboard...?
        val clip = ClipData.newPlainText("错误信息", errorInformation)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this@AnomalousActivity, "已复制到剪贴板", Toast.LENGTH_SHORT).show()
    }
}