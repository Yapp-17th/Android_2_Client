package com.example.sport_planet.presentation.mypage

import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentSettingBinding
import com.example.sport_planet.presentation.base.BaseFragment
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
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}