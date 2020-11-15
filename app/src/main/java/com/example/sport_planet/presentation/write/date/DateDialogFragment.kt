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

class DateDialogFragment private constructor() : DialogFragment() {
    private lateinit var binding: DialogDateBinding
    private val dateListener: DateListener? = null

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
        binding.calendar.minDate = System.currentTimeMillis()

        binding.btnConfirm.setOnClickListener {
            dateListener?.confirm(Date(binding.calendar.date))
        }

        binding.btnCancel.setOnClickListener {
            dateListener?.cancel()
        }
    }

    companion object {
        fun newInstance() = DateDialogFragment()
    }
}

interface DateListener {
    fun confirm(date: Date)
    fun cancel()
}