package com.example.apiunduhsharecoba

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var btndownload: Button
    private lateinit var btnshare: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btndownload = findViewById(R.id.btnDownload)
        btnshare = findViewById(R.id.btnshare)

        btndownload.setOnClickListener {
            download()
        }

        btnshare.setOnClickListener {
            share()
        }
    }

    private fun download() {
        try {
            val url = "https://firebasestorage.googleapis.com/v0/b/dbnaviku.appspot.com/o/rarfile%2Fbenda%2FBangunan.rar?alt=media&token=[object%20Object]"
            val fileName = "Bangunan.rar"
            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse(url)
            val request = DownloadManager.Request(uri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("application/x-rar-compressed")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(fileName)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            downloadManager.enqueue(request)
            Toast.makeText(this, "Berhasil diunduh", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal mengunduh", Toast.LENGTH_LONG).show()
        }
    }

    private fun share() {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Bangunan.rar")

        val uri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", file)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "file/rar"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(intent, "Share File"))
    }

}
