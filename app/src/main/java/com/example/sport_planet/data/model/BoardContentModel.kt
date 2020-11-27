package com.example.sport_planet.data.model

import com.example.sport_planet.data.response.GroupStatus
import com.example.sport_planet.data.response.HostModel

data class BoardContentModel(
        val boardId: Long,
        val city: String,
        val content: String,
        val exercise: String,
        val groupStatus: GroupStatus,
        val host: HostModel,
        val isBookMark: Boolean,
        val place: String,
        val recruitNumber: Int,
        val recruitedNumber: Int,
        val title: String,
        val boardTime: String
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
            recruitedNumber = recruitedNumber
    )
}