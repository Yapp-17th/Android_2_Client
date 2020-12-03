package com.example.sport_planet.presentation.write.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.sport_planet.R
import com.example.sport_planet.databinding.DialogDateBinding
import java.util.*

class DateDialogFragment private constructor() :
    DialogFragment(),
    View.OnClickListener {
    private lateinit var binding: DialogDateBinding
    private lateinit var dateListener: DateListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_date, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendar.minDate = System.currentTimeMillis() + ((1000 * 60 * 60) * 4)
        binding.btnConfirm.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnConfirm -> dateListener?.confirm(Date(binding.calendar.date))
            binding.btnCancel -> dateListener?.cancel()
        }
        this.dismiss()
    }

    fun setListener(listener: DateListener) {
        this.dateListener = listener
    }

    companion object {
        fun newInstance() = DateDialogFragment()
    }
}

interface DateListener {
    fun confirm(date: Date)
    fun cancel()
}