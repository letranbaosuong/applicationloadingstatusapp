package com.letranbaosuong.applicationloadingstatusapp

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.letranbaosuong.applicationloadingstatusapp.utilities.sendNotification

class MainActivity : AppCompatActivity() {
    private var downloadID: Long = 0
    private lateinit var loadingButton: LoadingButton

    private var urlSelected: String? = null
    private var fileNameSelected: String? = null

    private val receiverDownload = object : BroadcastReceiver() {
        @SuppressLint("Range")
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            loadingButton.buttonState = ButtonState.Completed

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

            val query = DownloadManager.Query()
            query.setFilterById(id!!)

            val cursor = downloadManager.query(query)
            if (cursor.moveToFirst()) {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                var downloadStatus = "Fail"
                if (DownloadManager.STATUS_SUCCESSFUL == status) {
                    downloadStatus = "Success"
                }

                val toast = Toast.makeText(
                    applicationContext,
                    getString(R.string.notification_description),
                    Toast.LENGTH_LONG
                )
                toast.show()

                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.sendNotification(
                    channelId = CHANNEL_ID,
                    messageBody = getString(R.string.notification_description),
                    applicationContext = applicationContext,
                    fileName = fileNameSelected!!,
                    status = downloadStatus,
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        loadingButton = findViewById(R.id.loading_button)
        setSupportActionBar(toolbar)

        registerReceiver(receiverDownload, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        loadingButton.setOnClickListener {

            loadingButton.buttonState = ButtonState.Loading
            if (urlSelected != null) {
                loadingButton.buttonState = ButtonState.Loading
                download()

            } else {
                val toast = Toast.makeText(
                    applicationContext,
                    getString(R.string.please_select_file),
                    Toast.LENGTH_LONG
                )
                toast.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    loadingButton.buttonState = ButtonState.Completed
                }, 2000)
            }
        }

        createChannel(CHANNEL_ID, getString(R.string.notification_channel_name))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun download() {

        val request = DownloadManager.Request(Uri.parse(urlSelected))
            .setRequiresCharging(false)
            .setAllowedOverRoaming(true)
            .setAllowedOverMetered(true)
            .setTitle(getString(R.string.app_name))
            .setDescription(getString(R.string.app_description))

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)
    }

    companion object {
        private const val CHANNEL_ID = "channelId"
        private const val LOAD_APP_URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/masterABC.zip"
        private const val RETROFIT_URL =
            "https://github.com/square/retrofit/archive/refs/heads/master.zip"
        private const val GLIDE_URL =
            "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton && view.isChecked) {

            when (view.getId()) {
                R.id.radio_retrofit ->
                    urlSelected = RETROFIT_URL

                R.id.radio_load_app ->
                    urlSelected = LOAD_APP_URL

                R.id.radio_glide ->
                    urlSelected = GLIDE_URL
            }

            fileNameSelected = view.text.toString()
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description =
                getString(R.string.notification_channel_name_description)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiverDownload)
        super.onDestroy()
    }
}
