package com.example.sport_planet.presentation.board

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.MenuEnum
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.data.model.MenuModel
import com.example.sport_planet.data.model.chatting.ChatRoomInfo
import com.example.sport_planet.databinding.ActivityBoardBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.chatting.UserInfo
import com.example.sport_planet.presentation.chatting.view.ChattingActivity
import com.example.sport_planet.remote.RemoteDataSourceImpl
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class BoardActivity : BaseActivity<ActivityBoardBinding>(R.layout.activity_board) {
    private val viewModel: BoardViewModel by lazy {
        ViewModelProvider(
            this,
            BoardViewModelFactory(RemoteDataSourceImpl())
        ).get(BoardViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.boardId.value = intent.getLongExtra(BOARD_ID, -1)

        viewModel.boardId.observe(this, Observer {
            viewModel.getBoardContent()
        })

        viewModel.boardContent.observe(this, Observer {
            binding.tvTitle.text = it.title
            binding.tvBody.text = it.content
            binding.tvPeopleCount.text = "남은 인원 ${it.recruitNumber - it.recruitedNumber}명 (${it.recruitedNumber}/${it.recruitNumber})"
            binding.tvUserName.text = it.host.hostName
            binding.tvGroupStatus.text = it.groupStatus.name
            binding.tvExercise.text = it.exercise
            binding.tvCity.text = it.city
            binding.tvDate.text = it.startsAt
            binding.tvPlace.text = it.place
            binding.tvLikeCount.text = it.host.likes.toString()
            binding.tvDislikeCount.text = it.host.dislikes.toString()
            binding.toolbar.run {
                this.setMenu(
                    MenuModel(
                        MenuEnum.STAR.apply { this.enabled = it.isBookMark },
                        View.OnClickListener { viewModel.bookmarkChange() }),
                    MenuModel(MenuEnum.MENU, View.OnClickListener {
                        this@BoardActivity.let { activity ->
                            val popup = PopupMenu(activity.applicationContext, binding.toolbar.menu)
                            activity.menuInflater.inflate(R.menu.menu_board, popup.menu)
                            popup.setOnMenuItemClickListener { item ->
                                when (item.itemId) {
                                    R.id.board_report -> {
                                        //report
                                        false
                                    }
                                    R.id.board_hide -> {
                                        viewModel.hideBoard()
                                        false
                                    }
                                    else -> {
                                        false
                                    }
                                }
                            }
                            popup.show()
                        }
                    })
                )
            }

        })

        binding.toolbar.setSeparator(SeparatorEnum.NONE)
        binding.toolbar.setBackButtonClick(View.OnClickListener { this.finish() })

        binding.btnChatting.setOnClickListener {
            viewModel.makeChattingRoom()
            viewModel.makeChattingRoomtResultLiveData.observe(this,
                Observer {
                    val intent = Intent(applicationContext, ChattingActivity::class.java)
                    intent.putExtra("chatRoomInfo",
                        ChatRoomInfo(
                            it.data.id,
                            it.data.boardId,
                            it.data.guestId,
                            it.data.hostId == UserInfo.USER_ID,
                            it.data.opponentNickname
                        )
                    )
                    startActivity(intent)
                }
            )
        }
    }


    companion object {
        const val BOARD_ID = "BOARD_ID"

        fun createInstance(activity: Activity, boardId: Long) {
            val intent = Intent(activity, BoardActivity::class.java)
            intent.putExtra(BOARD_ID, boardId)
            activity.startActivity(intent)
        }
    }
}