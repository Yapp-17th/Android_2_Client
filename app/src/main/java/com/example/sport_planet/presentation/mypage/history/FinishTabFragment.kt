package com.example.sport_planet.presentation.mypage.history

import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentFinishTabBinding
import com.example.sport_planet.presentation.base.BaseFragment

class FinishTabFragment : BaseFragment<FragmentFinishTabBinding,FinishTabViewModel>(R.layout.fragment_finish_tab){
    override val viewModel: FinishTabViewModel by lazy {
        ViewModelProvider(this).get(FinishTabViewModel::class.java)
    }

    override fun init() {
    }

    companion object{
        fun newInstance() = FinishTabFragment()
    }
}