package com.example.sport_planet.util

import android.content.Context
import android.util.TypedValue

object Util {
    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context?.resources?.displayMetrics
        )
    }
}