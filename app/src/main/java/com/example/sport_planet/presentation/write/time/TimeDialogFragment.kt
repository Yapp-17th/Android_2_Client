package com.example.sport_planet.presentation.write.time

import android.os.Bundle
import android.util.Log
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
        val hour = if (binding.time.hour > 10) {
            binding.time.hour.toString()
        } else {
            "0" + binding.time.hour.toString()
        }
        val minute = if (binding.time.minute > 10) {
            binding.time.minute.toString()
        } else {
            "0" + binding.time.minute.toString()
        }
        Log.d("ehdghks", "hour : $hour")
        Log.d("ehdghks", "minute : $minute")
        when (v) {
            binding.btnConfirm -> timeListener.confirm(hour + minute)
            binding.btnCancel -> timeListener.cancel()
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