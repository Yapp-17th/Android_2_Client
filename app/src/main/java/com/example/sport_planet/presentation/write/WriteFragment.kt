package com.example.sport_planet.presentation.write

import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentWriteBinding
import com.example.sport_planet.presentation.base.BaseFragment

class WriteFragment private constructor() :
    BaseFragment<FragmentWriteBinding, WriteViewModel>(R.layout.fragment_write) {
    companion object {
        fun newInstance() = WriteFragment()
    }

    override val viewModel: WriteViewModel = ViewModelProvider(this).get(WriteViewModel::class.java)

    override fun init() {
        val countingList = (1..15).toList()
        val adapter = ArrayAdapter(this, R.layout.item_member_count, countingList.toMutableList())

    }
}