package com.yapp.sport_planet.presentation.login

import android.app.Application
import com.yapp.sport_planet.R
import com.kakao.sdk.common.KakaoSdk
import io.reactivex.plugins.RxJavaPlugins

class GlobalApplication :Application(){
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,getString(R.string.kakao_native_app_key))

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
    }
}