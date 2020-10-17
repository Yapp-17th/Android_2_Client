package com.example.sport_planet.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentHomeBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.home.adapter.HomeRecyclerAdapter

class HomeFragment private constructor() :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    companion object {
        fun newInstance() = HomeFragment()
    }

    override val viewModel: HomeViewModel
            by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }


    override fun init() {
        binding.vm = viewModel
        binding.rec.adapter = HomeRecyclerAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWriteList()
    }
}