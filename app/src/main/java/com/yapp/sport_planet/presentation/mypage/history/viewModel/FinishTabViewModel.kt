package com.yapp.sport_planet.presentation.mypage.history.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.sport_planet.data.model.mypage.EvaluateListModel
import com.yapp.sport_planet.data.model.mypage.MyViewHistoryModel
import com.yapp.sport_planet.data.request.EvaluateReportRequest
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
import com.yapp.sport_planet.util.applySchedulers

class FinishTabViewModel : BaseViewModel() {
    private val _myViewHistoryList = MutableLiveData<List<MyViewHistoryModel>>()
    val myViewHistoryList: LiveData<List<MyViewHistoryModel>> get() = _myViewHistoryList

    private val _applyList = MutableLiveData<List<EvaluateListModel>>()
    val applyList: LiveData<List<EvaluateListModel>> get() = _applyList

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> get() = _isSuccess

    fun getHistory() {
        compositeDisposable.add(
            RemoteDataSourceImpl()
                .getMyViewHistory("complete")
                .applySchedulers()
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
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
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
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