package com.example.sport_planet.presentation.mypage.history.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.data.model.mypage.ApplyListModel
import com.example.sport_planet.data.model.mypage.MyViewHistoryModel
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers

class IngTabViewModel : BaseViewModel() {

    private val _myViewHistoryList = MutableLiveData<List<MyViewHistoryModel>>()
    val myViewHistoryList: LiveData<List<MyViewHistoryModel>> get() = _myViewHistoryList

    private val _applyList = MutableLiveData<List<ApplyListModel>>()
    val applyList : LiveData<List<ApplyListModel>> get() = _applyList

    fun getHistory() {
        compositeDisposable.add(
            RemoteDataSourceImpl()
                .getMyViewHistory("continue")
                .applySchedulers()
                .subscribe({
                    if (it.success) {
                        _myViewHistoryList.value = it.data
                    }
                }, {})
        )
    }

    fun getApplyList(boardId: Long) {
        compositeDisposable.add(
            RemoteDataSourceImpl()
                .getApplyList(boardId)
                .applySchedulers()
                .subscribe({
                    if(it.success)
                        _applyList.value = it.data
            }, {})
        )
    }

}