package com.yapp.sport_planet

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.yapp.sport_planet.di.viewModelModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,getString(R.string.kakao_native_app_key))

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    sourceModule,
                    viewModelModule,
                    useCaseModule
                )
            )
        }
    }
}