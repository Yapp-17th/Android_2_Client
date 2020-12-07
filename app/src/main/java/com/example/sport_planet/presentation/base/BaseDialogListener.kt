package com.example.sport_planet.presentation.base

import android.content.Intent

interface BaseDialogListener {
    fun onAccept(data : Intent? = null)
}