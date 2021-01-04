package com.yapp.sport_planet.presentation.chatting.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beust.klaxon.JsonObject
import com.yapp.sport_planet.data.enums.ApprovalStatusButtonEnum
import com.yapp.sport_planet.data.model.chatting.ChatRoomInfo
import com.yapp.sport_planet.data.response.chatting.ChattingMessageListResponse
import com.yapp.sport_planet.data.response.chatting.ChattingMessageResponse
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.presentation.chatting.ChattingConstant
import com.yapp.sport_planet.presentation.chatting.EventWrapper
import com.yapp.sport_planet.presentation.chatting.UserInfo
import com.yapp.sport_planet.remote.NetworkHelper
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
import com.yapp.sport_planet.util.applySchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

class ChattingActivityViewModel(private val chatRoomInfo: ChatRoomInfo) : BaseViewModel() {

    private val TAG = "ChattingActivityViewModel"

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private lateinit var chattingMessage: ChattingMessageResponse

    private val _chattingMessageListResponseLiveData = MutableLiveData<ChattingMessageListResponse>()
    val chattingMessageListResponseLiveData: LiveData<ChattingMessageListResponse>
        get() = _chattingMessageListResponseLiveData

    private val _noticeMessageLiveData = MutableLiveData<ChattingMessageResponse>()
    val noticeMessageLiveData: LiveData<ChattingMessageResponse>
        get() = _noticeMessageLiveData

    private val _chattingMessageLiveData = MutableLiveData<ChattingMessageResponse>()
    val chattingMessageLiveData: LiveData<ChattingMessageResponse>
        get() = _chattingMessageLiveData

    private val _approvalStatusLiveData = MutableLiveData<ApprovalStatusButtonEnum>()
    val approvalStatusLiveData: LiveData<ApprovalStatusButtonEnum>
        get() = _approvalStatusLiveData

    private val _showErrorToastLiveData = MutableLiveData<EventWrapper<Boolean>>()
    val showErrorToastLiveData: LiveData<EventWrapper<Boolean>>
        get() = _showErrorToastLiveData

    private lateinit var mStompClient: StompClient
    private lateinit var topic: Disposable
    private var closeSocket = false

    private var chattingMessageJsonObject = JSONObject()

    private var readMessage = ArrayList<Long>()

    class ViewModelFactory(private val chatRoomInfo: ChatRoomInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(ChattingActivityViewModel::class.java)) {
                ChattingActivityViewModel(chatRoomInfo) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }

    fun settingChattingMessageList(chatRoomId: Long){
        compositeDisposable.add(
            remoteDataSourceImpl.getChattingMessageList(chatRoomId)
                .applySchedulers()
                .subscribe({
                    it.run {
                        _approvalStatusLiveData.postValue(approvalStatus(chatRoomInfo.isHost, it.appliedStatus))
                        _chattingMessageListResponseLiveData.postValue(it)
                    }
            },{
                    Log.d(TAG, it.localizedMessage)
            })
        )
    }

    @SuppressLint("CheckResult")
    @OptIn(UnstableDefault::class)
    fun initSocket() {
        val url = ChattingConstant.URL

        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        mStompClient.lifecycle()
            .subscribe { lifecycleEvent: LifecycleEvent ->
                when (lifecycleEvent.type!!) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.d(TAG, "[OPENED] 채탕방 웹소켓 OPENED")
                        topic = mStompClient.topic("/sub/chat/room/${chatRoomInfo.chatRoomId}")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe ({ stompMessage ->
                                chattingMessage = Json.parse(ChattingMessageResponse.serializer(), stompMessage.payload)
                                when(chattingMessage.realTimeUpdateType) {
                                    ChattingConstant.REAL_TIME_MESSAGE_READ -> {
                                        readMessage.add(chattingMessage.id)
                                        when(chattingMessage.type){
                                            ChattingConstant.CHAT_BOT_MESSAGE -> _noticeMessageLiveData.postValue(chattingMessage)
                                            else -> _chattingMessageLiveData.postValue(chattingMessage)
                                        }
                                    }
                                    else -> _approvalStatusLiveData.postValue(approvalStatus(chatRoomInfo.isHost, chattingMessage.realTimeUpdateType))
                                }
                            }, {
                                Log.d(TAG, it.localizedMessage)
                            })
                    }
                    LifecycleEvent.Type.ERROR -> Log.d(TAG, "[ERROR]: 채팅방 웹소켓 ERROR")
                    LifecycleEvent.Type.CLOSED -> {
                        if(!closeSocket)
                            Log.d(TAG, "[CLOSED]: 채팅방 웹소켓 비정상적인 CLOSE")
                        else
                            Log.d(TAG, "[CLOSED]: 채팅방 웹소켓 정상적인 CLOSE")
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> Log.d(TAG, "[CLOSED]: 채팅방 웹소켓 SERVER HEARTBEAT FAILED")
                }
            }
        mStompClient.connect()
    }

    fun disconnectSocket(){
        closeSocket = true
        mStompClient.disconnect()
    }

    @SuppressLint("CheckResult")
    fun sendReadUpdateMessage(){
        try {
            chattingMessageJsonObject.put("content", readMessage)
            chattingMessageJsonObject.put("type", "UPDATE")
            chattingMessageJsonObject.put("senderId", UserInfo.USER_ID)
            chattingMessageJsonObject.put("chatRoomId", chatRoomInfo.chatRoomId)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mStompClient.send("/pub/v1/chat/message/update", chattingMessageJsonObject.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "STOMP echo send successfully");
            },{
                Log.d(TAG, it.toString()) }
            )
    }

    @SuppressLint("CheckResult")
    fun sendMessage(chattingMessageContent: String){
        try {
            chattingMessageJsonObject.put("content", chattingMessageContent)
            chattingMessageJsonObject.put("type", ChattingConstant.TALK_MESSAGE)
            chattingMessageJsonObject.put("senderId", UserInfo.USER_ID)
            chattingMessageJsonObject.put("chatRoomId", chatRoomInfo.chatRoomId)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mStompClient.send("/pub/v1/chat/message", chattingMessageJsonObject.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "STOMP echo send successfully");
            },{
                Log.d(TAG, it.toString()) }
            )
    }

    fun approvalStatusButtonOnClick(){
        when(_approvalStatusLiveData.value){
            ApprovalStatusButtonEnum.GUEST_APPLY -> applyBoard()
            ApprovalStatusButtonEnum.HOST_APPROVE -> approveBoard()
            ApprovalStatusButtonEnum.HOST_APPROVE_CANCLE -> disapproveBoard()
        }
    }

    private fun applyBoard(){
        val applyBoardObject = JsonObject()
        applyBoardObject["chatRoomId"] = chatRoomInfo.chatRoomId
        compositeDisposable.add(
            remoteDataSourceImpl.applyBoard(chatRoomInfo.boardId, applyBoardObject)
                .applySchedulers()
                .subscribe({
                    Log.d(TAG, it.message)
                    if(it.status == 400)
                        _showErrorToastLiveData.value = EventWrapper(true)
                },{
                    Log.d(TAG, it.localizedMessage)}
                )
        )
    }

    private fun approveBoard(){
        val approveBoardObject = JsonObject()
        approveBoardObject["chatRoomId"] = chatRoomInfo.chatRoomId
        approveBoardObject["guestId"] = chatRoomInfo.guestId
        compositeDisposable.add(
            remoteDataSourceImpl.approveBoard(chatRoomInfo.boardId, approveBoardObject)
                .applySchedulers()
                .subscribe({
                    Log.d(TAG, it.message)
                    if(it.status == 400)
                        _showErrorToastLiveData.value = EventWrapper(true)
                },{
                    Log.d(TAG, it.localizedMessage)}
                )
        )
    }

    private fun disapproveBoard(){
        val disapproveBoardObject = JsonObject()
        disapproveBoardObject["chatRoomId"] = chatRoomInfo.chatRoomId
        disapproveBoardObject["guestId"] = chatRoomInfo.guestId
        compositeDisposable.add(
            remoteDataSourceImpl.disapproveBoard(chatRoomInfo.boardId, disapproveBoardObject)
                .applySchedulers()
                .subscribe({
                    Log.d(TAG, it.message)
                    if(it.status == 400)
                        _showErrorToastLiveData.value = EventWrapper(true)
                },{
                    Log.d(TAG, it.localizedMessage) }
                )
        )
    }

    private fun approvalStatus(isHost: Boolean, status: String): ApprovalStatusButtonEnum {
        return when(isHost){
            false -> when(status){
                ChattingConstant.REAL_TIME_PENDING -> ApprovalStatusButtonEnum.GUEST_APPLY
                ChattingConstant.REAL_TIME_APPLIED, ChattingConstant.REAL_TIME_DISAPPROVED -> ApprovalStatusButtonEnum.GUEST_APPROVE_AWAIT
                ChattingConstant.REAL_TIME_APPROVED -> ApprovalStatusButtonEnum.GUEST_APPROVE_SUCCESS
                else -> throw IllegalArgumentException("적절하지 않은 Guest AppliedStatus")
            }
            true  -> when(status){
                ChattingConstant.REAL_TIME_PENDING -> ApprovalStatusButtonEnum.HOST_NONE
                ChattingConstant.REAL_TIME_APPLIED, ChattingConstant.REAL_TIME_DISAPPROVED -> ApprovalStatusButtonEnum.HOST_APPROVE
                ChattingConstant.REAL_TIME_APPROVED -> ApprovalStatusButtonEnum.HOST_APPROVE_CANCLE
                else -> throw IllegalArgumentException("적절하지 않은 Host AppliedStatus")
            }
        }
    }
}
