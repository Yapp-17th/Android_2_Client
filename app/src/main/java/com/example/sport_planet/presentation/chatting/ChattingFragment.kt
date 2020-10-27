package com.example.sport_planet.presentation.chatting

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentChattingBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.adapter.ChattingRoomAdapter
import com.example.sport_planet.presentation.chatting.viewmodel.ChattingFragmentViewModel
import kotlinx.android.synthetic.main.fragment_chatting.*

class ChattingFragment private constructor(): BaseFragment<FragmentChattingBinding,BaseViewModel>(R.layout.fragment_chatting) {
    companion object {
        fun newInstance() = ChattingFragment()
    }

    override val viewModel: ChattingFragmentViewModel
        by lazy { ViewModelProvider(this).get(ChattingFragmentViewModel::class.java) }

    private val chattingRoomAdapter = ChattingRoomAdapter()

    override fun init() {
        binding.vm = viewModel

        rv_fragment_chatting_recyclerview.run{
            adapter = chattingRoomAdapter
            layoutManager = LinearLayoutManager(this@ChattingFragment.context)
            setHasFixedSize(true)
        }

        viewModel.settingChattingRoomList()
        viewModel.ChattingRoomListResponseLiveData.observe(this,
            Observer {
                for(chattingRoom in it.data!!){
                    chattingRoomAdapter.addChattingRoom(chattingRoom)
                }
            }
        )

        // ChattingActivity test용 임시 버튼
        bt_test_activity_chatting.setOnClickListener {
            val intent = Intent(activity, ChattingActivity::class.java)
            startActivity(intent)
        }
    }

}