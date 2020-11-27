package com.example.sport_planet.presentation.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.data.model.BoardModel
import com.example.sport_planet.data.model.toBoardModel
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class HomeViewModel(private val remote: RemoteDataSource) :
    BaseViewModel() {

    val boardList: MutableLiveData<List<BoardModel>> = MutableLiveData()
    val pageCount: MutableLiveData<Int> = MutableLiveData(0)
    private var _boardList: ArrayList<BoardModel> = ArrayList()

    val showRecyclerViewRefresh: PublishSubject<Boolean> = PublishSubject.create()

    fun getBoardList(page: Int = 0) {
        remote.getBoardList(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("okhttp", "getWriteList : $it")
                if (it.success) {
                    _boardList = ArrayList(it.data)
                    boardList.value = _boardList
                } else {

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
            .subscribe({
                Log.d("okhttp", "bookmarkChange : $it")
                if (it.success) {
                    changeBoardListItem(item)
                } else {

                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun addBoardNextPage() {

    }

    private fun changeBoardListItem(oldBoard: BoardModel) {
        remote.getBoardContent(oldBoard.boardId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("okhttp", "BoardContentItem : $it")
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

class HomeViewModelFactory(private val remote: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(remote) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}