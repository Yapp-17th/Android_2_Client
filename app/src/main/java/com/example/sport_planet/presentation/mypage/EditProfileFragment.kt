package com.example.sport_planet.presentation.mypage

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.response.ExerciseResponse
import com.example.sport_planet.data.response.RegionResponse
import com.example.sport_planet.databinding.ActivityProfileBinding
import com.example.sport_planet.presentation.base.BaseAcceptDialog
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.profile.ExerciseDialog
import com.example.sport_planet.presentation.profile.ExerciseListAdapter
import com.example.sport_planet.presentation.profile.ProfileViewModel
import com.example.sport_planet.presentation.profile.RegionDialog
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class EditProfileFragment :
    BaseFragment<ActivityProfileBinding, ProfileViewModel>(R.layout.activity_profile) {
    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun init() {
        initViewModel()
        observeLiveData()
        binding.tvStart.setOnClickListener {
            viewModel.editProfile()
        }
    }

    private fun initViewModel() {
        binding.vm = viewModel
        binding.tvStart.text = getString(R.string.fragment_edit_profile_edit_text)
        binding.customToolBar.title.text = getString(R.string.activity_profile_head)
        binding.customToolBar.back.setOnClickListener { onBackPressed() }
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
                    onBackPressed()
                }
                400 -> {
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
        dialog.show(parentFragmentManager, "")
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
        dialog.show(parentFragmentManager, "")
    }

    private fun showErrorPopup(title: String) {
        val dialog =
            BaseAcceptDialog.newInstance(dialogTitleText = title, dialogWidthRatio = 0.911111f)
        dialog.show(parentFragmentManager, "")
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
                setBackgroundColor(resources.getColor(R.color.darkblue, null))
                setTextColor(resources.getColor(R.color.white, null))

            }
        }
    }

    companion object {
        fun newInstance() = EditProfileFragment()
    }
}