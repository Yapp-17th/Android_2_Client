package com.example.sport_planet.presentation.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentMypageBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.mypage.editProfile.EditProfileFragment
import com.example.sport_planet.presentation.mypage.history.view.HistoryActivity
import com.example.sport_planet.presentation.mypage.setting.SettingFragment

class MyPageFragment : BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {
    companion object {
        fun newInstance() = MyPageFragment()
    }

    private val myPageExerciseListAdapter = MyPageExerciseListAdapter()

    override val viewModel: MyPageViewModel by lazy {
        ViewModelProvider(this).get(MyPageViewModel::class.java)
    }

    override fun init() {
        binding.vm = viewModel
        viewModel.getMyProfile()
        binding.rvContent.adapter = myPageExerciseListAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
        binding.tvEditProfile.setOnClickListener {
            moveFragment(EditProfileFragment.newInstance())
        }
        binding.tvHistory.setOnClickListener {
            val intent = Intent(context, HistoryActivity::class.java)
            startActivity(intent)
        }
        binding.tvBookmark.setOnClickListener {
            // TODO: 2020-11-13 스크랩 페이지로 이동
        }
        binding.tvSetting.setOnClickListener {
            moveFragment(SettingFragment.newInstance())
        }
    }

    private fun moveFragment(fragment : Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame, fragment)
            .commit()
    }

    private fun observeLiveData() {
        viewModel.category.observe(viewLifecycleOwner, Observer {
            myPageExerciseListAdapter.setItem(it)
        })
    }

}