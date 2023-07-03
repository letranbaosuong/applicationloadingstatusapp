//package com.letranbaosuong.applicationloadingstatusapp.receiver
//
//import android.app.DownloadManager
//import android.app.NotificationManager
//import android.content.BroadcastReceiver
//import android.content.ContentValues.TAG
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import androidx.core.content.ContextCompat
//import com.letranbaosuong.applicationloadingstatusapp.ButtonState
//import com.letranbaosuong.applicationloadingstatusapp.R
//import com.letranbaosuong.applicationloadingstatusapp.utilities.sendNotification
//
//class DownloadReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, downloadID)
//        id?.let {
//            Log.d(TAG, "onReceive EXTRA_DOWNLOAD: Finished with Success: ${getDownloadStatus(id)}")
//            btnDownload.setState(ButtonState.Completed)
//            val notification = NotificationBody(
//                currentOptionName,
//                getDownloadStatus(id)
//            )
//            sendNotification(notification)
//        }
//    }
//
//}