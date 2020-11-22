package com.example.sport_planet.presentation.chatting.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beust.klaxon.JsonObject
import com.example.sport_planet.data.enums.ApprovalStatusButtonEnum
import com.example.sport_planet.data.model.ChatRoomInfo
import com.example.sport_planet.data.response.ChattingMessageListResponse
import com.example.sport_planet.data.response.ChattingMessageResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.ChattingConstant
import com.example.sport_planet.presentation.chatting.UserInfo
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

class ChattingActivityViewModel(private val chatRoomInfo: ChatRoomInfo) : BaseViewModel() {

    private val TAG = "ChattingActivityViewModel"

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private lateinit var chattingMessage: ChattingMessageResponse

    private val _chattingMessageListResponseLiveData = MutableLiveData<ChattingMessageListResponse>()
    val chattingMessageListResponseLiveData: LiveData<ChattingMessageListResponse>
        get() = _chattingMessageListResponseLiveData

    private val _chattingMessageLiveData = MutableLiveData<ChattingMessageResponse>()
    val chattingMessageLiveData: LiveData<ChattingMessageResponse>
        get() = _chattingMessageLiveData

    private val _approvalStatusLiveData = MutableLiveData<ApprovalStatusButtonEnum>()
    val approvalStatusLiveData: LiveData<ApprovalStatusButtonEnum>
        get() = _approvalStatusLiveData

    private lateinit var stompClient: StompClient
    private lateinit var stompConnection: Disposable
    private lateinit var topic: Disposable
    private var closeSocket = false

    private var chattingMessageJsonObject = JSONObject()

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

    private fun makeChattingMessageRead(chatRoomId: Long, messageId: Long){
        compositeDisposable.add(
            remoteDataSourceImpl.makeChattingMessageRead(chatRoomId, messageId)
                .applySchedulers()
                .subscribe({
                    Log.d(TAG, it.message)
                },{
                    Log.d(TAG, it.localizedMessage)}
                )
        )
    }

    @OptIn(UnstableDefault::class)
    fun initSocket(chatRoomId: Long) {
        val url = ChattingConstant.URL
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        stompClient = StompClient(client, intervalMillis).apply { this@apply.url = url }

        stompConnection = stompClient.connect()
            .subscribe ({
                when (it.type) {
                    Event.Type.OPENED -> {
                        Log.d(TAG, "[OPENED]: 웹소켓 OPEN")
                        topic = stompClient.join("/sub/chat/room/${chatRoomInfo.chatRoomId}")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe ({ stompMessage ->
                                chattingMessage = Json.parse(ChattingMessageResponse.serializer(), stompMessage)
                                when(chattingMessage.realTimeUpdateType) {
                                    "MESSAGE_READ" -> {
                                        _chattingMessageLiveData.postValue(chattingMessage)
                                        makeChattingMessageRead(chatRoomInfo.chatRoomId, chattingMessage.id!!)
                                    }
                                    else -> {
                                        _approvalStatusLiveData.postValue(approvalStatus(chatRoomInfo.isHost, chattingMessage.realTimeUpdateType!!))
                                    }
                                }
                            }, {
                                Log.d(TAG, it.localizedMessage)
                            })

                    }
                    Event.Type.CLOSED -> {
                        if(!closeSocket)
                            Log.d(TAG, "[CLOSED]: 웹소켓 비정상적인 CLOSE")
                        else
                            Log.d(TAG, "[CLOSED]: 웹소켓 정상적인 CLOSE")
                    }
                    Event.Type.ERROR -> {
                        Log.d(TAG, "[ERROR]: 웹소켓 ERROR")
                    }
            }
        },{Log.d("에러", it.localizedMessage)})
    }

    fun disconnectSocket(){
        closeSocket = true
        stompConnection.dispose()
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

        stompClient.send("/pub/v1/chat/message", chattingMessageJsonObject.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, it.toString())
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

    fun applyBoard(){
        val applyBoardObject = JsonObject()
        applyBoardObject.put("chatRoomId", chatRoomInfo.chatRoomId)
        compositeDisposable.add(
            remoteDataSourceImpl.applyBoard(chatRoomInfo.boardId, applyBoardObject)
                .applySchedulers()
                .subscribe({
                    Log.d(TAG, it.message)
                },{
                    Log.d(TAG, it.localizedMessage)}
                )
        )
    }

    fun approveBoard(){
        val approveBoardObject = JsonObject()
        approveBoardObject["chatRoomId"] = chatRoomInfo.chatRoomId
        approveBoardObject["guestId"] = chatRoomInfo.guestId
        compositeDisposable.add(
            remoteDataSourceImpl.approveBoard(chatRoomInfo.boardId, approveBoardObject)
                .applySchedulers()
                .subscribe({
                    Log.d(TAG, it.message)
                },{
                    Log.d(TAG, it.localizedMessage)}
                )
        )
    }

    fun disapproveBoard(){
        val disapproveBoardObject = JsonObject()
        disapproveBoardObject["chatRoomId"] = chatRoomInfo.chatRoomId
        disapproveBoardObject["guestId"] = chatRoomInfo.guestId
        compositeDisposable.add(
            remoteDataSourceImpl.disapproveBoard(chatRoomInfo.boardId, disapproveBoardObject)
                .applySchedulers()
                .subscribe({
                    Log.d(TAG, it.message)
                },{
                    Log.d(TAG, it.localizedMessage) }
                )
        )
    }

    fun approvalStatus(isHost: Boolean, status: String): ApprovalStatusButtonEnum {
        return when(isHost){
            false -> when(status){
                ChattingConstant.REAL_TIME_PENDING -> ApprovalStatusButtonEnum.GUEST_APPLY
                ChattingConstant.REAL_TIME_APPLIED -> ApprovalStatusButtonEnum.GUEST_APPROVE_AWAIT
                ChattingConstant.REAL_TIME_APPROVED -> ApprovalStatusButtonEnum.GUEST_APPROVE_SUCCESS
                ChattingConstant.REAL_TIME_DISAPPROVED -> ApprovalStatusButtonEnum.GUEST_APPROVE_AWAIT
                else -> throw IllegalArgumentException("적절하지 않은 Guest AppliedStatus")
            }
            true  -> when(status){
                ChattingConstant.REAL_TIME_PENDING -> ApprovalStatusButtonEnum.HOST_NONE
                ChattingConstant.REAL_TIME_APPLIED -> ApprovalStatusButtonEnum.HOST_APPROVE
                ChattingConstant.REAL_TIME_APPROVED -> ApprovalStatusButtonEnum.HOST_APPROVE_CANCLE
                ChattingConstant.REAL_TIME_DISAPPROVED -> ApprovalStatusButtonEnum.HOST_APPROVE
                else -> throw IllegalArgumentException("적절하지 않은 Host AppliedStatus")
            }
        }
    }
}
