package com.yapp.sport_planet.presentation.write.select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.data.model.CommonApiModel
import com.yapp.sport_planet.data.model.toCommon
import com.yapp.sport_planet.data.response.basic.toCommon
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class SelectViewModel(
    private val remoteDataSource: RemoteDataSource
) : BaseViewModel() {

    val items: MutableLiveData<List<CommonApiModel>> = MutableLiveData()

    fun getCity() {
        remoteDataSource.getRegion()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccess()) {
                    items.value = response.data.map { it.toCommon() }
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun getExercise() {
        remoteDataSource.getExercise()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccess()) {
                    if (response.isSuccess()) {
                        items.value = response.data.map { it.toCommon() }
                    }
                }
            }, {
                it.printStackTrace()
            })
    }

    fun getUserTag() {
        remoteDataSource.getUserTag()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccess()) {
                    items.value = response.data.map { it.toCommon() }
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }
}

class SelectViewModelFactory(private val remoteDataSource: RemoteDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SelectViewModel::class.java)) {
            SelectViewModel(remoteDataSource) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}