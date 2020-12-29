package com.yapp.sport_planet.data.model

import android.view.View
import com.yapp.sport_planet.data.enums.MenuEnum

data class MenuModel(
    val menuType: MenuEnum,
    val event: View.OnClickListener
)