package com.example.sport_planet.model

import com.example.sport_planet.model.response.GroupStatusResponse
import com.example.sport_planet.model.response.HostResponse

data class BoardContentModel(
        val boardId: Int,
        val city: String,
        val content: String,
        val exercise: String,
        val groupStatusResponse: GroupStatusResponse,
        val hostResponse: HostResponse,
        val isBookMark: Boolean,
        val place: String,
        val recruitNumber: Int,
        val recruitedNumber: Int,
        val title: String
    )