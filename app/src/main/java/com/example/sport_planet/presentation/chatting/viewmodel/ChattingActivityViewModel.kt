package com.example.sport_planet.presentation.chatting.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.sport_planet.model.ChattingMessageListResponse
import com.example.sport_planet.model.ChattingMessageResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.ChattingConstant
import com.example.sport_planet.presentation.chatting.UserInfo
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class ChattingActivityViewModel : BaseViewModel() {

    private val TAG = "ChattingActivityViewModel"

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private lateinit var chattingMessage: ChattingMessageResponse

    private val _chattingMessageListResponseLiveData = MutableLiveData<ChattingMessageListResponse>()
    val chattingMessageListResponseLiveData: LiveData<ChattingMessageListResponse>
        get() = _chattingMessageListResponseLiveData

    private val _chattingMessageLiveData = MutableLiveData<ChattingMessageResponse>()
    val chattingMessageLiveData: LiveData<ChattingMessageResponse>
        get() = _chattingMessageLiveData

    private val _approvalStatusLiveData = MutableLiveData<String>()
    val approvalStatusLiveData: LiveData<String>
        get() = _approvalStatusLiveData

    private lateinit var stompClient: StompClient
    private lateinit var stompConnection: Disposable
    private lateinit var topic: Disposable
    private var closeSocket = false

    private var chatRoomId by Delegates.notNull<Long>()
    private var chattingMessageJsonObject = JSONObject()

    fun settingChattingMessageList(chatRoomId: Long){
        compositeDisposable.add(
            remoteDataSourceImpl.getChattingMessageList(chatRoomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        _approvalStatusLiveData.postValue(it.appliedStatus)
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
                .subscribe({
                    Log.d(TAG, it.message)
                },{
                    Log.d(TAG, it.localizedMessage)}
                )
        )
    }

    fun initSocket(chatRoomId: Long) {
        val url = ChattingConstant.URL
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        this.chatRoomId = chatRoomId

        stompClient = StompClient(client, intervalMillis).apply { this@apply.url = url }

        stompConnection = stompClient.connect()
            .subscribe ({
                when (it.type) {
                    Event.Type.OPENED -> {
                        Log.d(TAG, "[OPENED]: 웹소켓 OPEN")
                        topic = stompClient.join("/sub/chat/room/${this.chatRoomId}")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe ({ stompMessage ->
                                chattingMessage = Klaxon().parse<ChattingMessageResponse>(stompMessage)!!
                                when(chattingMessage.realTimeUpdateType) {
                                    "MESSAGE_READ" -> {
                                        makeChattingMessageRead(this.chatRoomId, chattingMessage.id!!)
                                        _chattingMessageLiveData.postValue(chattingMessage)
                                    }
                                    else -> {
                                        _approvalStatusLiveData.postValue(chattingMessage.realTimeUpdateType)
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
            chattingMessageJsonObject.put("type", ChattingConstant.TALK_TYPE)
            chattingMessageJsonObject.put("senderId", UserInfo.USER_ID)
            chattingMessageJsonObject.put("chatRoomId", this.chatRoomId)

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

    fun applyBoard(boardId: Long, chatRoomId: Long){
        val applyBoardObject = JsonObject()
        applyBoardObject.put("chatRoomId", chatRoomId)
        compositeDisposable.add(
            remoteDataSourceImpl.applyBoard(boardId, applyBoardObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, it.message)
                },{
                    Log.d(TAG, it.localizedMessage)}
                )
        )
    }

    fun approveBoard(boardId: Long, chatRoomId: Long, guestId: Long){
        val approveBoardObject = JsonObject()
        approveBoardObject["chatRoomId"] = chatRoomId
        approveBoardObject["guestId"] = guestId
        compositeDisposable.add(
            remoteDataSourceImpl.approveBoard(boardId, approveBoardObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, it.message)
                },{
                    Log.d(TAG, it.localizedMessage)}
                )
        )
    }

    fun disapproveBoard(boardId: Long, chatRoomId: Long, guestId: Long){
        val disapproveBoardObject = JsonObject()
        disapproveBoardObject["chatRoomId"] = chatRoomId
        disapproveBoardObject["guestId"] = guestId
        compositeDisposable.add(
            remoteDataSourceImpl.disapproveBoard(boardId, disapproveBoardObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, it.message)
                },{
                    Log.d(TAG, it.localizedMessage) }
                )
        )
    }
}
