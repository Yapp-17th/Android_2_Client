package com.example.sport_planet.presentation.chatting.viewmodel

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
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class ChattingActivityViewModel : BaseViewModel() {

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private lateinit var chattingMessage: ChattingMessageResponse

    private val _ChattingMessageListResponseLiveData = MutableLiveData<ChattingMessageListResponse>()
    val ChattingMessageListResponseLiveData: LiveData<ChattingMessageListResponse>
        get() = _ChattingMessageListResponseLiveData

    private val _ChattingMessageLiveData = MutableLiveData<ChattingMessageResponse>()
    val ChattingMessageLiveData: LiveData<ChattingMessageResponse>
        get() = _ChattingMessageLiveData

    private val _ApprovalStatusLiveData = MutableLiveData<String>()
    val ApprovalStatusLiveData: LiveData<String>
        get() = _ApprovalStatusLiveData

    private lateinit var stomp: StompClient
    private lateinit var stompConnection: Disposable
    private lateinit var topic: Disposable

    private var chatRoomId by Delegates.notNull<Long>()
    private var chattingMessageJsonObject = JSONObject()

    fun settingChattingMessageList(chatRoomId: Long){
        compositeDisposable.add(
            remoteDataSourceImpl.getChattingMessageList(chatRoomId)
                .subscribe({
                    it.run {
                        _ApprovalStatusLiveData.postValue(it.appliedStatus)
                        _ChattingMessageListResponseLiveData.postValue(it)
                    }
                },{})
        )
    }

    fun makeChattingMessageRead(chatRoomId: Long, messageId: Long){
        compositeDisposable.add(
            remoteDataSourceImpl.makeChattingMessageRead(chatRoomId, messageId)
                .subscribe({
                },{})
        )
    }

    fun settingStomp(chatRoomId: Long, hostId: Long) {
        val url = ChattingConstant.URL
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        this.chatRoomId = chatRoomId

        stomp = StompClient(client, intervalMillis).apply { this@apply.url = url }
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    topic = stomp.join("/sub/chat/room/${this.chatRoomId}").subscribe {
                        stompMessage ->
                        chattingMessage = Klaxon().parse<ChattingMessageResponse>(stompMessage)!!
                        when(chattingMessage.realTimeUpdateType) {
                            "APPLIED" -> _ApprovalStatusLiveData.postValue("APPLIED")
                            "APPROVED" -> _ApprovalStatusLiveData.postValue("APPROVED")
                            "DISAPPROVED" -> _ApprovalStatusLiveData.postValue("APPLIED")
                            else -> {
                                makeChattingMessageRead(this.chatRoomId, chattingMessage.id!!)
                                _ChattingMessageLiveData.postValue(chattingMessage)
                            }
                        }

                    }
                }
                Event.Type.CLOSED -> {
                }
                Event.Type.ERROR -> {
                }
            }
        }
    }

    fun disposeStomp(){
        stompConnection.dispose()
    }

    fun sendMessage(chattingMessageContent: String){
        try {
            chattingMessageJsonObject.put("content", chattingMessageContent)
            chattingMessageJsonObject.put("type", ChattingConstant.TALK_TYPE)
            chattingMessageJsonObject.put("senderId", UserInfo.USER_ID)
            chattingMessageJsonObject.put("chatRoomId", this.chatRoomId)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        stomp.send("/pub/v1/chat/message", chattingMessageJsonObject.toString()).subscribe()
    }

    fun applyBoard(boardId: Long, chatRoomId: Long){
        val applyBoardObject = JsonObject()
        applyBoardObject.put("chatRoomId", chatRoomId)
        compositeDisposable.add(
            remoteDataSourceImpl.applyBoard(boardId, applyBoardObject)
                .subscribe({
                    Log.d("신청 성공: ", it.toString())
            },{
                    Log.d("신청 실패: ", it.toString())
                })
        )
    }

    fun approveBoard(boardId: Long, chatRoomId: Long, guestId: Long){
        val approveBoardObject = JsonObject()
        approveBoardObject["chatRoomId"] = chatRoomId
        approveBoardObject["guestId"] = guestId
        compositeDisposable.add(
            remoteDataSourceImpl.approveBoard(boardId, approveBoardObject)
                .subscribe({
                    Log.d("승인 성공: ", it.toString())
                },{
                    Log.d("승인 실패: ", it.toString())
                })
        )
    }

    fun disapproveBoard(boardId: Long, chatRoomId: Long, guestId: Long){
        val disapproveBoardObject = JsonObject()
        disapproveBoardObject["chatRoomId"] = chatRoomId
        disapproveBoardObject["guestId"] = guestId
        compositeDisposable.add(
            remoteDataSourceImpl.disapproveBoard(boardId, disapproveBoardObject)
                .subscribe({
                    Log.d("취소 성공: ", it.toString())
                },{
                    Log.d("취소 실패: ", it.toString())
                })
        )
    }
}
