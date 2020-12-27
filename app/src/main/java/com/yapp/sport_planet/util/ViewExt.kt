package com.yapp.sport_planet.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:setTextLongToString")
fun setTextLongToString(textView: TextView, long: Long) {
    textView.text = long.toString()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}