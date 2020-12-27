package com.yapp.sport_planet.presentation.base

import android.os.Bundle
import android.view.View
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.DialogBaseAcceptCancelBinding

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
        binding.run {
            if (title == "")
                tvTitle.visibility = View.GONE
            else
                tvTitle.text = title

            tvBody.text = body
            tvCancel.setOnClickListener {
                dismiss()
            }
            tvOk.run {
                text = acceptText
                setOnClickListener {
                    mListener?.onAccept()
                    dismiss()
                }
            }
        }
    }

    companion object {
        fun newInstance(
            dialogTitleText: String?,
            dialogBodyText: String,
            dialogAcceptText : String,
            dialogHeightRatio: Float? = null
        ) = BaseAcceptCancelDialog().apply {
            arguments = Bundle().apply {
                if (dialogHeightRatio != null) {
                    putFloat(DIALOG_HEIGHT_RATIO, dialogHeightRatio)
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