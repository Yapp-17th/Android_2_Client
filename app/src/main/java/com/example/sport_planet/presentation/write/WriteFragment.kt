package com.example.sport_planet.presentation.write

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentWriteBinding
import com.example.sport_planet.presentation.base.BaseFragment

class WriteFragment private constructor() :
    BaseFragment<FragmentWriteBinding, WriteViewModel>(R.layout.fragment_write) {

    override val viewModel: WriteViewModel by lazy {
        ViewModelProvider(this).get(WriteViewModel::class.java)
    }

    override fun init() {
        val adapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.count_list,
            R.layout.item_member_count
        )

        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        fun newInstance() = WriteFragment()
    }
}