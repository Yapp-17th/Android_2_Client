package com.example.sport_planet.presentation.write.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.sport_planet.R
import com.example.sport_planet.databinding.DialogTimeBinding

class TimeDialogFragment private constructor() :
    DialogFragment(),
    View.OnClickListener {
    private lateinit var binding: DialogTimeBinding
    private lateinit var timeListener: TimeListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_time, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnConfirm -> timeListener?.confirm("")
            binding.btnCancel -> timeListener?.cancel()
        }
        this.dismiss()
    }

    fun setListener(listener: TimeListener) {
        this.timeListener = listener
    }

    companion object {
        fun newInstance() = TimeDialogFragment()
    }
}

interface TimeListener {
    fun confirm(time: String)
    fun cancel()
}