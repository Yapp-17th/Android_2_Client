package com.yapp.sport_planet.presentation.home.filter.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class AddressCityViewModel(private val remoteDataSource: RemoteDataSource) :
    BaseViewModel() {
    val items: MutableLiveData<List<RegionResponse.Data>> = MutableLiveData()
    val selectedItems: MutableLiveData<List<RegionResponse.Data>> = MutableLiveData()
    val showErrorToast: PublishSubject<Unit> = PublishSubject.create()

    fun getAddressCity() {
        remoteDataSource.getRegion()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.isSuccess()) {
                    val result = it.data.toMutableList()
                    result.add(0, RegionResponse.Data(id = 0, name = "전체"))
                    items.value = result
                }
            }, {
                it.printStackTrace()
            })
    }

    fun clickItems(item: RegionResponse.Data) {
        val list: MutableList<RegionResponse.Data> =
            ArrayList(this.selectedItems.value ?: emptyList())
        val defaultItem = RegionResponse.Data(0, "전체")

        if (item == defaultItem) {
            selectedItems.value = listOf(item)
            return
        } else {
            list.remove(defaultItem)
        }

        if (!list.contains(item) && list.size >= 3) {
            showErrorToast.onNext(Unit)
        } else {
            if (list.contains(item)) list.remove(item) else list.add(item)
            selectedItems.value = if (list.isEmpty()) arrayListOf(defaultItem) else list
        }
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