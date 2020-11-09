package com.example.sport_planet.presentation.chatting

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentChattingBinding
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.adapter.ChattingRoomAdapter
import com.example.sport_planet.presentation.chatting.viewmodel.ChattingFragmentViewModel
import kotlinx.android.synthetic.main.fragment_chatting.*
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class ChattingFragment private constructor(): BaseFragment<FragmentChattingBinding,BaseViewModel>(R.layout.fragment_chatting) {
    companion object {
        fun newInstance() = ChattingFragment()
    }

    override val viewModel: ChattingFragmentViewModel
        by lazy { ViewModelProvider(this).get(ChattingFragmentViewModel::class.java) }

    private lateinit var chattingRoomAdapter: ChattingRoomAdapter

    override fun init() {
        chattingRoomAdapter = ChattingRoomAdapter(requireContext())

        binding.vm = viewModel

        activity?.runOnUiThread {
            binding.toolbarFragmentChatting.run {
                this.back.visibility = View.GONE
                this.title.text = "  채팅"
                this.title.textSize = 20F
            }
        }

        rv_fragment_chatting_recyclerview.run{
            adapter = chattingRoomAdapter
            layoutManager = LinearLayoutManager(this@ChattingFragment.context)
            setHasFixedSize(true)
        }

        bt_fragment_test.setOnClickListener {
            viewModel.makeChattingRoom()
        }
    }

    override fun onStart() {
        super.onStart()
        settingChattingRoomList()
    }

    fun settingChattingRoomList(){
        viewModel.settingChattingRoomList()
        viewModel.ChattingRoomListResponseLiveData.observe(this,
            Observer {
                chattingRoomAdapter.settingChattingRoomList(it.data as ArrayList<ChattingRoomListResponse.Data>)
            }
        )
    }

}