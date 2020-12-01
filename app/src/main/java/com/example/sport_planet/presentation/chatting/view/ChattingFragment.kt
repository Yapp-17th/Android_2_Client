package com.example.sport_planet.presentation.chatting.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.databinding.FragmentChattingBinding
import com.example.sport_planet.data.response.chatting.ChattingRoomListResponse
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.presentation.chatting.ChattingConstant
import com.example.sport_planet.presentation.chatting.adapter.ChattingRoomAdapter
import com.example.sport_planet.presentation.chatting.viewmodel.ChattingFragmentViewModel
import com.example.sport_planet.presentation.custom.CustomDialog
import kotlinx.android.synthetic.main.fragment_chatting.*

class ChattingFragment private constructor(): BaseFragment<FragmentChattingBinding,BaseViewModel>(R.layout.fragment_chatting) {
    companion object {
        fun newInstance() =
            ChattingFragment()
    }

    private lateinit var chattingRoomsHashMap : HashMap<Long, ChattingRoomListResponse.Data>

    override val viewModel: ChattingFragmentViewModel
        by lazy { ViewModelProvider(this).get(ChattingFragmentViewModel::class.java) }

    private lateinit var chattingRoomAdapter: ChattingRoomAdapter

    override fun init() {
        chattingRoomAdapter = ChattingRoomAdapter(
            requireContext()
        ) { chattingRoomItem -> leaveChattingRoomDialog(chattingRoomItem)}
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

    }

    override fun onStart() {
        super.onStart()

        viewModel.settingChattingRoomList()

        viewModel.chattingRoomListResponseLiveData.observe(this,
            Observer {
                chattingRoomsHashMap = HashMap()
                for(chattingRoom in it.data){
                    chattingRoomsHashMap[chattingRoom.id] = chattingRoom
                }
                chattingRoomAdapter.settingChattingRoomList(chattingRoomsHashMap)
                if(chattingRoomAdapter.itemCount == 0){
                    iv_chatting_fragment_nothing.visibility = View.VISIBLE
                    tv_chatting_fragment_nothing.visibility = View.VISIBLE
                }
                else {
                    iv_chatting_fragment_nothing.visibility = View.INVISIBLE
                    tv_chatting_fragment_nothing.visibility = View.INVISIBLE
                }
            }
        )

        viewModel.initSocket()

        viewModel.chattingMessageLiveData.observe(this,
            Observer {
                if(chattingRoomsHashMap[it.chatRoomId] != null){
                    when(it.realTimeUpdateType){
                        ChattingConstant.REAL_TIME_MESSAGE_READ -> chattingRoomAdapter.updateChattingRoomList(it.chatRoomId!!, it)
                    }
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

    private fun leaveChattingRoomDialog(chattingRoomItem: ChattingRoomListResponse.Data) {
        val dialog = CustomDialog.CustomDialogBuilder()
            .setContent(getString(R.string.dialog_common_content2))
            .setOKText(getString(R.string.dialog_common_ok2))
            .setOnOkClickedListener{
                chattingRoomsHashMap.remove(chattingRoomItem.id)
                chattingRoomAdapter.leaveChattingRoom(chattingRoomItem.id)
                viewModel.leaveChattingRoom(chattingRoomItem.id)
            }.create()
        dialog.show(parentFragmentManager, dialog.tag)
    }

}