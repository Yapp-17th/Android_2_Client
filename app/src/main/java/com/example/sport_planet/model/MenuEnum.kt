package com.example.sport_planet.model

import com.example.sport_planet.R

enum class MenuEnum {
    STAR_ENABLED {
        override val resourceId: Int
            get() = R.drawable.ic_star_enabled
        override val onClick: () -> Unit
            get() = {}
    },
    STAR_DISABLED {
        override val resourceId: Int
            get() = R.drawable.ic_star_disabled
        override val onClick: () -> Unit
            get() = {}
    },
    MENU {
        override val resourceId: Int
            get() = R.drawable.ic_menu
        override val onClick: () -> Unit
            get() = {}
    };

    abstract val resourceId: Int
    abstract val onClick: () -> Unit
}