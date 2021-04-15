package com.yapp.sport_planet.presentation.board

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beust.klaxon.JsonObject
import com.yapp.domain.usecase.board.DeleteBoardUseCase
import com.yapp.domain.usecase.board.GetBoardContentUseCase
import com.yapp.domain.usecase.board.HideBoardUseCase
import com.yapp.domain.usecase.board.ReportBoardUseCase
import com.yapp.domain.usecase.bookmark.CreateBookMarkUseCase
import com.yapp.domain.usecase.bookmark.DeleteBookMarkUseCase
import com.yapp.domain.usecase.chatting.MakeChattingRoomUseCase
import com.yapp.sport_planet.data.vo.ReportRequestVo
import com.yapp.sport_planet.data.vo.board.BoardContentVo
import com.yapp.sport_planet.data.vo.chatting.MakeChattingRoomVo
import com.yapp.sport_planet.data.vo.dtoToVo
import com.yapp.sport_planet.data.vo.voToDto
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.presentation.chatting.EventWrapper
import com.yapp.sport_planet.util.applySchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class BoardViewModel(
    private val makeChattingRoomUseCase: MakeChattingRoomUseCase,
    private val boardContentUseCase: GetBoardContentUseCase,
    private val deleteBookMarkUseCase: DeleteBookMarkUseCase,
    private val createBookMarkUseCase: CreateBookMarkUseCase,
    private val hideBoardUseCase: HideBoardUseCase,
    private val deleteBoardUseCase: DeleteBoardUseCase,
    private val reportBoardUseCase: ReportBoardUseCase
) :
    BaseViewModel() {

    val boardId: MutableLiveData<Long> = MutableLiveData()
    private val _boardId: Long
        get() = boardId.value ?: -1

    private val _hostId = MutableLiveData<Long>()
    val hostId: Long
        get() = _hostId.value ?: -1

    private val _makeChattingRoomResultLiveData =
        MutableLiveData<EventWrapper<MakeChattingRoomVo>>()
    val makeChattingRoomResultLiveData: LiveData<EventWrapper<MakeChattingRoomVo>>
        get() = _makeChattingRoomResultLiveData

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    val boardContent: MutableLiveData<BoardContentVo> = MutableLiveData()
    val showBoardHideView: PublishSubject<Unit> = PublishSubject.create()
    val successFinish: PublishSubject<String> = PublishSubject.create()

    fun makeChattingRoom() {
        val chattingRoomJsonObject = JsonObject()
        chattingRoomJsonObject["hostId"] = _hostId.value
        chattingRoomJsonObject["boardId"] = boardId.value
        compositeDisposable.add(
            makeChattingRoomUseCase.execute(chattingRoomJsonObject)
                .applySchedulers()
                .subscribe(
                    {
                        _makeChattingRoomResultLiveData.postValue(EventWrapper(it.dtoToVo()))
                    }, {
                        Log.d("BoardViewModel", it.localizedMessage)
                    }
                )
        )
    }

    fun getBoardContent() {
        boardContentUseCase.execute(_boardId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                val boardContentVo = it.dtoToVo()
                boardContent.value = boardContentVo
                _hostId.value = boardContentVo.host.hostId
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun bookmarkChange() {
        boardContent.value?.let { boardContent ->
            val api =
                if (boardContent.isBookMark) deleteBookMarkUseCase.execute(boardContent.boardId)
                else createBookMarkUseCase.execute(boardContent.boardId)
            api.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
                .subscribe({
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
        hideBoardUseCase.execute(_boardId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    successFinish.onNext("숨기기 완료")
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun deleteBoard() {
        deleteBoardUseCase.execute(_boardId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    successFinish.onNext("삭제 완료")
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun reportBoard(reportRequestVo : ReportRequestVo) {
        compositeDisposable.add(reportBoardUseCase.execute(reportRequestVo.voToDto())
            .applySchedulers()
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                _isSuccess.value = true
            }, {
                it.printStackTrace()
            })
        )
    }

}

class BoardViewModelFactory(
    private val makeChattingRoomUseCase: MakeChattingRoomUseCase,
    private val boardContentUseCase: GetBoardContentUseCase,
    private val deleteBookMarkUseCase: DeleteBookMarkUseCase,
    private val createBookMarkUseCase: CreateBookMarkUseCase,
    private val hideBoardUseCase: HideBoardUseCase,
    private val deleteBoardUseCase: DeleteBoardUseCase,
    private val reportBoardUseCase: ReportBoardUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BoardViewModel::class.java)) {
            BoardViewModel(
                makeChattingRoomUseCase,
                boardContentUseCase,
                deleteBookMarkUseCase,
                createBookMarkUseCase,
                hideBoardUseCase,
                deleteBoardUseCase,
                reportBoardUseCase
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}