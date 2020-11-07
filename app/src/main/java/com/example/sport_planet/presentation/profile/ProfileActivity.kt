package com.example.sport_planet.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivityProfileBinding
import com.example.sport_planet.presentation.base.BaseAcceptDialog
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.main.MainActivity
import com.example.sport_planet.remote.RemoteDataSourceImpl
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    private val remoteDataSourceImpl = RemoteDataSourceImpl()
    private val userToken by lazy {
        intent.getStringExtra("userToken")
    }
    private val userId by lazy {
        intent.getStringExtra("userId")
    }
    private val userEmail by lazy {
        intent.getStringExtra("userEmail")
    }
    private val userNickname by lazy {
        intent.getStringExtra("userNickname")
    }
    private val userExerciseList = mutableListOf<String>()

    private lateinit var userRegion: String

    private var userIntroduceMyself = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (userEmail != null)
            et_email.setText(userEmail)
        if (userNickname != null)
            et_nick_name.setText(userNickname)
        binding.clInterestExcise.setOnClickListener {
            compositeDisposable.add(
                remoteDataSourceImpl.getExercise()
                    .subscribe({
                        val dialog = ExerciseDialog.newInstance(
                            dialogTitleText = getString(R.string.dialog_select_title),
                            dialogWidthRatio = 0.911111f,
                            dialogItemList = it.exerciseModel.data.toTypedArray()
                        )
                        dialog.setSelectDialogListener(object :
                            ExerciseDialog.SelectDialogListener {
                            override fun onAccept(item: List<String>) {
                                userExerciseList.addAll(item)
                                binding.rvExercise.visibility = View.VISIBLE
                                binding.rvExercise.adapter =
                                    ExerciseListAdapter(::getExerciseItem).apply {
                                        setItem(userExerciseList)
                                    }
                                binding.clInterestExcise.visibility = View.GONE
                            }
                        })
                        dialog.show(supportFragmentManager, "")
                    }, {})
            )
        }
        binding.clRegion.setOnClickListener {
            compositeDisposable.add(
                remoteDataSourceImpl.getRegion()
                    .subscribe({
                        val dialog = RegionDialog.newInstance(
                            dialogTitleText = getString(R.string.dialog_region_title),
                            dialogWidthRatio = 0.911111f,
                            dialogItemList = it.regionModel.data.toTypedArray()
                        )
                        dialog.setSelectDialogListener(object :
                            RegionDialog.SelectDialogListener {
                            override fun onAccept(item: String) {
                                userRegion = item
                                binding.clRegionList.visibility = View.VISIBLE
                                binding.tvRegion.text = userRegion
                                binding.ivX.setOnClickListener { getRegionItem() }
                                binding.clRegion.visibility = View.GONE
                            }
                        })
                        dialog.show(supportFragmentManager, "")
                    }, {})
            )
        }
        binding.tvStart.setOnClickListener {
            userIntroduceMyself = tv_introduce_myself.text.toString()
        }
    }

    private fun getExerciseItem(item: String) {
        userExerciseList.remove(item)
        if (userExerciseList.size == 0) {
            binding.rvExercise.visibility = View.GONE
            binding.clInterestExcise.visibility = View.VISIBLE
        }
    }

    private fun getRegionItem() {
        userRegion = ""
        binding.clRegionList.visibility = View.GONE
        binding.clRegion.visibility = View.VISIBLE

    }

    private fun showNicknamePopup(title: String) {
        val dialog =
            BaseAcceptDialog.newInstance(dialogTitleText = title, dialogWidthRatio = 0.911111f)
        dialog.show(supportFragmentManager, "")
    }

    private fun showFinishedPopup(title: String, image: Int) {
        val dialog = BaseAcceptDialog.newInstance(
            dialogTitleText = title,
            dialogImage = image,
            dialogWidthRatio = 0.911111f
        )
        dialog.setAcceptDialogListener(object : BaseAcceptDialog.AcceptDialogListener{
            override fun onAccept() {
                val intent = Intent(this@ProfileActivity,MainActivity::class.java)
                startActivity(intent)
            }
        })
    }
}