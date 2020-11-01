package com.example.sport_planet.presentation.home

import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.presentation.base.BaseViewModel

class HomeViewModel :
    BaseViewModel() {

    val writeList: MutableLiveData<List<String>> = MutableLiveData()

    fun getWriteList() {
        writeList.value = (1..100).shuffled().take(10).map { it.toString() }
    }
}