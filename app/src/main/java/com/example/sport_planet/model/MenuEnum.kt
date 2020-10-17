package com.example.sport_planet.model

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.sport_planet.R

enum class MenuEnum {
    STAR_ENABLED {
        override val resourceId: Int
            get() = R.drawable.ic_star_enabled
    },
    STAR_DISABLED {
        override val resourceId: Int
            get() = R.drawable.ic_star_disabled
    },
    MENU {
        override val resourceId: Int
            get() = R.drawable.ic_menu
    };

    abstract val resourceId: Int
}