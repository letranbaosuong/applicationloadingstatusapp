package com.letranbaosuong.applicationloadingstatusapp.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.letranbaosuong.applicationloadingstatusapp.DETAIL_ACTIVITY_INTENT_FILENAME_KEY
import com.letranbaosuong.applicationloadingstatusapp.DETAIL_ACTIVITY_INTENT_STATUS_KEY
import com.letranbaosuong.applicationloadingstatusapp.DetailActivity
import com.letranbaosuong.applicationloadingstatusapp.R

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    channelId: String,
    messageBody: String,
    applicationContext: Context,
    fileName: String,
    status: String
) {

    val detailActivityIntent = Intent(applicationContext, DetailActivity::class.java)
    detailActivityIntent.putExtra(DETAIL_ACTIVITY_INTENT_STATUS_KEY, status)
    detailActivityIntent.putExtra(DETAIL_ACTIVITY_INTENT_FILENAME_KEY, fileName)
    detailActivityIntent.setClass(applicationContext, DetailActivity::class.java)

    val buttonPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        detailActivityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(buttonPendingIntent)
        .addAction(
            R.drawable.ic_assistant_black_24dp,
            applicationContext.getString(R.string.notification_button),
            buttonPendingIntent
        )
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}