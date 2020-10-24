package com.example.sport_planet.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.sport_planet.R
import com.example.sport_planet.databinding.DialogExerciseBinding
import com.example.sport_planet.presentation.base.BaseDialogFragment

class ExerciseDialog : BaseDialogFragment<DialogExerciseBinding>(R.layout.dialog_exercise) {

    private var mListener: SelectDialogListener? = null
    val item = mutableListOf<String>()
    private val selectItems = mutableListOf<String>()

    interface SelectDialogListener {
        fun onAccept(item: List<String>)
    }

    fun setSelectDialogListener(listener: SelectDialogListener) {
        mListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getStringArray("dialogItemList")?.toMutableList()?.let { item.addAll(it) }
        binding.tvTitle.text = arguments?.getString("dialogTitleText")
        binding.rvContent.adapter = ExerciseAdapter(::getItem).apply {
            setItem(item)
        }
        binding.tvSelect.setOnClickListener {
            mListener?.onAccept(selectItems)
            dismiss()
        }

    }

    private fun getItem(item: String) {
        if (selectItems.contains(item)) {
            selectItems.remove(item)
        } else {
            selectItems.add(item)
        }
        with(binding.tvSelect) {
            if (selectItems.size != 0) {
                isEnabled = true
                setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                isEnabled = false
                setTextColor(ContextCompat.getColor(context, R.color.gray))
            }
        }
    }

    companion object {
        fun newInstance(
            dialogTitleText: String,
            dialogHeightRatio: Float? = null,
            dialogWidthRatio: Float? = null,
            dialogItemList: Array<String>
        ) = ExerciseDialog().apply {
            arguments = Bundle().apply {
                if (dialogHeightRatio != null) {
                    putFloat(DIALOG_HEIGHT_RATIO, dialogHeightRatio)
                }
                if (dialogWidthRatio != null) {
                    putFloat(DIALOG_WIDTH_RATIO, dialogWidthRatio)
                }
                putString("dialogTitleText", dialogTitleText)
                putStringArray("dialogItemList", dialogItemList)
            }
        }
    }
}