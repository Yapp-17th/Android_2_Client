package com.yapp.sport_planet.presentation.mypage.scrap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.sport_planet.data.model.mypage.MyBookMarksModel
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
import com.yapp.sport_planet.util.applySchedulers

class ScrapViewModel : BaseViewModel() {

    private val _bookMarkList = MutableLiveData<List<MyBookMarksModel>>()
    val bookMarkList: LiveData<List<MyBookMarksModel>> get() = _bookMarkList

    fun getBookMark() {
        compositeDisposable.add(
            RemoteDataSourceImpl()
                .getBookMarks()
                .applySchedulers()
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
                .subscribe({
                    if (it.success && !it.data.isNullOrEmpty()) {
                        _bookMarkList.value = it.data.filter { myBookMarksModel ->
                            myBookMarksModel.hostId != -1L
                        }
                    }

                }, {})
        )
    }

    fun deleteBookMark(boardId: Long) {
        compositeDisposable.add(RemoteDataSourceImpl()
            .deleteBookMark(boardId)
            .applySchedulers()
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    _bookMarkList.value = _bookMarkList.value?.filter { myBookMarkModel ->
                        myBookMarkModel.boardId != boardId
                    }
                }
            }, {})
        )
    }
}