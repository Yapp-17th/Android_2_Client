package com.yapp.sport_planet.data.enums

import com.yapp.sport_planet.R

enum class MenuEnum {
    STAR {
        override val resourceId: Int
            get() = if (enabled) R.drawable.ic_star_enabled else R.drawable.ic_star_disabled
    },
    MENU {
        override val resourceId: Int
            get() = R.drawable.ic_menu
    };

    abstract val resourceId: Int
    var enabled: Boolean = false
}