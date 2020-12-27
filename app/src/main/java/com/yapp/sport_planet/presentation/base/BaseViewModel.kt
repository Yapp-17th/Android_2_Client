package com.yapp.sport_planet.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}