package com.example.sport_planet.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivityMainBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.chatting.view.ChattingFragment
import com.example.sport_planet.presentation.home.HomeFragment
import com.example.sport_planet.presentation.mypage.MyPageFragment
import com.example.sport_planet.presentation.write.WriteActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_home -> bottomNavigationReplaceFragment(HomeFragment.newInstance())
                R.id.main_write -> {
                    WriteActivity.createInstance(
                        activity = this
                    )
                    false
                }
                R.id.main_chatting -> bottomNavigationReplaceFragment(ChattingFragment.newInstance())
                R.id.main_mypage -> bottomNavigationReplaceFragment(MyPageFragment.newInstance())
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.main_home
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun bottomNavigationReplaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(binding.frame.id, fragment).commit()
        return true
    }
}