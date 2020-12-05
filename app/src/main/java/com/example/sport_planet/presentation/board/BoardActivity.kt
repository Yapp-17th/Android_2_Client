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
import com.example.sport_planet.presentation.write.WriteActivity
import com.example.sport_planet.remote.RemoteDataSourceImpl
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*
import java.text.SimpleDateFormat

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

        viewModel.boardContent.observe(this, Observer { boardContentModel ->
            val isHost = boardContentModel.host.hostId == UserInfo.USER_ID
            binding.btnChatting.visibility =
                if (isHost) View.GONE else View.VISIBLE

            binding.tvTitle.text = boardContentModel.title
            binding.tvBody.text = boardContentModel.content
            binding.tvPeopleCount.text =
                "남은 인원 ${boardContentModel.recruitNumber - boardContentModel.recruitedNumber}명 (${boardContentModel.recruitedNumber}/${boardContentModel.recruitNumber})"
            binding.tvUserName.text = boardContentModel.host.hostName
            binding.tvGroupStatus.text = boardContentModel.groupStatus.name
            binding.tvExercise.text = boardContentModel.exercise
            binding.tvCity.text = boardContentModel.city
            binding.tvTag.text = boardContentModel.userTag
            binding.tvDate.text =
                SimpleDateFormat(getString(R.string.full_date_format)).format(boardContentModel.startsAt)
            binding.tvPlace.text = boardContentModel.place
            binding.tvLikeCount.text = boardContentModel.host.likes.toString()
            binding.tvDislikeCount.text = boardContentModel.host.dislikes.toString()
            binding.toolbar.run {
                this.setMenu(
                    MenuModel(
                        MenuEnum.STAR.apply { this.enabled = boardContentModel.isBookMark },
                        View.OnClickListener { viewModel.bookmarkChange() }),
                    MenuModel(MenuEnum.MENU, View.OnClickListener {
                        this@BoardActivity.let { activity ->
                            val popup = PopupMenu(activity.applicationContext, binding.toolbar.menu)
                            activity.menuInflater.inflate(
                                if (isHost) R.menu.menu_owner_board else R.menu.menu_board,
                                popup.menu
                            )
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
                                    R.id.board_edit -> {
                                        WriteActivity.createInstance(
                                            activity = this@BoardActivity,
                                            boardId = boardContentModel.boardId
                                        )
                                        false
                                    }
                                    R.id.board_delete -> {
                                        viewModel.deleteBoard()
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
            viewModel.makeChattingRoomResultLiveData.observe(this,
                Observer {
                    it.getContentIfNotHandled()?.data.let { chattingRoom ->
                        if (chattingRoom != null) {
                            val intent = Intent(applicationContext, ChattingActivity::class.java)
                            intent.putExtra(
                                "chatRoomInfo",
                                ChatRoomInfo(
                                    chattingRoom.id,
                                    chattingRoom.boardId,
                                    chattingRoom.guestId,
                                    chattingRoom.hostId == UserInfo.USER_ID,
                                    chattingRoom.opponentNickname
                                )
                            )
                            startActivity(intent)
                        }
                    }
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