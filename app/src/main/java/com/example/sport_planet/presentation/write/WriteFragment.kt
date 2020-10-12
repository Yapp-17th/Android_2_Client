package com.example.sport_planet.presentation.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentWriteBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel

class WriteFragment private constructor():BaseFragment<FragmentWriteBinding,BaseViewModel>(R.layout.fragment_write) {
    companion object {
        fun newInstance() = WriteFragment()
    }

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun init() {
    }
}