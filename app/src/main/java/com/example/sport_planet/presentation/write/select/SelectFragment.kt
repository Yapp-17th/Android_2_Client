package com.example.sport_planet.presentation.write.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentSelectBinding
import com.example.sport_planet.presentation.write.adapter.SelectGridViewAdapter

class SelectFragment private constructor(): DialogFragment() {
    private lateinit var binding: FragmentSelectBinding
//    private lateinit var adapter: SelectGridViewAdapter
    private var listener: SelectItemListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.gvList.adapter = adapter
    }

    fun setListener(listener: SelectItemListener) {
        this.listener = listener
    }

    companion object {
        @JvmStatic
        fun <T> newInstance() = SelectFragment()
    }
}

interface SelectItemListener {
    fun <T> confirm(model: T)
}