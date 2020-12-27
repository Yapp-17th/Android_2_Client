package com.yapp.sport_planet.data.model

import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse

data class BoardContentModel(
    val boardId: Long,
    val city: RegionResponse.Data,
    val content: String,
    val exercise: ExerciseResponse.Data,
    val groupStatus: GroupStatusModel,
    val host: HostModel,
    val isBookMark: Boolean,
    val place: String,
    val recruitNumber: Int,
    val recruitedNumber: Int,
    val title: String,
    val boardTime: String,
    val startsAt: String,
    val userTag: UserTagModel
)

fun BoardContentModel.toBoardModel(): BoardModel {
    return BoardModel(
            boardId = boardId,
            hostId = host.hostId,
            hostName = host.hostName,
            title = title,
            groupStatus = groupStatus,
            exercise = exercise.name,
            city = city.name,
            isBookMark = isBookMark,
            boardTime = boardTime,
            recruitNumber = recruitNumber,
            recruitedNumber = recruitedNumber,
            startsAt = startsAt,
            userTag = userTag.name
    )
}