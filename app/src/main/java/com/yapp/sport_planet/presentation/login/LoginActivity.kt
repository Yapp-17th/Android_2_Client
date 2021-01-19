package com.yapp.sport_planet.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.response.login.LoginResponse
import com.yapp.sport_planet.databinding.ActivityLoginBinding
import com.yapp.sport_planet.presentation.base.BaseActivity
import com.yapp.sport_planet.presentation.chatting.UserInfo
import com.yapp.sport_planet.presentation.main.MainActivity
import com.yapp.sport_planet.presentation.profile.ProfileActivity
import com.yapp.sport_planet.remote.NetworkHelper
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
import com.yapp.sport_planet.util.PrefUtil
import com.yapp.sport_planet.util.applySchedulers
import io.reactivex.rxkotlin.addTo

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        autoLogin()

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                loginErrorCode(error)
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(error.toString(), "사용자 정보 요청 실패")
                    } else {
                        val userToken = token.accessToken
                        val userId = user?.id.toString()
                        val userEmail = user?.kakaoAccount?.email.toString()
                        val userNickname = user?.kakaoAccount?.profile?.nickname.toString()
                        compositeDisposable.add(
                            RemoteDataSourceImpl().postSignIn(
                                LoginResponse(userToken, userEmail, userNickname, userId)
                            ).applySchedulers()
                                .doOnSubscribe { showLoading() }
                                .doAfterTerminate { hideLoading() }
                                .subscribe({
                                    when (it.body()?.status) {
                                        200 -> {
                                            PrefUtil.setStrValue(
                                                this@LoginActivity,
                                                "serverToken",
                                                it.headers()["token"].toString()
                                            )
                                            UserInfo.USER_ID =
                                                it.headers()["userId"].toString().toLong()
                                            NetworkHelper.token =
                                                PrefUtil.getStrValue(this, "serverToken", "")
                                                    .toString()
                                            val intent = Intent(this, MainActivity::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(intent)
                                        }
                                        404, 401 -> {
                                            val intent = Intent(this, ProfileActivity::class.java)
                                            intent.putExtra("userToken", userToken)
                                            intent.putExtra("userId", userId)
                                            intent.putExtra("userEmail", userEmail)
                                            intent.putExtra("userNickname", userNickname)
                                            startActivity(intent)
                                        }
                                    }
                                }, {
                                    Log.e("it2", it.message.toString())
                                })
                        )

                    }

                }
            }
        }

        binding.btLoginKakao.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    private fun autoLogin() {
        Log.d(
            "okhttp", "token : ${
                PrefUtil.getStrValue(this, "serverToken", "")
                    .toString()
            }"
        )
        NetworkHelper.token =
            PrefUtil.getStrValue(this, "serverToken", "")
                .toString()

        RemoteDataSourceImpl().autoLogin()
            .applySchedulers()
            .doOnSubscribe { showLoading() }
            .doAfterTerminate { hideLoading() }
            .subscribe({
                Log.d("okhttp", "response body : ${it.body()}")
                when (it.body()?.status) {
                    200 -> {
                        PrefUtil.setStrValue(
                            this@LoginActivity,
                            "serverToken",
                            it.headers()["token"].toString()
                        )
                        UserInfo.USER_ID =
                            it.headers()["userId"].toString().toLong()
                        NetworkHelper.token =
                            PrefUtil.getStrValue(this, "serverToken", "")
                                .toString()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }, {
                it.printStackTrace()
            }).addTo(compositeDisposable)

    }

    private fun loginErrorCode(error: Throwable) {
        when {
            error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
            }
            error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
            }
            error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
            }
            error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
            }
            error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
            }
            error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
            }
            error.toString() == AuthErrorCause.ServerError.toString() -> {
                Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
            }
            error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
            }
            else -> { // Unknown
                Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}