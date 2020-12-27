package com.yapp.sport_planet.presentation.mypage.setting

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.FragmentSettingBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.login.LoginActivity
import com.yapp.sport_planet.util.PrefUtil
import com.kakao.sdk.user.UserApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class SettingFragment :
    BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    override val viewModel: SettingViewModel by lazy {
        ViewModelProvider(this).get(SettingViewModel::class.java)
    }

    override fun init() {
        binding.run {
            customToolBar.run {
                title.text = getString(R.string.fragment_my_page_setting)
                back.setOnClickListener {
                    onBackPressed()
                }
            }
            tvLogout.setOnClickListener { logout() }
            tvWithdrawal.setOnClickListener { showLogoutPopup() }
            tvVersion.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("market://details?id=" + requireContext().packageName)
                startActivity(intent)
            }
        }
        observeLiveData()
    }

    private fun logout() {
        PrefUtil.setStrValue(requireContext(), "serverToken", "")
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun observeLiveData() {
        viewModel.run {
            isDeleteSuccess.observe(viewLifecycleOwner, Observer {
                if (it) {
                    logout()
                }
            })
            isLoading.observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (it) showLoading() else hideLoading() }
                .addTo(compositeDisposable)
        }
    }


    private fun showLogoutPopup() {
        val dialog = LogoutDialog.newInstance()
        dialog.setLogoutDialogListener(object : LogoutDialog.LogoutDialogListener {
            override fun onAccept() {
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e("Logout", "연결 끊기 실패", error)
                    } else {
                        viewModel.deleteUser()
                    }
                }
            }
        })
        dialog.show(parentFragmentManager, "")
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}