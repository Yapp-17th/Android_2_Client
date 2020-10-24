package com.example.sport_planet.model

import com.example.sport_planet.R

enum class SeparatorEnum {
    NONE {
        override val colorId: Int
            get() = R.color.black
    },
    HOST {
        override val colorId: Int
            get() = R.color.pink
    },
    GUEST {
        override val colorId: Int
            get() = R.color.skyblue
    };

    abstract val colorId: Int
}