package com.example.sport_planet.presentation.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivityMainBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.chatting.ChattingFragment
import com.example.sport_planet.presentation.home.HomeFragment
import com.example.sport_planet.presentation.mypage.MyPageFragment
import com.example.sport_planet.presentation.write.WriteFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_home -> bottomNavigationReplaceFragment(HomeFragment.newInstance())
                R.id.main_write -> bottomNavigationReplaceFragment(WriteFragment.newInstance())
                R.id.main_chatting -> bottomNavigationReplaceFragment(ChattingFragment.newInstance())
                R.id.main_mypage -> bottomNavigationReplaceFragment(MyPageFragment.newInstance())
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.main_home
    }

    private fun bottomNavigationReplaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(binding.frame.id, fragment).commit()
        return true
    }
}