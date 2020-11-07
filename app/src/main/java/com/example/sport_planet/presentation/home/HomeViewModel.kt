package com.example.sport_planet.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class HomeViewModel(private val remote: RemoteDataSource) :
    BaseViewModel() {

    val writeList: MutableLiveData<List<String>> = MutableLiveData()

    fun getWriteList() {
        remote.getBoardList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
            .addTo(compositeDisposable)
        writeList.value = (1..100).shuffled().take(10).map { it.toString() }
    }
}

class HomeViewModelFactory(private val remote: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(remote) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}