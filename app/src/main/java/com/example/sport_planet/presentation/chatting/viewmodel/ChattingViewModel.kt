package com.example.sport_planet.presentation.chatting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Klaxon
import com.example.sport_planet.model.ChattingMessageResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.ChattingInfo
import com.example.sport_planet.presentation.chatting.model.ChattingMessage
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ChattingViewModel : BaseViewModel() {

    private val chattingInfo = ChattingInfo

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private val _ChattingMessageResponseLiveData = MutableLiveData<ChattingMessageResponse>()
    val ChattingMessageResponseLiveData: LiveData<ChattingMessageResponse>
        get() = _ChattingMessageResponseLiveData // TODO: 변수 네이밍 고민하

    private val _ChattingMessageLiveData = MutableLiveData<ChattingMessage>()
    val ChattingMessageLiveData: LiveData<ChattingMessage>
        get() = _ChattingMessageLiveData

    private lateinit var stomp: StompClient
    private lateinit var stompConnection: Disposable
    private lateinit var topic: Disposable

    private var chattingMessageJsonObject = JSONObject()

    fun settingChattingMessage(){
     compositeDisposable.add(
         remoteDataSourceImpl.getChattingMessage()
             .subscribe({
                 it.run {
                     _ChattingMessageResponseLiveData.postValue(it)
                 }
             },{})
     )
    }

    fun settingStomp() {
        val url = ChattingInfo.URL
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
                        val chattingMessage = Klaxon().parse<ChattingMessage>(stompMessage)
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
            chattingMessageJsonObject.put("type", ChattingInfo.MESSAGE_TYPE_TALK)
            chattingMessageJsonObject.put("senderId", ChattingInfo.SENDER_ID)
            chattingMessageJsonObject.put("chatRoomId", ChattingInfo.CHATROOM_ID)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        stomp.send("/pub/v1/chat/message", chattingMessageJsonObject.toString()).subscribe()
    }
}
