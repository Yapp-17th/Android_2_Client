package com.example.sport_planet.presentation.mypage.history.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.data.model.MyViewHistoryModel
import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.response.EvaluateListResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers

class FinishTabViewModel : BaseViewModel() {
    private val _myViewHistoryList = MutableLiveData<List<MyViewHistoryModel>>()
    val myViewHistoryList: LiveData<List<MyViewHistoryModel>> get() = _myViewHistoryList

    private val _applyList = MutableLiveData<List<EvaluateListResponse.EvaluateListModel>>()
    val applyList: LiveData<List<EvaluateListResponse.EvaluateListModel>> get() = _applyList

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> get() = _isSuccess

    fun getHistory() {
        compositeDisposable.add(
            RemoteDataSourceImpl()
                .getMyViewHistory("complete")
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
                .getEvaluateList(boardId)
                .applySchedulers()
                .subscribe({
                    if (it.success) {
                        _applyList.value = it.data
                    }
                }, {})
        )
    }

    fun postReport(evaluateReportRequest: EvaluateReportRequest) {
        compositeDisposable.add(
            RemoteDataSourceImpl()
                .postEvaluateReport(evaluateReportRequest)
                .applySchedulers()
                .subscribe({
                    _isSuccess.value = true
                }, {
                    it.printStackTrace()
                })
        )
    }

}