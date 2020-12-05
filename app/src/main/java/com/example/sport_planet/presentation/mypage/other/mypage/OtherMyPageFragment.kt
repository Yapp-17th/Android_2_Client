package com.example.sport_planet.presentation.mypage.other.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentOtherMypageBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.mypage.MyPageExerciseListAdapter
import com.example.sport_planet.presentation.mypage.other.history.OtherHistoryFragment

class OtherMyPageFragment :
    BaseFragment<FragmentOtherMypageBinding, OtherMyPageViewModel>(R.layout.fragment_other_mypage) {

    private val myPageExerciseListAdapter = MyPageExerciseListAdapter()

    override val viewModel: OtherMyPageViewModel by lazy {
        ViewModelProvider(this).get(OtherMyPageViewModel::class.java)
    }

    override fun init() {
        binding.vm = viewModel
        binding.rvContent.adapter = myPageExerciseListAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userId = arguments?.getLong("userId", 0L)
        userId?.let { viewModel.getMyProfile(it) }
        observeLiveData()
        binding.tvHistory.setOnClickListener { moveFragment(OtherHistoryFragment.instance(userId)) }
    }

    private fun observeLiveData() {
        viewModel.historyResponse.observe(viewLifecycleOwner, Observer {
            myPageExerciseListAdapter.setItem(it.data.category)
        })
    }

    private fun moveFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame, fragment)
            .commit()
    }


    companion object {
        fun newInstance(userId: Long) = OtherMyPageFragment().apply {
            arguments = Bundle().apply {
                putLong("userId", userId)
            }
        }
    }

}