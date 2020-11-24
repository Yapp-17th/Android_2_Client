package com.example.sport_planet.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers

class MyPageViewModel : BaseViewModel() {

    val userName = MutableLiveData<String>()
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
                .subscribe({
                    if (it.success) {
                        userName.value = it.data.userName
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