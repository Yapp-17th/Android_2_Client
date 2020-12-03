package com.example.sport_planet.data.model

import android.view.View
import com.example.sport_planet.data.enums.MenuEnum

data class MenuModel(
    val menuType: MenuEnum,
    val event: View.OnClickListener
)