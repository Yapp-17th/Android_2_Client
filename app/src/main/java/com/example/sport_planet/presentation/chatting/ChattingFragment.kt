package com.example.sport_planet.presentation.chatting

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

    override fun init() {
        val chattingRoomAdapter = ChattingRoomAdapter(requireContext())

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
    }

}