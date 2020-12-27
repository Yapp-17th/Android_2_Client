package com.yapp.sport_planet.presentation.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<B : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    DialogFragment() {

    protected lateinit var binding: B
    protected var listener: BaseDialogListener? = null

    private var heightRatio = -1f
    private var widthRatio = 0.911111f

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? BaseDialogListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dpMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dpMetrics)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        arguments?.getFloat(DIALOG_WIDTH_RATIO)?.let {
            if (it > 0) {
                widthRatio = it
            }
        }

        arguments?.getFloat(DIALOG_HEIGHT_RATIO)?.let {
            if (it > 0) {
                heightRatio = it
            }
        }

        if (heightRatio == -1f) {
            dialog?.window?.setLayout(
                (dpMetrics.widthPixels * widthRatio).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            dialog?.window?.setLayout(
                (dpMetrics.widthPixels * widthRatio).toInt(),
                (dpMetrics.heightPixels * heightRatio).toInt()
            )
        }
    }

    companion object {
        const val DIALOG_HEIGHT_RATIO = "DIALOG_HEIGHT_RATIO"
        const val DIALOG_WIDTH_RATIO = "DIALOG_WIDTH_RATIO"
    }

}