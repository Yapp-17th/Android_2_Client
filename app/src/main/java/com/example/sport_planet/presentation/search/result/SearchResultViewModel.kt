package com.example.sport_planet.presentation.search.result

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.data.enums.TimeFilterEnum
import com.example.sport_planet.data.model.BoardModel
import com.example.sport_planet.data.model.toBoardModel
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class SearchResultViewModel(
    private val remote: RemoteDataSource
) : BaseViewModel() {

    val boardList: MutableLiveData<List<BoardModel>> = MutableLiveData(emptyList())
    val pageCount: MutableLiveData<Int> = MutableLiveData(0)
    val city: MutableLiveData<String> = MutableLiveData()
    val exercise: MutableLiveData<String> = MutableLiveData()
    private val timeFilter: MutableLiveData<TimeFilterEnum> =
        MutableLiveData(TimeFilterEnum.TIME_LATEST)
    private var _boardList: ArrayList<BoardModel> = ArrayList()

    val showRecyclerViewRefresh: PublishSubject<Boolean> = PublishSubject.create()

    fun getBoardList() {
        remote.getBoardList(
            size = 20,
            page = pageCount.value ?: 0,
            category = exercise.value ?: "0",
            address = city.value ?: "0",
            sorting = timeFilter.value?.query ?: TimeFilterEnum.TIME_LATEST.query
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                Log.d("okhttp", "getWriteList : $it")
                if (it.success) {
                    if (it.data.isEmpty()) subPageCount()
                    _boardList.addAll(it.data)
                    boardList.value = _boardList
                }
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
                Log.d("okhttp", "bookmarkChange : $it")
                if (it.success) {
                    changeBoardListItem(item)
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun changeTimeFilter(timeFilterEnum: TimeFilterEnum) {
        timeFilter.value = timeFilterEnum
        pageCount.value = 0
        _boardList.clear()
        getBoardList()
    }

    fun addPageCount() {
        pageCount.value = (pageCount.value ?: 0) + 1
        getBoardList()
    }

    fun subPageCount() {
        val count = pageCount.value ?: 0
        if (count <= 0) return
        pageCount.value = count - 1
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
                    showRecyclerViewRefresh.onNext(true)
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }
}