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
import com.example.sport_planet.presentation.chatting.ChattingInfo
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ChattingActivityViewModel : BaseViewModel() {

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private val _ChattingMessageListResponseLiveData = MutableLiveData<ChattingMessageListResponse>()
    val ChattingMessageListResponseLiveData: LiveData<ChattingMessageListResponse>
        get() = _ChattingMessageListResponseLiveData

    private val _ChattingMessageLiveData = MutableLiveData<ChattingMessageResponse>()
    val ChattingMessageLiveData: LiveData<ChattingMessageResponse>
        get() = _ChattingMessageLiveData

    private lateinit var stomp: StompClient
    private lateinit var stompConnection: Disposable
    private lateinit var topic: Disposable

    private var chattingMessageJsonObject = JSONObject()

    fun settingChattingMessageList(chatRoomId: Long){
        compositeDisposable.add(
            remoteDataSourceImpl.getChattingMessageList(chatRoomId)
                .subscribe({
                    it.run {
                        _ChattingMessageListResponseLiveData.postValue(it)
                    }
                },{})
        )
    }

    fun settingStomp() {
        val url = ChattingConstant.URL
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        stomp = StompClient(client, intervalMillis).apply { this@apply.url = url }
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    topic = stomp.join("/sub/chat/room/" + ChattingInfo.CHATROOM_ID).subscribe {
                        stompMessage ->
                        val chattingMessage = Klaxon().parse<ChattingMessageResponse>(stompMessage)
                        _ChattingMessageLiveData.postValue(chattingMessage)
                    }
                }
                Event.Type.CLOSED -> {
                }
                Event.Type.ERROR -> {
                }
            }
        }
    }

    fun sendMessage(chattingMessageContent: String){
        try {
            chattingMessageJsonObject.put("content", chattingMessageContent)
            chattingMessageJsonObject.put("type", ChattingConstant.TALK_TYPE)
            chattingMessageJsonObject.put("senderId", ChattingInfo.USER_ID)
            chattingMessageJsonObject.put("chatRoomId", ChattingInfo.CHATROOM_ID)

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
                    Log.d("테스트-신청", it.toString())
            },{
                    Log.d("테스트-신청", it.toString())
                })
        )
    }

    fun approveBoard(boardId: Long, chatRoomId: Long, guestId: Long){
        val approveBoardObject = JsonObject()
        approveBoardObject.put("chatRoomId", chatRoomId)
        approveBoardObject.put("guestId", guestId)
        compositeDisposable.add(
            remoteDataSourceImpl.approveBoard(boardId, approveBoardObject)
                .subscribe({
                    Log.d("테스트 성공-승인", it.toString()+" "+boardId+" "+chatRoomId+" "+guestId)
                },{
                    Log.d("테스트 성공-실패", it.toString()+" "+boardId+" "+chatRoomId+" "+guestId)
                })
        )
    }
}
