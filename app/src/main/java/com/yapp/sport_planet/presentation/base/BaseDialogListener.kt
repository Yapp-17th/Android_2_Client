package com.yapp.sport_planet.presentation.base

import android.content.Intent

interface BaseDialogListener {
    fun onAccept(data : Intent? = null)
}