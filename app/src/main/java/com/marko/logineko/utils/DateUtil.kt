package com.marko.logineko.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun parseServerDateInto(date: String, pattern: String): String {
        val parser = SimpleDateFormat("yyyy-MM-ddTHH:mm:ss", Locale.getDefault())
        val serverDate = parser.parse(date)
        val formater = SimpleDateFormat(pattern, Locale.getDefault())
        return formater.format(serverDate!!)
    }
}
