package com.yapp.sport_planet.presentation.chatting.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.sport_planet.data.response.chatting.ChattingMessageResponse
import com.yapp.sport_planet.data.response.chatting.ChattingRoomListResponse
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.presentation.chatting.ChattingConstant
import com.yapp.sport_planet.presentation.chatting.UserInfo
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
import com.yapp.sport_planet.util.applySchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

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

    private lateinit var mStompClient: StompClient
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

    @SuppressLint("CheckResult")
    @OptIn(UnstableDefault::class)
    fun initSocket() {
        val url = ChattingConstant.URL

        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        mStompClient.lifecycle()
            .subscribe { lifecycleEvent: LifecycleEvent ->
                when (lifecycleEvent.type!!) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.d(TAG, "[OPENED] 채탕방 목록 웹소켓 OPENED")
                        topic = mStompClient.topic("/sub/user/${UserInfo.USER_ID}/chat/room")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe ({ stompMessage ->
                                chattingMessage = Json.parse(ChattingMessageResponse.serializer(), stompMessage.payload)
                                _chattingMessageLiveData.postValue(chattingMessage)
                            }, {
                                Log.d(TAG, it.localizedMessage)
                            })
                    }
                    LifecycleEvent.Type.ERROR -> Log.d(TAG, "[ERROR]: 채팅방 목록 웹소켓 ERROR")
                    LifecycleEvent.Type.CLOSED -> {
                        if(!closeSocket)
                            Log.d(TAG, "[CLOSED]: 채팅방 목록 웹소켓 비정상적인 CLOSE")
                        else
                            Log.d(TAG, "[CLOSED]: 채팅방 목록 웹소켓 정상적인 CLOSE")
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> Log.d(TAG, "[CLOSED]: 채팅방 목록 웹소켓 SERVER HEARTBEAT FAILED")
                }
            }
        mStompClient.connect()
    }

    fun disconnectSocket(){
        closeSocket = true
        mStompClient.disconnect()
    }
}