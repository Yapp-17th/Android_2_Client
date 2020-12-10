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
import com.example.sport_planet.presentation.mypage.scrap.ScrapFragment
import com.example.sport_planet.presentation.mypage.setting.SettingFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

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