package com.example.sport_planet.presentation.write

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.data.model.UserTagModel
import com.example.sport_planet.data.response.basic.ExerciseResponse
import com.example.sport_planet.data.response.basic.RegionResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class WriteViewModel(private val remote: RemoteDataSource) : BaseViewModel() {
    val exercise: MutableLiveData<ExerciseResponse.Data> = MutableLiveData()
    val city: MutableLiveData<RegionResponse.Data> = MutableLiveData()
    val userTag: MutableLiveData<UserTagModel> = MutableLiveData()
    val date: MutableLiveData<String> = MutableLiveData()
    val time: MutableLiveData<String> = MutableLiveData()
    val place: MutableLiveData<String> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val body: MutableLiveData<String> = MutableLiveData()
    val count: MutableLiveData<Int> = MutableLiveData()

    val showFinishView: PublishSubject<Unit> = PublishSubject.create()
    val showPostError: PublishSubject<Unit> = PublishSubject.create()

    fun postBoard() {
        if (exercise.value == null ||
            city.value == null ||
            userTag.value == null ||
            date.value == null ||
            time.value == null ||
            place.value == null ||
            title.value == null ||
            body.value == null ||
            count.value == null
        ) {
            showPostError.onNext(Unit)
            return
        }

        remote.postBoard(
            title = title.value!!,
            content = body.value!!,
            category = exercise.value!!.id,
            city = city.value!!.id,
            userTag = userTag.value!!.id,
            recruitNumber = count.value!!,
            date = getDate(),
            place = place.value!!
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    showFinishView.onNext(Unit)
                } else {

                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun clearDateAndTime() {
        date.value = null
        time.value = null
    }

    fun getDate(): String {
        return date.value!! + time.value!!
    }
}

class WriteViewModelFactory(private val remote: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteViewModel::class.java)) {
            return WriteViewModel(remote) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}