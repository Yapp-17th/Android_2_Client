package com.yapp.sport_planet.presentation.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.presentation.home.filter.city.AddressCityFragment
import com.yapp.sport_planet.presentation.home.filter.exercise.ExerciseFragment

class FilterViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val city: List<RegionResponse.Data> = emptyList(),
    private val exercise: List<ExerciseResponse.Data> = emptyList(),
    private val lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()

    init {
        fragmentList.clear()
        fragmentList.add(AddressCityFragment.newInstance(city = city))
        fragmentList.add(ExerciseFragment.newInstance(exercise = exercise))
    }

    fun resetFragment() {
        fragmentManager.fragments.forEach {
            when (it) {
                is AddressCityFragment -> it.clearCity()
                is ExerciseFragment -> it.clearExercise()
            }
        }
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}