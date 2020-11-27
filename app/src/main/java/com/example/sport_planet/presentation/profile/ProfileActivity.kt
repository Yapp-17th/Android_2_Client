package com.example.sport_planet.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.response.basic.ExerciseResponse
import com.example.sport_planet.data.response.basic.RegionResponse
import com.example.sport_planet.databinding.ActivityProfileBinding
import com.example.sport_planet.presentation.base.BaseAcceptDialog
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.login.LoginActivity
import com.example.sport_planet.presentation.main.MainActivity
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    val viewModel: ProfileViewModel
            by lazy { ViewModelProvider(this).get(ProfileViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observeLiveData()
        binding.tvStart.setOnClickListener {
            viewModel.postSignUp()
        }
    }

    private fun initViewModel() {
        binding.vm = viewModel
        binding.customToolBar.title.text = getString(R.string.activity_profile_head)
        binding.customToolBar.back.setOnClickListener {
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        intent.getStringExtra("userToken")?.let { viewModel.setUserToken(it) }
        intent.getStringExtra("userId")?.let { viewModel.setUserId(it) }
        intent.getStringExtra("userEmail")?.let { viewModel.userEmail.value = it }
        intent.getStringExtra("userNickname")?.let { viewModel.userNickname.value = it }
    }

    private fun observeLiveData() {
        viewModel.userEmail.observe(this, Observer {
            checkButtonAble()
        })
        viewModel.userNickname.observe(this, Observer {
            checkButtonAble()
        })
        viewModel.userName.observe(this, Observer {
            checkButtonAble()
        })
        viewModel.userIntroduceMyself.observe(this, Observer {
            checkButtonAble()
        })
        viewModel.userExerciseList.observe(this, Observer {
            checkButtonAble()
        })
        viewModel.userRegion.observe(this, Observer {
            checkButtonAble()
        })
        viewModel.exerciseList.observe(this, Observer {
            showExercisePopup(it)
        })
        viewModel.regionList.observe(this, Observer {
            showRegionPopup(it)
        })
        viewModel.postSignUpStatus.observe(this, Observer {
            when (it) {
                200 -> {
                    showFinishedPopup(viewModel.postSignUpStatusMessage.value.toString())
                }
                400 -> {
                    showErrorPopup(viewModel.postSignUpStatusMessage.value.toString())
                }
                500 -> {
                    showErrorPopup(viewModel.postSignUpStatusMessage.value.toString())
                }
            }
        })
    }

    private fun showRegionPopup(it: RegionResponse) {
        val dialog = RegionDialog.newInstance(
            dialogTitleText = getString(R.string.dialog_region_title),
            dialogWidthRatio = 0.911111f,
            dialogItemList = it.data
        )
        dialog.setSelectDialogListener(object :
            RegionDialog.SelectDialogListener {
            override fun onAccept(item: String, id: Long) {
                viewModel.setUserRegion(item, id)
                binding.run {
                    clRegionList.visibility = View.VISIBLE
                    tvRegion.text = viewModel.userRegion.value
                    ivX.setOnClickListener { getRegionItem() }
                    clRegion.visibility = View.GONE
                }
            }
        })
        dialog.show(supportFragmentManager, "")
    }

    private fun showExercisePopup(it: ExerciseResponse) {
        val dialog = ExerciseDialog.newInstance(
            dialogTitleText = getString(R.string.dialog_select_title),
            dialogWidthRatio = 0.911111f,
            dialogItemList = it.data
        )
        dialog.setSelectDialogListener(object :
            ExerciseDialog.SelectDialogListener {
            override fun onAccept(item: List<String>, idItem: List<Long>) {
                viewModel.setUserExerciseList(item, idItem)

                binding.run {
                    rvExercise.visibility = View.VISIBLE
                    rvExercise.adapter =
                        ExerciseListAdapter(::getExerciseItem).apply {
                            setItem(viewModel.userExerciseList.value!!)
                        }
                    clInterestExcise.visibility = View.GONE
                }
            }
        })
        dialog.show(supportFragmentManager, "")
    }

    private fun showErrorPopup(title: String) {
        val dialog =
            BaseAcceptDialog.newInstance(dialogTitleText = title, dialogWidthRatio = 0.911111f)
        dialog.show(supportFragmentManager, "")
    }

    private fun showFinishedPopup(text: String) {
        val dialog = BaseAcceptDialog.newInstance(
            dialogTitleText = text,
            dialogImage = R.drawable.profile_finish_logo,
            dialogWidthRatio = 0.911111f
        )
        dialog.setAcceptDialogListener(object : BaseAcceptDialog.AcceptDialogListener {
            override fun onAccept() {
                val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })
        dialog.show(supportFragmentManager, "")
    }

    private fun getExerciseItem(item: String, idItem: Int) {
        viewModel.removeUserExerciseItem(item, idItem)
        if (viewModel.userExerciseList.value!!.isEmpty()) {
            binding.run {
                rvExercise.visibility = View.GONE
                clInterestExcise.visibility = View.VISIBLE
            }
        }
    }

    private fun getRegionItem() {
        viewModel.setUserRegion("", 0L)
        binding.run {
            clRegionList.visibility = View.GONE
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