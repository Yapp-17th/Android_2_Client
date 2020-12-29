package com.yapp.sport_planet.presentation.mypage.other.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.FragmentOtherMypageBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.mypage.MyPageExerciseListAdapter
import com.yapp.sport_planet.presentation.mypage.other.history.OtherHistoryFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class OtherMyPageFragment :
    BaseFragment<FragmentOtherMypageBinding, OtherMyPageViewModel>(R.layout.fragment_other_mypage) {

    private val myPageExerciseListAdapter = MyPageExerciseListAdapter()

    override val viewModel: OtherMyPageViewModel by lazy {
        ViewModelProvider(this).get(OtherMyPageViewModel::class.java)
    }

    override fun init() {
        binding.run {
            vm = viewModel
            rvContent.adapter = myPageExerciseListAdapter
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userId = arguments?.getLong("userId", 0L)
        userId?.let { viewModel.getMyProfile(it) }
        observeLiveData()
        binding.tvHistory.setOnClickListener {
            if (userId != null) {
                moveFragment(OtherHistoryFragment.instance(userId.toLong()))
            }
        }
    }

    private fun observeLiveData() {
        viewModel.run {
            historyResponse.observe(viewLifecycleOwner, Observer {
                myPageExerciseListAdapter.setItem(it.data.category)
            })
            isLoading.observeOn(AndroidSchedulers.mainThread())
                .subscribe { if(it) showLoading() else hideLoading() }
                .addTo(compositeDisposable)
        }
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