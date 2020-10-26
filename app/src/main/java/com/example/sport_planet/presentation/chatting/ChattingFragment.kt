package com.example.sport_planet.presentation.chatting

import android.content.Intent
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentChattingBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_chatting.*

class ChattingFragment private constructor(): BaseFragment<FragmentChattingBinding,BaseViewModel>(R.layout.fragment_chatting) {
    companion object {
        fun newInstance() = ChattingFragment()
    }

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun init() {

        // ChattingActivity test용 임시 버튼
        bt_test_activity_chatting.setOnClickListener {
            val intent = Intent(activity, ChattingActivity::class.java)
            startActivity(intent)
        }

    }

}