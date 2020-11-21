package com.example.sport_planet.presentation.chatting.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.databinding.FragmentChattingBinding
import com.example.sport_planet.data.response.ChattingRoomListResponse
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.adapter.ChattingRoomAdapter
import com.example.sport_planet.presentation.chatting.viewmodel.ChattingFragmentViewModel
import kotlinx.android.synthetic.main.fragment_chatting.*

class ChattingFragment private constructor(): BaseFragment<FragmentChattingBinding,BaseViewModel>(R.layout.fragment_chatting) {
    companion object {
        fun newInstance() =
            ChattingFragment()
    }

    private var chattingRoomsHashMap = HashMap<Long, ChattingRoomListResponse.Data>()

    override val viewModel: ChattingFragmentViewModel
        by lazy { ViewModelProvider(this).get(ChattingFragmentViewModel::class.java) }

    private lateinit var chattingRoomAdapter: ChattingRoomAdapter

    override fun init() {
        chattingRoomAdapter = ChattingRoomAdapter(requireContext())
        chattingRoomAdapter.setHasStableIds(true)

        binding.vm = viewModel

        activity?.runOnUiThread {
            binding.toolbarFragmentChatting.run {
                this.setSeparator(SeparatorEnum.NONE)
                this.setBackButtonVisible(false)
                this.setTitle("채팅")
            }
        }

        rv_fragment_chatting_recyclerview.run{
            activity?.runOnUiThread {
                adapter = chattingRoomAdapter
                layoutManager = LinearLayoutManager(this@ChattingFragment.context)
                setHasFixedSize(true)
            }
        }

        bt_fragment_test.setOnClickListener {
            viewModel.makeChattingRoom()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.settingChattingRoomList()

        viewModel.chattingRoomListResponseLiveData.observe(this,
            Observer {
                for(chattingRoom in it.data){
                    chattingRoomsHashMap[chattingRoom.id] = chattingRoom
                }
                chattingRoomAdapter.settingChattingRoomList(chattingRoomsHashMap)
                if(chattingRoomAdapter.itemCount == 0){
                    iv_chatting_fragment_nothing.visibility = View.VISIBLE
                    tv_chatting_fragment_nothing.visibility = View.VISIBLE
                }
            }
        )

        viewModel.initSocket()

        viewModel.chattingMessageLiveData.observe(this,
            Observer {
                if(chattingRoomsHashMap[it.chatRoomId] != null){
                    chattingRoomAdapter.updateChattingRoomList(it.chatRoomId!!, it)
                }
                else {
                    viewModel.settingChattingRoomList()
                }
            }
        )
    }

    override fun onDestroy() {
        viewModel.disconnectSocket()
        super.onDestroy()
    }

}