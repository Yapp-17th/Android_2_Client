package com.example.sport_planet.presentation.mypage.other.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.data.model.OtherHistoryModel
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers

class OtherHistoryViewModel : BaseViewModel() {

    private val _otherHistoryModel = MutableLiveData<List<OtherHistoryModel>>()
    val otherHistoryModel : LiveData<List<OtherHistoryModel>> get() = _otherHistoryModel

    fun getOtherHistory(userId: Long) {
        compositeDisposable.add(
            RemoteDataSourceImpl().getOthersHistory(userId)
                .applySchedulers()
                .subscribe({
                    if (it.success)
                        _otherHistoryModel.value = it.data
                }, {})
        )
    }
}