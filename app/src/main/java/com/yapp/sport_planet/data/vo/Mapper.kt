package com.yapp.sport_planet.data.vo


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
import com.yapp.sport_planet.data.vo.basic.ExerciseVo
import com.yapp.sport_planet.data.vo.basic.RegionVo
import com.yapp.sport_planet.data.vo.board.BoardContentVo
import com.yapp.sport_planet.data.vo.board.BoardVo
import com.yapp.sport_planet.data.vo.chatting.*
import com.yapp.sport_planet.data.vo.common.CommonVo
import com.yapp.sport_planet.data.vo.common.UserTagVo
import com.yapp.sport_planet.data.vo.mypage.*

fun CommonServerDto.dtoToVo() =
    CommonServerVo(
        transactionTime = transactionTime,
        status = status,
        responseType = responseType,
        message = message
    )

fun CommonDto.dtoToVo() =
    CommonVo(
        status = status,
        success = success,
        message = message
    )

fun ServerCallBackDto.dtoToVo() =
    ServerCallBackVo(
        message = message,
        status = status,
        success = success
    )


fun BoardContentDto.dtoToVo() =
    BoardContentVo(
        boardId = boardId,
        city = city.dtoToVo(),
        content = content,
        exercise = exercise.dtoToVo(),
        groupStatus = groupStatus.dtoToVo(),
        host = host.dtoToVo(),
        isBookMark = isBookMark,
        place = place,
        recruitedNumber = recruitedNumber,
        recruitNumber = recruitNumber,
        title = title,
        boardTime = boardTime,
        startsAt = startsAt,
        userTag = userTag.dtoToVo()
    )

fun ApplyListDto.dtoToVo() =
    ApplyListVo(
        applyStatus = applyStatus.dtoToVo(),
        chattingRoomId = chattingRoomId,
        guestId = guestId,
        guestName = guestName,
        isHost = isHost,
        hostId = hostId,
        boardId = boardId
    )

fun BoardDto.dtoToVo() =
    BoardVo(
        boardId = boardId,
        hostId = hostId,
        hostName = hostName,
        title = title,
        groupStatus = groupStatus.dtoToVo(),
        exercise = exercise,
        city = city,
        isBookMark = isBookMark,
        boardTime = boardTime,
        recruitNumber = recruitNumber,
        recruitedNumber = recruitedNumber,
        startsAt = startsAt,
        userTag = userTag
    )

fun MyBookMarksDto.dtoToVo() =
    MyBookMarksVo(
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

fun ChattingMessageListDto.dtoToVo() =
    ChattingMessageListVo(
        transactionTime = transactionTime,
        firstUnreadMessageId = firstUnreadMessageId,
        boardTitle = boardTitle,
        appliedStatus = appliedStatus,
        data = data.map {
            it.dtoToVo()
        }
    )

fun ChattingMessageDto.dtoToVo() = ChattingMessageVo(
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

fun ChattingRoomListDto.dtoToVo() =
    ChattingRoomListVo(
        id = id,
        hostId = hostId,
        guestId = guestId,
        boardId = boardId,
        opponentNickname = opponentNickname,
        status = status,
        createdAt = createdAt,
        lastMessage = lastMessage.dtoToVo(),
        unreadMessages = unreadMessages
    )

fun MakeChattingRoomDto.dtoToVo() =
    MakeChattingRoomVo(
        id = id,
        hostId = hostId,
        guestId = guestId,
        boardId = boardId,
        opponentNickname = opponentNickname,
        createdAt = createdAt
    )

fun ExerciseDto.dtoToVo() = ExerciseVo(id = id, name = name)

fun RegionDto.dtoToVo() = RegionVo(id = id, name = name)

fun MyViewHistoryDto.dtoToVo() =
    MyViewHistoryVo(
        isHost = isHost,
        nickName = nickName,
        isContinue = isContinue,
        boardInfo = boardInfo.dtoToVo()
    )

fun OtherHistoryDto.dtoToVo() =
    OtherHistoryVo(
        isHost = isHost,
        nickName = nickName,
        isContinue = isContinue,
        boardInfo = boardInfo.dtoToVo()
    )

fun HistoryDto.dtoToVo() =
    HistoryVo(
        category = category,
        city = city,
        dislike = dislike,
        intro = intro,
        isMine = isMine,
        like = like,
        nickName = nickName
    )

fun EvaluateDto.dtoToVo() =
    EvaluateVo(
        userId = userId,
        nickName = nickName,
        isHost = isHost,
        isLike = isLike,
        isDislike = isDislike
    )

fun UserTagDto.dtoToVo() = UserTagVo(id = id, name = name)

fun MyProfileEditDto.dtoToVo() =

    MyProfileEditVo(
        userId = userId,
        userName = userName,
        nickName = nickName,
        email = email,
        intro = intro,
        category = category.map { it.dtoToVo() },
        city = city.dtoToVo()
    )

fun BoardInfoDto.dtoToVo() = BoardInfoVo(
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


fun EvaluateReportRequestVo.voToDto() =
    EvaluateReportRequestDto(
        userId = userId,
        reportType = reportType,
        content = content
    )

fun MyViewEditRequestVo.voToDto() =
    MyViewEditRequestDto(
        address = address,
        category = category,
        email = email,
        intro = intro,
        nickName = nickName,
        userName = userName
    )

fun ReportRequestVo.voToDto() = ReportRequestDto(boardId, reportType, content)

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

fun RegionResponse.Data.responseToDto() = com.yapp.domain.dto.basic.RegionDto(id, name)
fun ExerciseResponse.Data.responseToDto() = com.yapp.domain.dto.basic.ExerciseDto(id, name)
fun GroupStatusDto.dtoToVo() = GroupStatusVo(code, name)
fun HostDto.dtoToVo() = HostVo(dislikes, hostId, hostName, likes, intro)
fun ApplyStatusDto.dtoToVo() = ApplyStatusVo(code, name)
