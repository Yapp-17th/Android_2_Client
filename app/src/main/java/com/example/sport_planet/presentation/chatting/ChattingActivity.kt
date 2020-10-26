package com.example.sport_planet.presentation.chatting

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivityChattingBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.chatting.adapter.ChattingAdapter
import com.example.sport_planet.presentation.chatting.viewmodel.ChattingViewModel
import kotlinx.android.synthetic.main.activity_chatting.*

class ChattingActivity : BaseActivity<ActivityChattingBinding>(R.layout.activity_chatting) {

    private val chattingAdapter = ChattingAdapter(this)
    private val chattingViewModel: ChattingViewModel
         by lazy {
             ViewModelProvider(this).get(ChattingViewModel::class.java)
         }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rv_activity_chatting_recyclerview.run {
            adapter = chattingAdapter
            layoutManager = LinearLayoutManager(this@ChattingActivity)
            setHasFixedSize(true)
        }

        chattingViewModel.settingChattingMessage()
        chattingViewModel.ChattingMessageResponseLiveData.observe(this,
            Observer {
                for(chattingMessage in it.data!!){
                    chattingAdapter.addChattingMessage(chattingMessage)
                    rv_activity_chatting_recyclerview.smoothScrollToPosition(chattingAdapter.itemCount)
                    // TODO: 추후 마지막으로 읽은 메세지 위치로(firstUnreadMessageId) 수정
                }
            }
        )

        chattingViewModel.settingStomp()
        chattingViewModel.ChattingMessageLiveData.observe(this,
                Observer {
                    chattingAdapter.addChattingMessage(it)
                    rv_activity_chatting_recyclerview.smoothScrollToPosition(chattingAdapter.itemCount)
                }
        )

        bt_activity_chatting_send.setOnClickListener{
            if(et_activity_chatting_message_content.length() > 0) {
                chattingViewModel.sendMessage(et_activity_chatting_message_content.text.toString())
                et_activity_chatting_message_content.text.clear()
            }
        }

    }
}