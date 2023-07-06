package com.letranbaosuong.applicationloadingstatusapp

import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

const val DETAIL_ACTIVITY_INTENT_STATUS_KEY = "Status"
const val DETAIL_ACTIVITY_INTENT_FILENAME_KEY = "FileName"

class DetailActivity : AppCompatActivity() {
    private lateinit var buttonOk: Button
    private lateinit var fileNameValue: TextView
    private lateinit var statusValue: TextView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val detailToolbar: Toolbar = findViewById(R.id.detail_toolbar)
        buttonOk = findViewById(R.id.buttonOk)
        fileNameValue = findViewById(R.id.fileNameValue)
        statusValue = findViewById(R.id.statusValue)
        setSupportActionBar(detailToolbar)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancelAll()

        val fileName = intent.getStringExtra(DETAIL_ACTIVITY_INTENT_STATUS_KEY)
        fileNameValue.text = fileName

        val status = intent.getStringExtra(DETAIL_ACTIVITY_INTENT_FILENAME_KEY)
        statusValue.text = status

        buttonOk.setOnClickListener {
            finish()
        }
    }
}
