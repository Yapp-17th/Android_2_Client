package com.example.sport_planet.presentation.chatting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.data.response.chatting.ChattingMessageResponse
import com.example.sport_planet.data.response.chatting.ChattingRoomListResponse
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

    fun leaveChattingRoom(chatRoomId: Long){
        compositeDisposable.add(
            remoteDataSourceImpl.leaveChattingRoom(chatRoomId)
                .applySchedulers()
                .subscribe(
                    {},{}
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
                                chattingMessage = Json.parse(ChattingMessageResponse.serializer(), stompMessage)
                                _chattingMessageLiveData.postValue(chattingMessage)
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