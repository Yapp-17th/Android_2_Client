package com.example.sport_planet.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivitySplashBinding
import com.example.sport_planet.presentation.base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    companion object {
        private const val SPLASH_DELAY_TIME: Long = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }, SPLASH_DELAY_TIME)
    }
}