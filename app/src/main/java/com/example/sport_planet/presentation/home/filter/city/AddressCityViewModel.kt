package com.example.sport_planet.presentation.home.filter.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.data.model.AddressCityModel
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers

class AddressCityViewModel(private val remoteDataSource: RemoteDataSource) :
    BaseViewModel() {
    val items: MutableLiveData<List<AddressCityModel>> = MutableLiveData()

    fun getAddressCity() {
        remoteDataSource.getAddressCity()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.isSuccess()) {
                    val result = it.data.toMutableList()
                    result.add(0, AddressCityModel(id = -1, name = "전체"))
                    items.value = result
                }
            }, {
                it.printStackTrace()
            })
    }
}

class AddressCityViewModelFactory(private val remoteDataSource: RemoteDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddressCityViewModel::class.java)) {
            AddressCityViewModel(remoteDataSource) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}