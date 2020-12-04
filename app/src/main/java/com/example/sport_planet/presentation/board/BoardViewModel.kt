package com.example.sport_planet.presentation.board

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beust.klaxon.JsonObject
import com.example.sport_planet.data.model.BoardContentModel
import com.example.sport_planet.data.response.chatting.MakeChattingRoomResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.EventWrapper
import com.example.sport_planet.remote.RemoteDataSource
import com.example.sport_planet.util.applySchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class BoardViewModel(private val remote: RemoteDataSource) :
    BaseViewModel() {

    val boardId: MutableLiveData<Long> = MutableLiveData()
    private val _boardId: Long
        get() = boardId.value ?: -1

    private val _hostId = MutableLiveData<Long>()
    val hostId: Long
        get() = _hostId.value ?: -1

    private val _makeChattingRoomResultLiveData = MutableLiveData<EventWrapper<MakeChattingRoomResponse>>()
    val makeChattingRoomResultLiveData: LiveData<EventWrapper<MakeChattingRoomResponse>>
        get() = _makeChattingRoomResultLiveData

    val boardContent: MutableLiveData<BoardContentModel> = MutableLiveData()

    fun makeChattingRoom(){
        val chattingRoomJsonObject = JsonObject()
        chattingRoomJsonObject["hostId"] = _hostId.value
        chattingRoomJsonObject["boardId"] = boardId.value
        compositeDisposable.add(
            remote.makeChattingRoom(chattingRoomJsonObject)
                .applySchedulers()
                .subscribe(
                    {
                       _makeChattingRoomResultLiveData.postValue(EventWrapper(it))
                    },{
                        Log.d("BoardViewModel", it.localizedMessage)
                    }
                )
        )
    }

    fun getBoardContent() {
        remote.getBoardContent(_boardId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    boardContent.value = it.data
                    _hostId.value = it.data.host.hostId.toLong()
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun bookmarkChange() {
        boardContent.value?.let { boardContent ->
            val api =
                if (boardContent.isBookMark) remote.deleteBookMark(boardContent.boardId)
                else remote.createBookMark(boardContent.boardId)
            api.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
                .subscribe({
                    Log.d("okhttp", "bookmarkChange : $it")
                    if (it.success) {
                        getBoardContent()
                    }
                }, {
                    it.printStackTrace()
                })
                .addTo(compositeDisposable)
        }
    }

    fun hideBoard() {
        remote.hideBoard(_boardId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {

                } else {

                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }
}

class BoardViewModelFactory(private val remote: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BoardViewModel::class.java)) {
            BoardViewModel(remote) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}