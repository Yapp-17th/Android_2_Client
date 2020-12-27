package com.yapp.sport_planet.presentation.splash

import android.content.Intent
import android.os.Bundle
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ActivityIntroBinding
import com.yapp.sport_planet.presentation.base.BaseActivity
import com.yapp.sport_planet.presentation.login.LoginActivity

class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btIntroStart.setOnClickListener {
            getSharedPreferences(SPLASH, MODE_PRIVATE).edit().putBoolean(SPLASH, true).apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val SPLASH = "SPLASH"
    }
}