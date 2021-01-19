package com.yapp.sport_planet.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources?.displayMetrics
        )
    }

    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun toTimeFormat(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
        val formatter = SimpleDateFormat("a h:mm", Locale.KOREA)
        return formatter.format(parser.parse(date))
    }

    @SuppressLint("SimpleDateFormat")
    fun toDateFormat(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.KOREA)
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
        return formatter.format(parser.parse(date))
    }

    @SuppressLint("SimpleDateFormat")
    fun toDateFormatHasTime(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.KOREA)
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA)
        return formatter.format(parser.parse(date))
    }

    @SuppressLint("SimpleDateFormat")
    fun toDateFormatForWrite(date: String): String {
        val parser = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA)
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.KOREA)
        return formatter.format(parser.parse(date))
    }

    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun dateToMillis(date: String): Long {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.KOREA)
        return parser.parse(date).time
    }
}