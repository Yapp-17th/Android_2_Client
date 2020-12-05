package com.example.sport_planet.data.model

import java.util.*

data class BoardContentModel(
        val boardId: Long,
        val city: String,
        val content: String,
        val exercise: String,
        val groupStatus: GroupStatusModel,
        val host: HostModel,
        val isBookMark: Boolean,
        val place: String,
        val recruitNumber: Int,
        val recruitedNumber: Int,
        val title: String,
        val boardTime: String,
        val startsAt: Date,
        val userTag: String
)

fun BoardContentModel.toBoardModel(): BoardModel {
    return BoardModel(
            boardId = boardId,
            hostId = host.hostId,
            hostName = host.hostName,
            title = title,
            groupStatus = groupStatus,
            exercise = exercise,
            city = city,
            isBookMark = isBookMark,
            boardTime = boardTime,
            recruitNumber = recruitNumber,
            recruitedNumber = recruitedNumber,
            startsAt = startsAt,
            userTag = userTag
    )
}