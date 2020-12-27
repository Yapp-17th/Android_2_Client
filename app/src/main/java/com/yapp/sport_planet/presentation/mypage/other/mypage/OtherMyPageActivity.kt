package com.yapp.sport_planet.presentation.mypage.other.mypage

import android.os.Bundle
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ActivityOtherMypageBinding
import com.yapp.sport_planet.presentation.base.BaseActivity

class OtherMyPageActivity :
    BaseActivity<ActivityOtherMypageBinding>(R.layout.activity_other_mypage) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getLongExtra("userId",0L)
        supportFragmentManager.beginTransaction().replace(binding.frame.id,
            OtherMyPageFragment.newInstance(userId)
        ).commit()
    }
}