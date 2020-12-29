package com.yapp.sport_planet.presentation.write.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.DialogDateBinding
import java.text.SimpleDateFormat

class DateDialogFragment private constructor() :
    DialogFragment(),
    View.OnClickListener {
    private lateinit var binding: DialogDateBinding
    private lateinit var dateListener: DateListener
    private var selectedDate: String? = null

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
        selectedDate = SimpleDateFormat("yyyy-MM-dd").format(binding.calendar.minDate) + "T"
        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedDate =
                "$year-${month + 1}-${if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth}T"
        }
        binding.btnConfirm.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnConfirm -> dateListener.confirm(selectedDate)
            binding.btnCancel -> dateListener.cancel()
        }
        this.dismiss()
    }

    fun setListener(listener: DateListener) {
        this.dateListener = listener
    }

    companion object {
        fun newInstance(): DateDialogFragment {
            val args = Bundle()

            val fragment = DateDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

interface DateListener {
    fun confirm(date: String?)
    fun cancel()
}