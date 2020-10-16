package com.example.sport_planet.presentation.chatting.model

import com.beust.klaxon.Json

data class ChattingMessage (
        @Json(name = "sender")
        val sender: String,
        @Json(name = "content")
        val content: String,
        @Json(name = "type")
        val type: String
)