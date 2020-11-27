package com.example.sport_planet.presentation.mypage.scrap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.data.model.mypage.MyBookMarksModel
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers

class ScrapViewModel : BaseViewModel() {

    private val _bookMarkList = MutableLiveData<List<MyBookMarksModel>>()
    val bookMarkList : LiveData<List<MyBookMarksModel>> get() = _bookMarkList

    fun getBookMark() {
        compositeDisposable.add(RemoteDataSourceImpl()
            .getBookMarks()
            .applySchedulers()
            .subscribe ({
                if(it.success && !it.data.isNullOrEmpty()){
                    _bookMarkList.value = it.data
                }

        },{}))
    }

    fun deleteBookMark(boardId : Long){
//        compositeDisposable.add(RemoteDataSourceImpl()
//            .deleteBookMark(boardId)
//            .applySchedulers()
//            .subscribe())
    }
}