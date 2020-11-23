package com.example.sport_planet.presentation.write

import com.example.sport_planet.presentation.base.BaseViewModel
import java.util.*

class WriteViewModel : BaseViewModel() {
    var date: Date? = null
    var time: String = ""

    fun clearDateAndTime() {
        date = null
        time = ""
    }

}