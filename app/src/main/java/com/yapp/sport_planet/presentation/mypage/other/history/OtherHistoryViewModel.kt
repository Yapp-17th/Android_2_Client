package com.yapp.sport_planet.presentation.mypage.other.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.sport_planet.data.model.OtherHistoryModel
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
import com.yapp.sport_planet.util.applySchedulers

class OtherHistoryViewModel : BaseViewModel() {

    private val _otherHistoryModel = MutableLiveData<List<OtherHistoryModel>>()
    val otherHistoryModel: LiveData<List<OtherHistoryModel>> get() = _otherHistoryModel

    fun getOtherHistory(userId: Long) {
        compositeDisposable.add(
            RemoteDataSourceImpl().getOthersHistory(userId)
                .applySchedulers()
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
                .subscribe({
                    if (it.success)
                        _otherHistoryModel.value = it.data
                }, {
                })
        )
    }
}