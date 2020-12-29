package com.yapp.sport_planet.data.enums

enum class TimeFilterEnum(val query: String, val print: String) {
    TIME_LATEST("latest", "최신순"),
    TIME_REMAIN("remain", "남은인원순"),
    TIME_DEADLINE("deadline", "기간임박순")
}