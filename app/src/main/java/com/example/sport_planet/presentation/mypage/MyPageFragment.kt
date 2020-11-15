package com.example.sport_planet.presentation.mypage

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentMypageBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel

class MyPageFragment private constructor() :
    BaseFragment<FragmentMypageBinding, BaseViewModel>(R.layout.fragment_mypage) {
    companion object {
        fun newInstance() = MyPageFragment()
    }

    private val myPageExerciseListAdapter = MyPageExerciseListAdapter()
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    private val viewModel2: MyPageViewModel by lazy {
        ViewModelProvider(this).get(MyPageViewModel::class.java)
    }

    override fun init() {
        binding.vm = viewModel2
        viewModel2.getMyProfile()
        binding.rvContent.adapter = myPageExerciseListAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
        binding.tvEditProfile.setOnClickListener {
            // TODO: 2020-11-13 프로필 수정 페이지로 이동
        }
        binding.tvHistory.setOnClickListener {
            // TODO: 2020-11-13 히스토리 페이지로 이동
        }
        binding.tvBookmark.setOnClickListener {
            // TODO: 2020-11-13 스크랩 페이지로 이동
        }
        binding.tvSetting.setOnClickListener {
            // TODO: 2020-11-13 설정 페이지로 이동
        }
    }

    private fun observeLiveData() {
        viewModel2.category.observe(viewLifecycleOwner, Observer {
            myPageExerciseListAdapter.setItem(it)
        })
    }

}