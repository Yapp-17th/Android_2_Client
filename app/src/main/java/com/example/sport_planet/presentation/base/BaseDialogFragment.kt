package com.example.sport_planet.presentation.base

import android.content.Context
import android.os.Bundle
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
    private var widthRatio = 0.88888889f

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
        TODO("화면 해상도 대응")
    }

}
