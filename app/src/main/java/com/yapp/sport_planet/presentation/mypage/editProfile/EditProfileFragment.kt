package com.yapp.sport_planet.presentation.mypage.editProfile

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.databinding.ActivityProfileBinding
import com.yapp.sport_planet.presentation.base.BaseAcceptDialog
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.profile.ExerciseDialog
import com.yapp.sport_planet.presentation.profile.ExerciseListAdapter
import com.yapp.sport_planet.presentation.profile.ProfileViewModel
import com.yapp.sport_planet.presentation.profile.RegionDialog
import com.yapp.sport_planet.util.hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*


class EditProfileFragment :
    BaseFragment<ActivityProfileBinding, ProfileViewModel>(R.layout.activity_profile) {
    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun init() {
        initViewModel()
        viewModel.getMyProfileEdit()
        observeLiveData()

    }

    private fun initViewModel() {
        binding.run {
            clFullScreen.setOnClickListener {
                clFullScreen.hideKeyboard()
            }
            vm = viewModel
            tvStart.text = getString(R.string.fragment_edit_profile_edit_text)
            et_email.isEnabled = false
            customToolBar.title.text = getString(R.string.activity_profile_head_edit)
            customToolBar.back.setOnClickListener { onBackPressed() }
            tvStart.setOnClickListener {
                viewModel.editProfile()
            }
            ivX.setOnClickListener { viewModel.setUserRegion("", 0L) }
        }
    }

    private fun observeLiveData() {
        viewModel.run {
            userEmail.observe(this@EditProfileFragment, Observer {
                checkButtonAble()
            })
            userNickname.observe(this@EditProfileFragment, Observer {
                checkButtonAble()
            })
            userName.observe(this@EditProfileFragment, Observer {
                checkButtonAble()
            })
            userIntroduceMyself.observe(this@EditProfileFragment, Observer {
                checkButtonAble()
            })
            userExerciseList.observe(this@EditProfileFragment, Observer {
                binding.run {
                    if (it.isEmpty()) {
                        rvExercise.visibility = View.INVISIBLE
                        clInterestExcise.visibility = View.VISIBLE
                    } else {
                        rvExercise.visibility = View.VISIBLE
                        rvExercise.adapter =
                            ExerciseListAdapter(::getExerciseItem).apply {
                                setItem(it)
                            }
                        clInterestExcise.visibility = View.INVISIBLE
                    }
                }
                checkButtonAble()
            })
            userRegion.observe(this@EditProfileFragment, Observer {
                binding.run {
                    if (it.isNullOrBlank()) {
                        clRegionList.visibility = View.INVISIBLE
                        clRegion.visibility = View.VISIBLE
                    } else {
                        clRegionList.visibility = View.VISIBLE
                        clRegion.visibility = View.INVISIBLE
                    }
                }
                checkButtonAble()
            })
            exerciseList.observe(this@EditProfileFragment, Observer {
                showExercisePopup(it)
            })
            regionList.observe(this@EditProfileFragment, Observer {
                showRegionPopup(it)
            })
            postSignUpStatus.observe(this@EditProfileFragment, Observer {
                when (it) {
                    200 -> {
                        onBackPressed()
                    }
                    400 -> {
                        showErrorPopup(viewModel.postSignUpStatusMessage.value.toString())
                    }
                }
            })
            isLoading.observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (it) showLoading() else hideLoading() }
                .addTo(compositeDisposable)
        }

    }

    private fun showRegionPopup(it: RegionResponse) {
        val dialog = RegionDialog.newInstance(
            dialogTitleText = getString(R.string.dialog_region_title),
            dialogItemList = it.data
        )
        dialog.setSelectDialogListener(object :
            RegionDialog.SelectDialogListener {
            override fun onAccept(item: String, id: Long) {
                viewModel.setUserRegion(item, id)
            }
        })
        dialog.show(parentFragmentManager, "")
    }

    private fun showExercisePopup(it: ExerciseResponse) {
        val dialog = ExerciseDialog.newInstance(
            dialogTitleText = getString(R.string.dialog_select_title),
            dialogItemList = it.data
        )
        dialog.setSelectDialogListener(object :
            ExerciseDialog.SelectDialogListener {
            override fun onAccept(item: List<String>, idItem: List<Long>) {
                viewModel.setUserExerciseList(item, idItem)
            }
        })
        dialog.show(parentFragmentManager, "")
    }

    private fun showErrorPopup(title: String) {
        val dialog =
            BaseAcceptDialog.newInstance(dialogTitleText = title)
        dialog.show(parentFragmentManager, "")
    }

    private fun getExerciseItem(item: String, idItem: Int) {
        viewModel.removeUserExerciseItem(item, idItem)

    }


    private fun checkButtonAble() {
        if (
            viewModel.run {
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

    companion object {
        fun newInstance() = EditProfileFragment()
    }
}