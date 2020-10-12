package com.example.sport_planet.presentation.chatting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentChattingBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel

class ChattingFragment private constructor(): BaseFragment<FragmentChattingBinding,BaseViewModel>(R.layout.fragment_chatting) {
    companion object {
        fun newInstance() = ChattingFragment()
    }

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun init() {
    }

}