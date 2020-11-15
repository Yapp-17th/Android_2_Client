package com.example.sport_planet.presentation.write.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.sport_planet.R
import com.example.sport_planet.databinding.DialogTimeBinding
import java.util.*

class TimeDialogFragment private constructor() : DialogFragment() {
    private lateinit var binding: DialogTimeBinding
    private val timeListener: TimeListener? = null

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

        binding.btnConfirm.setOnClickListener {
            timeListener?.confirm()
        }

        binding.btnCancel.setOnClickListener {
            timeListener?.cancel()
        }
    }

    companion object {
        fun newInstance(date: Date): TimeDialogFragment {
            val fragment = TimeDialogFragment()
            //
            return fragment
        }
    }
}

interface TimeListener {
    fun confirm(date: Date, time: String)
    fun cancel()
}