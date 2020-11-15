package com.example.sport_planet.presentation.write

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentWriteBinding
import com.example.sport_planet.model.enums.SeparatorEnum
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.write.date.DateDialogFragment
import com.example.sport_planet.presentation.write.date.DateListener
import java.util.*

class WriteFragment private constructor() :
    BaseFragment<FragmentWriteBinding, WriteViewModel>(R.layout.fragment_write) {

    override val viewModel: WriteViewModel by lazy {
        ViewModelProvider(this).get(WriteViewModel::class.java)
    }

    private val dateDialog: DateDialogFragment by lazy {
        DateDialogFragment.newInstance()
    }

    private val dateListener: DateListener = object : DateListener {
        override fun confirm(date: Date) {

        }

        override fun cancel() {
            dateDialog.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

    companion object {
        const val DATE_DIALOG = "DATE_DIALOG"
        const val TIME_DIALOG = "TIME_DIALOG"

        fun newInstance() = WriteFragment()
    }
}

