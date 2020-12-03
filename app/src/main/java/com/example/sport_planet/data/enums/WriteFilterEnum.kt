package com.example.sport_planet.data.enums

enum class WriteFilterEnum {
    EXERCISE {
        override val title: String
            get() = "운동종목"
    },
    ADDRESS {
        override val title: String
            get() = "지역"
    },
    USERTAG {
        override val title: String
            get() = "게임성향"
    };

    abstract val title: String
}