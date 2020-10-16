package com.example.sport_planet.presentation.chatting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Klaxon
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.model.ChattingMessage
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ChattingViewModel : BaseViewModel() {

    private val _ChattingMessageLiveData = MutableLiveData<ChattingMessage>()
    val ChattingMessageLiveData: LiveData<ChattingMessage>
        get() = _ChattingMessageLiveData

    val chattingInfo = ChattingInfo

    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable

    private lateinit var stomp: StompClient

    var chattingMessageJsonObject = JSONObject()

    fun settingStomp() {
        val url = chattingInfo.URL
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
                    topic = stomp.join("/sub/chat/room/" + chattingInfo.CHATROOM_ID).subscribe { stompMessage ->
                        val chattingMessage = Klaxon()
                                .parse<ChattingMessage>(stompMessage)
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
            chattingMessageJsonObject.put("chat_room_id", chattingInfo.CHATROOM_ID)
            chattingMessageJsonObject.put("sender", chattingInfo.SENDER)
            chattingMessageJsonObject.put("content", chattingMessageContent)
            chattingMessageJsonObject.put("type", "TALK")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        stomp.send("/pub/chat/message", chattingMessageJsonObject.toString()).subscribe()

    }
}
