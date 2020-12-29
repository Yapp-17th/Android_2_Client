package com.yapp.sport_planet.presentation.write.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.enums.WriteFilterEnum
import com.yapp.sport_planet.data.model.CommonApiModel
import com.yapp.sport_planet.databinding.FragmentSelectBinding
import com.yapp.sport_planet.presentation.write.adapter.SelectGridViewAdapter
import com.yapp.sport_planet.remote.RemoteDataSourceImpl

class SelectFragment : DialogFragment() {
    private lateinit var binding: FragmentSelectBinding
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            SelectViewModelFactory(RemoteDataSourceImpl())
        ).get(SelectViewModel::class.java)
    }
    private lateinit var adapter: SelectGridViewAdapter
    private var listener: SelectItemListener? = null

    private lateinit var type: WriteFilterEnum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getString("DATA")?.run {
                type = WriteFilterEnum.values().firstOrNull { this == it.name } ?: WriteFilterEnum.NONE
                if (type == WriteFilterEnum.NONE) dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SelectGridViewAdapter {
            listener?.confirm(it)
            dismiss()
        }
        binding.gvList.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
    }

    override fun onResume() {
        super.onResume()
        if (type != null && type != WriteFilterEnum.NONE) {
            when (type) {
                WriteFilterEnum.CITY -> viewModel.getCity()
                WriteFilterEnum.EXERCISE -> viewModel.getExercise()
                WriteFilterEnum.USERTAG -> viewModel.getUserTag()
                else -> dismiss()
            }
        }
    }

    fun setListener(listener: SelectItemListener) {
        this.listener = listener
    }

    companion object {
        fun newInstance(item: WriteFilterEnum): SelectFragment {
            val args = Bundle()
            args.putString("DATA", item.name)
            val fragment = SelectFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

interface SelectItemListener {
    fun confirm(model: CommonApiModel)
}