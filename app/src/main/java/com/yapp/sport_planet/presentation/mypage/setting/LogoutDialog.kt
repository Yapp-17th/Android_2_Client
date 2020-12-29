package com.yapp.sport_planet.presentation.mypage.setting

import android.os.Bundle
import android.view.View
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.DialogLogoutBinding
import com.yapp.sport_planet.presentation.base.BaseDialogFragment

class LogoutDialog : BaseDialogFragment<DialogLogoutBinding>(R.layout.dialog_logout) {
    private var mListener: LogoutDialogListener? = null

    interface LogoutDialogListener {
        fun onAccept()
    }

    fun setLogoutDialogListener(listener: LogoutDialogListener) {
        mListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            tvCancel.setOnClickListener {
                dismiss()
            }
            tvOk.run {
                setOnClickListener {
                    mListener?.onAccept()
                    dismiss()
                }
            }
        }
    }

    companion object {
        fun newInstance(
            dialogHeightRatio: Float? = null
        ) = LogoutDialog().apply {
            arguments = Bundle().apply {
                if (dialogHeightRatio != null) {
                    putFloat(DIALOG_HEIGHT_RATIO, dialogHeightRatio)
                }
            }
        }
    }
}