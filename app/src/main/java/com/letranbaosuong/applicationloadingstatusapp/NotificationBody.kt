package com.letranbaosuong.applicationloadingstatusapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NotificationBody(
    val name: String = "",
    val status: Boolean = false
) : Parcelable