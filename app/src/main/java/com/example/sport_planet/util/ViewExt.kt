package com.example.sport_planet.util

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:setTextLongToString")
fun setTextLongToString(textView: TextView, long: Long) {
    textView.text = long.toString()
}