package com.example.sport_planet.presentation.home.filter.exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.data.model.AddressCityModel
import com.example.sport_planet.data.model.ExerciseModel
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers

class ExerciseViewModel(private val remoteDataSource: RemoteDataSource) :
    BaseViewModel() {
    val items: MutableLiveData<List<ExerciseModel>> = MutableLiveData()

    fun getAddressCity() {
        remoteDataSource.getExercise()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.isSuccess()) {
                    val result = it.data.toMutableList()
                    result.add(0, ExerciseModel(id = -1, name = "전체"))
                    items.value = result
                }
            }, {
                it.printStackTrace()
            })
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