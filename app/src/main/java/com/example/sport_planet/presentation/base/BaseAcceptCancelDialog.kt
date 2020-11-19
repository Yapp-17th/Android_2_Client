package com.example.sport_planet.presentation.base

import android.os.Bundle
import android.view.View
import com.example.sport_planet.R
import com.example.sport_planet.databinding.DialogBaseAcceptCancelBinding

class BaseAcceptCancelDialog :
    BaseDialogFragment<DialogBaseAcceptCancelBinding>(R.layout.dialog_base_accept_cancel) {
    private var mListener: AcceptCancelDialogListener? = null

    interface AcceptCancelDialogListener {
        fun onAccept()
    }

    fun setAcceptCancelDialogListener(listener: AcceptCancelDialogListener) {
        mListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("dialogTitleText", "")
        val body = arguments?.getString("dialogBodyText", "")
        val acceptText = arguments?.getString("dialogAcceptText", "")
        if (title == "")
            binding.tvTitle.visibility = View.GONE
        else
            binding.tvTitle.text = title

        binding.tvBody.text = body
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
        binding.tvOk.text = acceptText
        binding.tvOk.setOnClickListener {
            mListener?.onAccept()
            dismiss()
        }
    }

    companion object {
        fun newInstance(
            dialogTitleText: String?,
            dialogBodyText: String,
            dialogAcceptText : String,
            dialogHeightRatio: Float? = null,
            dialogWidthRatio: Float? = null
        ) = BaseAcceptCancelDialog().apply {
            arguments = Bundle().apply {
                if (dialogHeightRatio != null) {
                    putFloat(DIALOG_HEIGHT_RATIO, dialogHeightRatio)
                }
                if (dialogWidthRatio != null) {
                    putFloat(DIALOG_WIDTH_RATIO, dialogWidthRatio)
                }
                if (dialogTitleText != null) {
                    putString("dialogTitleText", dialogTitleText)
                }
                putString("dialogBodyText", dialogBodyText)
                putString("dialogAcceptText", dialogAcceptText)
            }
        }
    }
}