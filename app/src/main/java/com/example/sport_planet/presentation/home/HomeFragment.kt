package com.example.sport_planet.presentation.home

import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentHomeBinding
import com.example.sport_planet.model.MenuEnum
import com.example.sport_planet.model.SeparatorEnum
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.home.adapter.HomeRecyclerAdapter

class HomeFragment private constructor() :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    companion object {
        fun newInstance() = HomeFragment()
    }

    override val viewModel: HomeViewModel
            by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }


    override fun init() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWriteList()
    }
}