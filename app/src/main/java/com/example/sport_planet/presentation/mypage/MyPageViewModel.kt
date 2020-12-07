package com.example.sport_planet.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.NetworkHelper
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.PrefUtil
import com.example.sport_planet.util.applySchedulers

class MyPageViewModel : BaseViewModel() {

    val nickName = MutableLiveData<String>()
    val like = MutableLiveData<String>()
    val dislike = MutableLiveData<String>()
    val intro = MutableLiveData<String>()
    val city = MutableLiveData<String>()

    private val _category = MutableLiveData<List<String>>()
    val category: LiveData<List<String>> get() = _category

    fun getMyProfile() {
        compositeDisposable.add(
            RemoteDataSourceImpl().getMyProfile()
                .applySchedulers()
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
                .subscribe({
                    if (it.success) {
                        nickName.value = it.data.nickName
                        like.value = it.data.like.toString()
                        dislike.value = it.data.dislike.toString()
                        intro.value = it.data.intro
                        _category.value = it.data.category
                        city.value = it.data.city
                    }
                }, {})
        )
    }

}