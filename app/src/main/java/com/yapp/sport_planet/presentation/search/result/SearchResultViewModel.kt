package com.yapp.sport_planet.presentation.search.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.data.model.BoardModel
import com.yapp.sport_planet.data.model.toBoardModel
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class SearchResultViewModel(
    private val remote: RemoteDataSource
) : BaseViewModel() {

    val keyword: MutableLiveData<String> = MutableLiveData()

    val boardList: MutableLiveData<ArrayList<BoardModel>> = MutableLiveData(ArrayList())
    private var _boardList: ArrayList<BoardModel>
        get() = boardList.value!!
        set(value) {
            boardList.value = value
        }

    fun getSearchList() {
        keyword.value?.let { keyword ->
            remote.getSearchList(keyword.split(" ").joinToString(","))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
                .subscribe({
                    if (it.success) {
                        boardList.value = ArrayList(it.data)
                    } else {
                        boardList.value = ArrayList()
                    }
                }, {
                    it.printStackTrace()
                })
                .addTo(compositeDisposable)
        }
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

class SearchResultViewModelFactory(val remote: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            SearchResultViewModel(remote) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}