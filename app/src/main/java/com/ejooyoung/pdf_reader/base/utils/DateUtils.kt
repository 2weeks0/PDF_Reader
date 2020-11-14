package com.ejooyoung.pdf_reader.base.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    private val defaultDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun getCurrentTimeToDate(): String =
        defaultDateFormat.format(System.currentTimeMillis())
}