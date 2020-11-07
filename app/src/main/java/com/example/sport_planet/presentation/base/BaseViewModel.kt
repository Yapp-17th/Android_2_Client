package com.example.sport_planet.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val isLoading = MutableLiveData<Boolean>(false)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}