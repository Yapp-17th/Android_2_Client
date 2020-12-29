package com.yapp.sport_planet.presentation.base

import android.os.Bundle
import android.view.View
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.DialogBaseAcceptBinding

class BaseAcceptDialog : BaseDialogFragment<DialogBaseAcceptBinding>(R.layout.dialog_base_accept) {
    private var mListener: AcceptDialogListener? = null

    interface AcceptDialogListener {
        fun onAccept()
    }

    fun setAcceptDialogListener(listener: AcceptDialogListener) {
        mListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("dialogImage", 0)?.let {
            if (it != 0)
                binding.ivTitleImage.setImageResource(it)
        }
        binding.tvTitle.text = arguments?.getString("dialogTitleText").toString()
        binding.tvOk.setOnClickListener {
            mListener?.onAccept()
            dismiss()
        }
    }

    companion object {
        fun newInstance(
            dialogTitleText: String,
            dialogImage: Int? = null,
            dialogHeightRatio: Float? = null
        ) = BaseAcceptDialog().apply {
            arguments = Bundle().apply {
                if (dialogHeightRatio != null) {
                    putFloat(DIALOG_HEIGHT_RATIO, dialogHeightRatio)
                }
                if (dialogImage != null) {
                    putInt("dialogImage", dialogImage)
                }
                putString("dialogTitleText", dialogTitleText)
            }
        }
    }
}