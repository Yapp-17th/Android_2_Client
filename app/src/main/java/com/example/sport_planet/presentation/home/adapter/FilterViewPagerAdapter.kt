package com.example.sport_planet.presentation.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sport_planet.presentation.home.filter.city.AddressCityFragment
import com.example.sport_planet.presentation.home.filter.exercise.ExerciseFragment

class FilterViewPagerAdapter(private val fragmentManager: FragmentManager, private val lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()

    init {
        fragmentList.clear()
        fragmentList.add(AddressCityFragment.newInstance())
        fragmentList.add(ExerciseFragment.newInstance())
    }

    fun resetFragment() {
        fragmentManager.fragments.forEach {
            when(it) {
                is AddressCityFragment -> it.adapterClear()
                is ExerciseFragment -> it.adapterClear()
            }
        }
    }

    fun getCity(): String {
        return (fragmentList[0] as? AddressCityFragment)?.getCity() ?: ""
    }

    fun getExercise(): String {
        return (fragmentList[1] as? ExerciseFragment)?.getExercise() ?: ""
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}