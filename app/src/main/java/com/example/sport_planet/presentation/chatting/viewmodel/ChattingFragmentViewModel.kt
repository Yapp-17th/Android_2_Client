package com.example.sport_planet.presentation.chatting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.JsonObject
import com.example.sport_planet.data.response.ChattingMessageResponse
import com.example.sport_planet.data.response.ChattingRoomListResponse
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
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ChattingFragmentViewModel : BaseViewModel(){

    private val TAG = "ChattingFragmentViewModel"

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private lateinit var chattingMessage: ChattingMessageResponse

    private val _chattingMessageLiveData = MutableLiveData<ChattingMessageResponse>()
    val chattingMessageLiveData: LiveData<ChattingMessageResponse>
        get() = _chattingMessageLiveData

    private val _chattingRoomListResponseLiveData = MutableLiveData<ChattingRoomListResponse>()
    val chattingRoomListResponseLiveData: LiveData<ChattingRoomListResponse>
        get() = _chattingRoomListResponseLiveData

    private lateinit var stompClient: StompClient
    private lateinit var stompConnection: Disposable
    private lateinit var topic: Disposable
    private var closeSocket = false

    fun makeChattingRoom(){
        val chattingRoomJsonObject = JsonObject()
        chattingRoomJsonObject.put("hostId",  1)
        chattingRoomJsonObject.put("boardId", 1)
        compositeDisposable.add(
            remoteDataSourceImpl.makeChattingRoom(chattingRoomJsonObject)
                .applySchedulers()
                .subscribe(
                    {
                        when(it.status){
                            201 -> Log.d(TAG, "성공 " + it.toString())
                            200 -> Log.d(TAG, "실패 " + it.toString())
                        }
                    },{

                    }
                )
        )
    }

    fun settingChattingRoomList(){
        compositeDisposable.add(
            remoteDataSourceImpl.getChattingRoomList()
                .applySchedulers()
                .subscribe(
                    {
                        _chattingRoomListResponseLiveData.postValue(it)
                    },{}
                )
        )
    }

    @OptIn(UnstableDefault::class)
    fun initSocket() {
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
                        Log.d(TAG, "[OPENED]: 채팅방 목록 웹소켓 OPEN")
                        topic = stompClient.join("/sub/user/${UserInfo.USER_ID}/chat/room")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe ({ stompMessage ->
                                Log.d(TAG, stompMessage)
                                chattingMessage = Json.parse(ChattingMessageResponse.serializer(), stompMessage)
                                when(chattingMessage.realTimeUpdateType) {
                                    "MESSAGE_READ" -> {
                                        _chattingMessageLiveData.postValue(chattingMessage)
                                    }
                                }
                            }, {
                                Log.d(TAG, it.localizedMessage)
                            })

                    }
                    Event.Type.CLOSED -> {
                        if(!closeSocket)
                            Log.d(TAG, "[CLOSED]: 채팅방 목록 웹소켓 비정상적인 CLOSE")
                        else
                            Log.d(TAG, "[CLOSED]: 채팅방 목록 웹소켓 정상적인 CLOSE")
                    }
                    Event.Type.ERROR -> {
                        Log.d(TAG, "[ERROR]: 채팅방 목록 웹소켓 ERROR")
                    }
                }
            },{
                Log.d(TAG, it.localizedMessage) }
            )
    }

    fun disconnectSocket(){
        closeSocket = true
        stompConnection.dispose()
    }
}