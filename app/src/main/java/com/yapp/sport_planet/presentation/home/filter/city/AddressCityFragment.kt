package com.yapp.sport_planet.presentation.home.filter.city

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.databinding.FragmentAddressCityBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.home.adapter.FilterCityGridViewAdapter
import com.yapp.sport_planet.presentation.home.filter.FilterActivity
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
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

    private val defaultClick = listOf(RegionResponse.Data(0, "전체"))

    private lateinit var gridAdapter: FilterCityGridViewAdapter

    override fun init() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridAdapter = FilterCityGridViewAdapter {
            viewModel.clickItems(it)
        }
        binding.gvCity.adapter = gridAdapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            gridAdapter.setItems(it)
            Log.d("okhttp", "selectedItem : ${viewModel.selectedItems.value}")
        })

        viewModel.selectedItems.observe(viewLifecycleOwner, Observer {
            gridAdapter.setSelectedItems(it)
            (activity as? FilterActivity)?.getCity(it)
        })

        viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { if (it) showLoading() else hideLoading() }
            .addTo(compositeDisposable)

        viewModel.showErrorToast
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showToast("최대 3개까지 선택 가능합니다") }
            .addTo(compositeDisposable)

        viewModel.selectedItems.value =
            arguments?.getParcelableArrayList(FilterActivity.INTENT_CITY) ?: defaultClick
    }

    fun getCity(): List<RegionResponse.Data> {
        return viewModel.items.value ?: emptyList()
    }

    fun clearCity() {
        viewModel.selectedItems.value = defaultClick
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddressCity()
    }

    companion object {
        fun newInstance(city: List<RegionResponse.Data>): AddressCityFragment {
            val args = Bundle()
            args.putParcelableArrayList(
                FilterActivity.INTENT_CITY,
                ArrayList(if (city.isNotEmpty()) city else listOf(RegionResponse.Data(0, "전체")))
            )

            val fragment = AddressCityFragment()
            fragment.arguments = args
            return fragment
        }
    }
}