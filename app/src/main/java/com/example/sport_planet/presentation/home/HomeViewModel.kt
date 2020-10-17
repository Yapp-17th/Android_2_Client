package com.example.sport_planet.presentation.home

import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.presentation.base.BaseViewModel

interface HomeViewModelType {
    interface Inputs {
        fun getWriteList()
    }

    interface Outputs {
        val writeList: MutableLiveData<List<String>>
    }
}

class HomeViewModel :
    BaseViewModel(),
    HomeViewModelType.Inputs,
    HomeViewModelType.Outputs {

    //Output
    override val writeList: MutableLiveData<List<String>> = MutableLiveData()

    //Input
    override fun getWriteList() {
        writeList.value = (1..100).shuffled().take(10).map { it.toString() }
    }
}