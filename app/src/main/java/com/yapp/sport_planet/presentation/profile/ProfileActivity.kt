package com.yapp.sport_planet.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.databinding.ActivityProfileBinding
import com.yapp.sport_planet.presentation.base.BaseAcceptDialog
import com.yapp.sport_planet.presentation.base.BaseActivity
import com.yapp.sport_planet.presentation.chatting.UserInfo
import com.yapp.sport_planet.presentation.login.LoginActivity
import com.yapp.sport_planet.presentation.main.MainActivity
import com.yapp.sport_planet.remote.NetworkHelper
import com.yapp.sport_planet.util.PrefUtil
import com.yapp.sport_planet.util.hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    val viewModel: ProfileViewModel
            by lazy { ViewModelProvider(this).get(ProfileViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeLiveData()

    }

    private fun initView() {
        binding.run {
            clFullScreen.setOnClickListener {
                clFullScreen.hideKeyboard()
            }
            vm = viewModel
            customToolBar.run {
                this.title.text = getString(R.string.activity_profile_head)
                this.back.setOnClickListener {
                    val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            tvStart.setOnClickListener {
                viewModel.postSignUp()
            }
        }

        binding.etEmail.addTextChangedListener {
            viewModel.userEmail.value = it.toString()
        }

        intent.run {
            viewModel.run {
                getStringExtra("userToken")?.let { setUserToken(it) }
                getStringExtra("userId")?.let { setUserId(it) }
                getStringExtra("userEmail")?.let { userEmail.value = it }
                getStringExtra("userNickname")?.let { userNickname.value = it }
            }

        }
    }

    private fun observeLiveData() {
        viewModel.run {
            userEmail.observe(this@ProfileActivity, Observer {
                checkButtonAble()
            })
            userNickname.observe(this@ProfileActivity, Observer {
                checkButtonAble()
            })
            userName.observe(this@ProfileActivity, Observer {
                checkButtonAble()
            })
            userIntroduceMyself.observe(this@ProfileActivity, Observer {
                checkButtonAble()
            })
            userExerciseList.observe(this@ProfileActivity, Observer {
                checkButtonAble()
            })
            userRegion.observe(this@ProfileActivity, Observer {
                checkButtonAble()
            })
            exerciseList.observe(this@ProfileActivity, Observer {
                showExercisePopup(it)
            })
            regionList.observe(this@ProfileActivity, Observer {
                showRegionPopup(it)
            })
            postSignUpStatus.observe(this@ProfileActivity, Observer {
                when (it) {
                    200 -> {
                        showFinishedPopup("회원가입 성공했습니다.")
                    }
                    400 -> {
                        showErrorPopup(viewModel.postSignUpStatusMessage.value.toString())
                    }
                    500 -> {
                        showErrorPopup(viewModel.postSignUpStatusMessage.value.toString())
                    }
                }
            })
            isLoading
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (it) showLoading() else hideLoading() }
                .addTo(compositeDisposable)
        }
    }

    private fun showRegionPopup(it: RegionResponse) {
        val dialog = RegionDialog.newInstance(
            dialogTitleText = getString(R.string.dialog_region_title),
            dialogItemList = it.data.sortedBy { it.id }
        )
        dialog.setSelectDialogListener(object :
            RegionDialog.SelectDialogListener {
            override fun onAccept(item: String, id: Long) {
                viewModel.setUserRegion(item, id)
                binding.run {
                    clRegionList.visibility = View.VISIBLE
                    tvRegion.text = viewModel.userRegion.value
                    ivX.setOnClickListener { getRegionItem() }
                    clRegion.visibility = View.INVISIBLE
                }
            }
        })
        dialog.show(supportFragmentManager, "")
    }

    private fun showExercisePopup(it: ExerciseResponse) {
        val dialog = ExerciseDialog.newInstance(
            dialogTitleText = getString(R.string.dialog_select_title),
            dialogItemList = it.data.sortedBy { it.id }
        )
        dialog.setSelectDialogListener(object :
            ExerciseDialog.SelectDialogListener {
            override fun onAccept(item: List<String>, idItem: List<Long>) {
                viewModel.setUserExerciseList(item, idItem)

                binding.run {
                    rvExercise.visibility = View.VISIBLE
                    rvExercise.adapter =
                        ExerciseListAdapter(::getExerciseItem).apply {
                            viewModel.userExerciseList.value?.let { it -> setItem(it) }
                        }
                    clInterestExcise.visibility = View.INVISIBLE
                }
            }
        })
        dialog.show(supportFragmentManager, "")
    }

    private fun showErrorPopup(title: String) {
        val dialog =
            BaseAcceptDialog.newInstance(dialogTitleText = title)
        dialog.show(supportFragmentManager, "")
    }

    private fun showFinishedPopup(text: String) {
        val dialog = BaseAcceptDialog.newInstance(
            dialogTitleText = text,
            dialogImage = R.drawable.profile_finish_logo
        )
        dialog.setAcceptDialogListener(object : BaseAcceptDialog.AcceptDialogListener {
            override fun onAccept() {
                NetworkHelper.token = viewModel.serverToken.value.toString()
                PrefUtil.setStrValue(
                    this@ProfileActivity,
                    "serverToken",
                    viewModel.serverToken.value.toString()
                )
                UserInfo.USER_ID = viewModel.serverUserId.value.toString().toLong()
                val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })
        dialog.show(supportFragmentManager, "")
    }

    private fun getExerciseItem(item: String, idItem: Int) {
        viewModel.run {
            removeUserExerciseItem(item, idItem)
            if (userExerciseList.value!!.isEmpty()) {
                binding.run {
                    rvExercise.visibility = View.INVISIBLE
                    clInterestExcise.visibility = View.VISIBLE
                }
            }
        }


    }

    private fun getRegionItem() {
        viewModel.setUserRegion("", 0L)
        binding.run {
            clRegionList.visibility = View.INVISIBLE
            clRegion.visibility = View.VISIBLE
        }

    }

    private fun checkButtonAble() {
        if (
            with(viewModel) {
                userEmail.value.isNullOrBlank() || userNickname.value.isNullOrBlank() ||
                        userExerciseList.value.isNullOrEmpty() || userName.value.isNullOrBlank() ||
                        userRegion.value.isNullOrBlank() || userIntroduceMyself.value.isNullOrBlank()
            }
        ) {
            binding.tvStart.run {
                isEnabled = false
                setBackgroundColor(resources.getColor(R.color.grayefefef, null))
                setTextColor(resources.getColor(R.color.gray9c9c9c, null))

            }
        } else {
            binding.tvStart.run {
                isEnabled = true
                setBackgroundColor(resources.getColor(R.color.dark_blue, null))
                setTextColor(resources.getColor(R.color.white, null))
            }
        }
    }
}