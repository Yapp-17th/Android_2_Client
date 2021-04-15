package com.yapp.data

import com.yapp.data.model.BoardInfo
import com.yapp.data.model.GroupStatusModel
import com.yapp.data.model.HostModel
import com.yapp.data.model.UserTagModel
import com.yapp.data.model.mypage.ApplyStatus
import com.yapp.data.request.EvaluateReportRequest
import com.yapp.data.request.MyViewEditRequest
import com.yapp.data.request.board.ReportRequest
import com.yapp.data.response.OtherHistoryResponse
import com.yapp.data.response.ServerCallBackResponse
import com.yapp.data.response.basic.ExerciseResponse
import com.yapp.data.response.basic.RegionResponse
import com.yapp.data.response.board.BoardContentResponse
import com.yapp.data.response.board.BoardListResponse
import com.yapp.data.response.chatting.*
import com.yapp.data.response.common.CommonResponse
import com.yapp.data.response.common.UserTagResponse
import com.yapp.data.response.login.LoginResponse
import com.yapp.data.response.login.SignUpResponse
import com.yapp.data.response.mypage.*
import com.yapp.domain.dto.*
import com.yapp.domain.dto.basic.ExerciseDto
import com.yapp.domain.dto.basic.RegionDto
import com.yapp.domain.dto.board.BoardContentDto
import com.yapp.domain.dto.board.BoardDto
import com.yapp.domain.dto.chatting.*
import com.yapp.domain.dto.common.CommonDto
import com.yapp.domain.dto.common.UserTagDto
import com.yapp.domain.dto.login.LoginDto
import com.yapp.domain.dto.login.SignUpDto
import com.yapp.domain.dto.mypage.*
import io.reactivex.Single

fun CommonServerResponse.responseToDto() = Single.just(
    CommonServerDto(
        transactionTime = transactionTime,
        status = status,
        responseType = responseType,
        message = message
    )
)

fun CommonResponse.responseToDto() = Single.just(
    CommonDto(
        status = status,
        success = success,
        message = message
    )
)

fun ServerCallBackResponse.responseToDto() = Single.just(
    ServerCallBackDto(
        message = message,
        status = status,
        success = success
    )
)

fun BoardContentResponse.responseToDto() = Single.just(
    with(this.data) {
        BoardContentDto(
            boardId = boardId,
            city = city.responseToDto(),
            content = content,
            exercise = exercise.responseToDto(),
            groupStatus = groupStatus.modelToDto(),
            host = host.modelToDto(),
            isBookMark = isBookMark,
            place = place,
            recruitedNumber = recruitedNumber,
            recruitNumber = recruitNumber,
            title = title,
            boardTime = boardTime,
            startsAt = startsAt,
            userTag = userTag.modelToDto()
        )
    }
)

fun ApplyListResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            ApplyListDto(
                applyStatus = it.applyStatus.modelToDto(),
                chattingRoomId = it.chattingRoomId,
                guestId = it.guestId,
                guestName = it.guestName,
                isHost = it.isHost,
                hostId = it.hostId,
                boardId = it.boardId
            )
        }

    }
)

fun BoardListResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            BoardDto(
                boardId = it.boardId,
                hostId = it.hostId,
                hostName = it.hostName,
                title = it.title,
                groupStatus = it.groupStatus.modelToDto(),
                exercise = it.exercise,
                city = it.city,
                isBookMark = it.isBookMark,
                boardTime = it.boardTime,
                recruitNumber = it.recruitNumber,
                recruitedNumber = it.recruitedNumber,
                startsAt = it.startsAt,
                userTag = it.userTag
            )
        }
    }
)

fun MyBookMarksResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            MyBookMarksDto(
                boardId = it.boardId,
                hostId = it.hostId,
                hostName = it.hostName,
                title = it.title,
                groupStatus = it.groupStatus,
                exercise = it.exercise,
                city = it.city,
                isBookMark = it.isBookMark,
                recruitNumber = it.recruitNumber,
                recruitedNumber = it.recruitedNumber,
                time = it.time
            )
        }
    }
)

fun ChattingMessageListResponse.responseToDto() = Single.just(
    ChattingMessageListDto(
        transactionTime = transactionTime,
        firstUnreadMessageId = firstUnreadMessageId,
        boardTitle = boardTitle,
        appliedStatus = appliedStatus,
        data = data.map {
            it.responseToDto()
        }
    )
)

fun ChattingMessageResponse.responseToDto() = ChattingMessageDto(
    id = id,
    content = content,
    type = type,
    realTimeUpdateType = realTimeUpdateType,
    isHostRead = isHostRead,
    isGuestRead = isGuestRead,
    messageId = messageId,
    chatRoomId = chatRoomId,
    senderId = senderId,
    senderNickname = senderNickname,
    createdAt = createdAt

)

fun ChattingRoomListResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            ChattingRoomListDto(
                id = it.id,
                hostId = it.hostId,
                guestId = it.guestId,
                boardId = it.boardId,
                opponentNickname = it.opponentNickname,
                status = it.status,
                createdAt = it.createdAt,
                lastMessage = it.lastMessage.responseToDto(),
                unreadMessages = it.unreadMessages
            )
        }
    }
)

fun MakeChattingRoomResponse.responseToDto() = Single.just(
    with(this.data) {
        MakeChattingRoomDto(
            id = id,
            hostId = hostId,
            guestId = guestId,
            boardId = boardId,
            opponentNickname = opponentNickname,
            createdAt = createdAt
        )
    }
)

fun ExerciseResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            ExerciseDto(
                id = it.id,
                name = it.name
            )
        }
    }
)

fun RegionResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            RegionDto(
                id = it.id,
                name = it.name
            )
        }
    }
)

fun MyViewHistoryResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            MyViewHistoryDto(
                isHost = it.isHost,
                nickName = it.nickName,
                isContinue = it.isContinue,
                boardInfo = it.boardInfo.modelToDto()
            )
        }
    }
)

fun OtherHistoryResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            OtherHistoryDto(
                isHost = it.isHost,
                nickName = it.nickName,
                isContinue = it.isContinue,
                boardInfo = it.boardInfo.modelToDto()
            )
        }
    }
)

fun HistoryResponse.responseToDto() = Single.just(
    with(this.data) {
        HistoryDto(
            category = category,
            city = city,
            dislike = dislike,
            intro = intro,
            isMine = isMine,
            like = like,
            nickName = nickName
        )
    }
)

fun EvaluateListResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map {
            EvaluateDto(
                userId = it.userId,
                nickName = it.nickName,
                isHost = it.isHost,
                isLike = it.isLike,
                isDislike = it.isDislike
            )
        }
    }
)

fun UserTagResponse.responseToDto() = Single.just(
    with(this.data) {
        this.map { UserTagDto(id = it.id, name = it.name) }
    }
)

fun MyProfileEditResponse.responseToDto() = Single.just(
    with(this.data) {
        MyProfileEditDto(
            userId = userId,
            userName = userName,
            nickName = nickName,
            email = email,
            intro = intro,
            category = category.map { it.responseToDto() },
            city = city.responseToDto()
        )
    }
)

fun BoardInfo.modelToDto() = BoardInfoDto(
    boardId = boardId,
    hostId = hostId,
    hostName = hostName,
    title = title,
    groupStatus = groupStatus,
    exercise = exercise,
    city = city,
    isBookMark = isBookMark,
    recruitNumber = recruitNumber,
    recruitedNumber = recruitedNumber,
    time = time
)

fun ReportRequestDto.requestDtoToRequest() =
    ReportRequest(
        boardId = boardId,
        reportType = reportType,
        content = content
    )

fun EvaluateReportRequestDto.dtoToRequest() =
    EvaluateReportRequest(
        userId = userId,
        reportType = reportType,
        content = content
    )

fun MyViewEditRequestDto.dtoToRequest() =
    MyViewEditRequest(
        address= address,
        category = category,
        email = email,
        intro = intro,
        nickName = nickName,
        userName = userName
    )

fun LoginDto.dtoToResponse() =
    LoginResponse(
        accessToken = accessToken,
        email = email,
        nickName = nickName,
        userId = userId
    )

fun SignUpDto.dtoToResponse() =
    SignUpResponse(
        userId = userId,
        userName = userName,
        email = email,
        accessToken = accessToken,
        nickName = nickName,
        address = address,
        category = category,
        intro = intro
    )

fun RegionResponse.Data.responseToDto() = RegionDto(id, name)
fun ExerciseResponse.Data.responseToDto() = ExerciseDto(id, name)
fun GroupStatusModel.modelToDto() = GroupStatusDto(code, name)
fun HostModel.modelToDto() = HostDto(dislikes, hostId, hostName, likes, intro)
fun UserTagModel.modelToDto() = UserTagDto(id, name)
fun ApplyStatus.modelToDto() = ApplyStatusDto(code, name)
