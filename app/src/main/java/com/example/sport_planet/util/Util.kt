package com.example.sport_planet.util

import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context?.resources?.displayMetrics
        )
    }

    fun String.toDate(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }

    fun Date.formatTo(dateFormat: String = "a h:mm", timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.KOREA)
        formatter.timeZone = timeZone
        return formatter.format(this)
    }
}