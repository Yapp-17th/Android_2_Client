package com.yapp.sport_planet.data.enums

import com.yapp.sport_planet.R

enum class SeparatorEnum {
    NONE {
        override val colorId: Int
            get() = R.color.black
    },
    Host {
        override val colorId: Int
            get() = R.color.pink
    },
    Guest {
        override val colorId: Int
            get() = R.color.skyblue
    };

    abstract val colorId: Int
}