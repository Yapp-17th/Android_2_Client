package com.example.sport_planet.presentation.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentMypageBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel

class MyPageFragment private constructor() : BaseFragment<FragmentMypageBinding,BaseViewModel>(R.layout.fragment_mypage) {
    companion object {
        fun newInstance() = MyPageFragment()
    }

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun init() {
        TODO("Not yet implemented")
    }

}