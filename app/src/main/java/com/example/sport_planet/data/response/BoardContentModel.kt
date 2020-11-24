package com.example.sport_planet.model

import com.example.sport_planet.data.response.GroupStatus
import com.example.sport_planet.data.response.HostModel

data class BoardContentModel(
        val boardId: Int,
        val city: String,
        val content: String,
        val exercise: String,
        val groupStatus: GroupStatus,
        val hostModel: HostModel,
        val isBookMark: Boolean,
        val place: String,
        val recruitNumber: Int,
        val recruitedNumber: Int,
        val title: String
    )