package com.yapp.sport_planet.presentation.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.FragmentMypageBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.mypage.editProfile.EditProfileFragment
import com.yapp.sport_planet.presentation.mypage.history.view.HistoryActivity
import com.yapp.sport_planet.presentation.mypage.scrap.ScrapFragment
import com.yapp.sport_planet.presentation.mypage.setting.SettingFragment

class MyPageFragment :
    BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {
    companion object {
        fun newInstance() = MyPageFragment()
    }

    private val myPageExerciseListAdapter = MyPageExerciseListAdapter()

    override val viewModel: MyPageViewModel by lazy {
        ViewModelProvider(this).get(MyPageViewModel::class.java)
    }

    override fun init() {
        viewModel.getMyProfile()
        binding.run {
            vm = viewModel
            rvContent.adapter = myPageExerciseListAdapter
            tvEditProfile.setOnClickListener {
                moveFragment(EditProfileFragment.newInstance())
            }
            tvHistory.setOnClickListener {
                val intent = Intent(context, HistoryActivity::class.java)
                startActivity(intent)
            }
            tvBookmark.setOnClickListener {
                moveFragment(ScrapFragment.newInstance())
            }
            tvSetting.setOnClickListener {
                moveFragment(SettingFragment.newInstance())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
    }

    private fun moveFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame, fragment)
            .commit()
    }

    private fun observeLiveData() {
        viewModel.run {
            category.observe(viewLifecycleOwner, Observer {
                myPageExerciseListAdapter.setItem(it)
            })
            
        }
    }

}