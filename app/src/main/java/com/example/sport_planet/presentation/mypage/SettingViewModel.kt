package com.example.sport_planet.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers

class SettingViewModel : BaseViewModel() {

    private val _isDeleteSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess: LiveData<Boolean> get() = _isDeleteSuccess

    fun deleteUser() {
        compositeDisposable.add(
            RemoteDataSourceImpl().deleteUser()
                .applySchedulers()
                .subscribe({
                    if (it.success) {
                        _isDeleteSuccess.value = true
                    }
                }, {})
        )
    }

}