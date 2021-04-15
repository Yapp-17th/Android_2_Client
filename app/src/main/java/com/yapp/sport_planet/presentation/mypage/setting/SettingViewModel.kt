package com.yapp.sport_planet.presentation.mypage.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.data.remote.RemoteDataSourceImpl

class SettingViewModel : BaseViewModel() {

    private val _isDeleteSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess: LiveData<Boolean> get() = _isDeleteSuccess

    fun deleteUser() {
        compositeDisposable.add(
            RemoteDataSourceImpl().deleteUser()
                .applySchedulers()
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
                .subscribe({
                    if (it.success) {
                        _isDeleteSuccess.value = true
                    }
                }, {})
        )
    }
}