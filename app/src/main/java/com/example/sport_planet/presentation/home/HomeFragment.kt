package com.example.sport_planet.presentation.home

import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentHomeBinding
import com.example.sport_planet.model.enums.MenuEnum
import com.example.sport_planet.model.enums.SeparatorEnum
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.home.adapter.HomeRecyclerAdapter


class HomeFragment private constructor() :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    companion object {
        fun newInstance() = HomeFragment()
    }

    override val viewModel: HomeViewModel
            by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }


    override fun init() {
        activity?.runOnUiThread {
            binding.toolbar?.run {
                binding.toolbar.setSeparator(SeparatorEnum.GUEST)
                binding.toolbar.setMenu(MenuEnum.MENU)
            }
        }

        binding.vm = viewModel

        binding.recBoard.adapter = HomeRecyclerAdapter()

        registerForContextMenu(binding.clFilterTime)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        activity?.run {
            this.menuInflater.inflate(R.menu.menu_home_filter_time, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_recent -> {

            }
            R.id.filter_person -> {

            }
            R.id.filter_time -> {

            }
        }
        return true
    }
}