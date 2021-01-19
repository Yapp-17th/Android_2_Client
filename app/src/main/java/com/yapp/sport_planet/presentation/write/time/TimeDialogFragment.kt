package com.yapp.sport_planet.presentation.write.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.DialogTimeBinding
import com.yapp.sport_planet.presentation.write.WriteActivity.Companion.INTENT_DATE
import java.text.SimpleDateFormat
import java.util.*

class TimeDialogFragment private constructor() :
    DialogFragment(),
    View.OnClickListener {
    private lateinit var binding: DialogTimeBinding
    private lateinit var timeListener: TimeListener
    private var currentHour: Int = 0
    private var currentMinute: Int = 0

    private val pickDate by lazy { requireArguments().getString(INTENT_DATE) }

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

        currentHour = binding.time.hour
        currentMinute = binding.time.minute
        binding.time.hour = binding.time.hour + 4
        binding.btnConfirm.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val now: Long = System.currentTimeMillis()
        val mDate = Date(now)
        val simpleDate = SimpleDateFormat("yyyy-MM-dd'T'")
        val currentDate = simpleDate.format(mDate)

        val isToday = currentDate == pickDate

        if(isToday){
            if ((binding.time.hour - currentHour < 4) || ((binding.time.hour - currentHour == 4)) && binding.time.minute > currentMinute) {
                Toast.makeText(context, "최소 4시간의 차이가 있어야 합니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }

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
        when (v) {
            binding.btnConfirm -> timeListener.confirm("$pickDate$hour:$minute")
            binding.btnCancel -> timeListener.cancel()
        }
        this.dismiss()
    }

    fun setListener(listener: TimeListener) {
        this.timeListener = listener
    }

    companion object {
        fun newInstance(date: String): TimeDialogFragment {
            val args = Bundle()
            args.putString(INTENT_DATE, date)
            val fragment = TimeDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

interface TimeListener {
    fun confirm(time: String)
    fun cancel()
}