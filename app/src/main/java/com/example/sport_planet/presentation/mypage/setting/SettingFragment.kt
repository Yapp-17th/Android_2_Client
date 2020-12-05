package com.example.sport_planet.presentation.mypage.setting

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.util.PrefUtil
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentSettingBinding
import com.example.sport_planet.presentation.base.BaseAcceptCancelDialog
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.login.LoginActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class SettingFragment :
    BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    override val viewModel: SettingViewModel by lazy {
        ViewModelProvider(this).get(SettingViewModel::class.java)
    }

    override fun init() {
        binding.customToolBar.title.text = getString(R.string.fragment_my_page_setting)
        binding.customToolBar.back.setOnClickListener {
            onBackPressed()
        }
        binding.tvLogout.setOnClickListener { showLogoutPopup() }
        viewModel.isDeleteSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                PrefUtil.setStrValue(requireContext(),"serverToken","")
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        })
    }


    private fun showLogoutPopup() {
        val dialog = BaseAcceptCancelDialog.newInstance(
            dialogTitleText = getString(R.string.dialog_logout_title),
            dialogBodyText = getString(R.string.dialog_logout_body),
            dialogAcceptText = getString(R.string.dialog_logout_ok),
            dialogWidthRatio = 0.911111f
        )
        dialog.setAcceptCancelDialogListener(object :
            BaseAcceptCancelDialog.AcceptCancelDialogListener {
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