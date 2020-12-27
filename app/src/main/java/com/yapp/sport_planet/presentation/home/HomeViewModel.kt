package com.yapp.sport_planet.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.data.enums.TimeFilterEnum
import com.yapp.sport_planet.data.model.BoardModel
import com.yapp.sport_planet.data.model.board.BoardRequestModel
import com.yapp.sport_planet.data.model.toBoardModel
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class HomeViewModel(private val remote: RemoteDataSource) :
    BaseViewModel() {

    val boardList: MutableLiveData<ArrayList<BoardModel>> = MutableLiveData(ArrayList())
    private var _boardList: ArrayList<BoardModel>
        get() = boardList.value!!
        set(value) {
            boardList.value = value
        }

    private val boardRequestItem: MutableLiveData<BoardRequestModel> = MutableLiveData(BoardRequestModel())
    private val _boardRequestItem: BoardRequestModel
        get() = boardRequestItem.value!!

    var exercise: List<ExerciseResponse.Data>
        get() = _boardRequestItem.category
        set(value) {
            boardRequestItem.value = _boardRequestItem.copy(category = value)
        }

    var city: List<RegionResponse.Data>
        get() = _boardRequestItem.address
        set(value) {
            boardRequestItem.value = _boardRequestItem.copy(address = value)
        }

    var time: TimeFilterEnum
        get() = _boardRequestItem.sorting
        set(value) {
            boardRequestItem.value = _boardRequestItem.copy(sorting = value)
        }

    val showRecyclerViewRefresh: PublishSubject<Boolean> = PublishSubject.create()
    val showDataIsEmpty: PublishSubject<Boolean> = PublishSubject.create()

    val showSearchActivity: PublishSubject<Unit> = PublishSubject.create()
    fun requestSearchActivity() {
        showSearchActivity.onNext(Unit)
    }

    val showCityExerciseFilterActivity: PublishSubject<Unit> = PublishSubject.create()
    fun requestCityExerciseFilterActivity() {
        showCityExerciseFilterActivity.onNext(Unit)
    }

    val showTimeFilterPopup: PublishSubject<Unit> = PublishSubject.create()
    fun requestTimeFilterPopup() {
        showTimeFilterPopup.onNext(Unit)
    }

    fun getBoardList() {
        remote.getBoardList(
            category = _boardRequestItem.category.map { it.id }.joinToString(","),
            address = _boardRequestItem.address.map { it.id }.joinToString(","),
            sorting = _boardRequestItem.sorting.query
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    boardList.value = ArrayList(it.data)
                } else {
                    boardList.value = ArrayList()
                }
                showDataIsEmpty.onNext(_boardList.isEmpty())
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun bookmarkChange(item: BoardModel) {
        val api =
            if (item.isBookMark) remote.deleteBookMark(item.boardId) else remote.createBookMark(item.boardId)
        api.observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    changeBoardListItem(item)
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    private fun changeBoardListItem(oldBoard: BoardModel) {
        remote.getBoardContent(oldBoard.boardId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    val newBoard = it.data.toBoardModel()
                    _boardList[_boardList.indexOf(oldBoard)] = newBoard
                    boardList.value = _boardList
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }
}

class HomeViewModelFactory(private val remote: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(remote) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}