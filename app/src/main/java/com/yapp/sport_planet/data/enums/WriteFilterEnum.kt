package com.yapp.sport_planet.data.enums

enum class WriteFilterEnum {
    EXERCISE {
        override val title: String
            get() = "운동종목"
    },
    CITY {
        override val title: String
            get() = "지역"
    },
    USERTAG {
        override val title: String
            get() = "게임성향"
    },
    NONE {
        override val title: String
            get() = "오류"
    };

    abstract val title: String
}