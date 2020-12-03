package com.example.sport_planet.presentation.write

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.databinding.FragmentWriteBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.write.adapter.WriteGridViewAdapter
import com.example.sport_planet.presentation.write.date.DateDialogFragment
import com.example.sport_planet.presentation.write.date.DateListener
import com.example.sport_planet.presentation.write.select.SelectFragment
import com.example.sport_planet.presentation.write.time.TimeDialogFragment
import com.example.sport_planet.presentation.write.time.TimeListener
import java.text.SimpleDateFormat
import java.util.*

class WriteFragment private constructor() :
    BaseFragment<FragmentWriteBinding, WriteViewModel>(R.layout.fragment_write) {

    override val viewModel: WriteViewModel by lazy {
        ViewModelProvider(this).get(WriteViewModel::class.java)
    }

    private val dateDialog: DateDialogFragment by lazy {
        DateDialogFragment.newInstance().apply {
            setListener(dateListener)
        }
    }

    private val timeDialog: TimeDialogFragment by lazy {
        TimeDialogFragment.newInstance().apply {
            setListener(timeListener)
        }
    }

//    private val exerciseSelectFragment: SelectFragment<ExerciseModel> by lazy {
//        SelectFragment.newInstance()
//    }

    private val gridAdapter = WriteGridViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gvFilter.adapter = gridAdapter

        binding.toolbar.run {
            setSeparator(SeparatorEnum.NONE)
        }

        binding.spinnerCount.adapter =
            ArrayAdapter.createFromResource(
                context!!,
                R.array.count_list,
                R.layout.item_member_count
            )

        binding.spinnerCount.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        binding.tvDate.hint = SimpleDateFormat(getString(R.string.write_date_format)).format(System.currentTimeMillis()) + " " + SimpleDateFormat(getString(R.string.write_time_format)).format(System.currentTimeMillis())

        binding.tvDate.setOnClickListener {
            dateDialog.show(childFragmentManager.beginTransaction(), DATE_DIALOG)
        }
    }

    private val dateListener: DateListener = object : DateListener {
        override fun confirm(date: Date) {
            binding.tvDate.text = SimpleDateFormat(getString(R.string.write_date_format)).format(date)
            timeDialog.show(childFragmentManager.beginTransaction(), TIME_DIALOG)
            dateDialog.dismiss()
        }

        override fun cancel() {
//            viewModel.clearDateAndTime()
        }
    }

    private val timeListener: TimeListener = object : TimeListener {
        override fun confirm(time: String) {
//            viewModel.time = time
            binding.tvDate.text = binding.tvDate.text.toString() + " " + time
            timeDialog.dismiss()
            // api call
        }

        override fun cancel() {
//            viewModel.clearDateAndTime()
        }
    }

    companion object {
        const val DATE_DIALOG = "DATE_DIALOG"
        const val TIME_DIALOG = "TIME_DIALOG"

        fun newInstance() = WriteFragment()
    }

    override fun init() {
    }
}

