package com.yapp.sport_planet.presentation.home.filter

import androidx.lifecycle.MutableLiveData
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.presentation.base.BaseViewModel

class FilterViewModel : BaseViewModel() {
    val city: MutableLiveData<List<RegionResponse.Data>> = MutableLiveData()
    val exercise: MutableLiveData<List<ExerciseResponse.Data>> = MutableLiveData()
}