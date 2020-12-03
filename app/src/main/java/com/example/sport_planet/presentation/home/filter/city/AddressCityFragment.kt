package com.example.sport_planet.presentation.home.filter.city

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentAddressCityBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.home.adapter.FilterCityGridViewAdapter
import com.example.sport_planet.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class AddressCityFragment private constructor() :
    BaseFragment<FragmentAddressCityBinding, AddressCityViewModel>(R.layout.fragment_address_city) {
    override val viewModel: AddressCityViewModel by lazy {
        ViewModelProvider(
            this,
            AddressCityViewModelFactory(RemoteDataSourceImpl())
        ).get(AddressCityViewModel::class.java)
    }

    private val gridAdapter: FilterCityGridViewAdapter = FilterCityGridViewAdapter()

    override fun init() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gvCity.adapter = gridAdapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            gridAdapter.setItems(it)
        })

        viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { if (it) showLoading() else hideLoading() }
            .addTo(compositeDisposable)
    }

    fun getCity(): String {
        return gridAdapter.getSelectItemsId()
    }

    fun adapterClear() {
        (binding.gvCity.adapter as? FilterCityGridViewAdapter)?.clearSelectItem()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddressCity()
    }

    companion object {
        fun newInstance() = AddressCityFragment()
    }
}