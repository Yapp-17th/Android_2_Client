package com.example.sport_planet.presentation.mypage.history

import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentIngTabBinding
import com.example.sport_planet.presentation.base.BaseFragment

class IngTabFragment :
    BaseFragment<FragmentIngTabBinding, IngTabViewModel>(R.layout.fragment_ing_tab) {
    override val viewModel: IngTabViewModel by lazy {
        ViewModelProvider(this).get(IngTabViewModel::class.java)
    }

    override fun init() {

    }

    companion object {
        fun newInstance() = IngTabFragment()
    }
}