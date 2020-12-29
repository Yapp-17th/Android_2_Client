package com.yapp.sport_planet.presentation.home.filter.exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class ExerciseViewModel(private val remoteDataSource: RemoteDataSource) :
    BaseViewModel() {
    val items: MutableLiveData<List<ExerciseResponse.Data>> = MutableLiveData()
    val selectedItems: MutableLiveData<List<ExerciseResponse.Data>> = MutableLiveData()

    val showErrorToast: PublishSubject<Unit> = PublishSubject.create()

    fun getAddressCity() {
        remoteDataSource.getExercise()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.isSuccess()) {
                    val result = it.data.toMutableList()
                    result.add(0, ExerciseResponse.Data(id = 0, name = "전체"))
                    items.value = result
                }
            }, {
                it.printStackTrace()
            })
    }

    fun clickItems(item: ExerciseResponse.Data) {
        val list: MutableList<ExerciseResponse.Data> =
            ArrayList(this.selectedItems.value ?: emptyList())

        val defaultItem = ExerciseResponse.Data(0, "전체")

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

class ExerciseViewModelFactory(private val remoteDataSource: RemoteDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            ExerciseViewModel(remoteDataSource) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}