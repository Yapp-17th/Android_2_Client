package com.example.sport_planet.presentation.chatting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl

class ChattingFragmentViewModel : BaseViewModel(){

    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private val _ChattingRoomListResponseLiveData = MutableLiveData<ChattingRoomListResponse>()
    val ChattingRoomListResponseLiveData: LiveData<ChattingRoomListResponse>
        get() = _ChattingRoomListResponseLiveData

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