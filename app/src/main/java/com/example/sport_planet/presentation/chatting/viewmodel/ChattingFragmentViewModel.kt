package com.example.sport_planet.presentation.chatting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Json
import com.beust.klaxon.JsonObject
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.ChattingInfo
import com.example.sport_planet.remote.RemoteDataSourceImpl
import okhttp3.OkHttpClient

class ChattingFragmentViewModel : BaseViewModel(){

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private val _ChattingRoomListResponseLiveData = MutableLiveData<ChattingRoomListResponse>()
    val ChattingRoomListResponseLiveData: LiveData<ChattingRoomListResponse>
        get() = _ChattingRoomListResponseLiveData

    fun makeChattingRoom(){
        val chattingRoomJsonObject = JsonObject()
        chattingRoomJsonObject.put("hostId", 1)
        chattingRoomJsonObject.put("boardId", 1)
        compositeDisposable.add(
            remoteDataSourceImpl.makeChattingRoom(chattingRoomJsonObject)
                .subscribe(
                    {
                        if (it.status == 201)
                            Log.d("테스트", "성공 " + it.toString())
                        else if (it.status == 200)
                            Log.d("테스트", "실패 " + it.toString())

                    },{

                    }
                )
        )
    }

    fun settingChattingRoomList(){
        compositeDisposable.add(
            remoteDataSourceImpl.getChattingRoomList()
                .subscribe(
                    {
                        _ChattingRoomListResponseLiveData.postValue(it)
                    },{}
                )
        )
    }
}