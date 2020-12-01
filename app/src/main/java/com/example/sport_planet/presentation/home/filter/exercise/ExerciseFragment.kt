package com.example.sport_planet.presentation.home.filter.exercise

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentExerciseBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.home.adapter.FilterExerciseGridViewAdapter
import com.example.sport_planet.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class ExerciseFragment private constructor() :
    BaseFragment<FragmentExerciseBinding, ExerciseViewModel>(R.layout.fragment_exercise) {

    override val viewModel: ExerciseViewModel by lazy {
        ViewModelProvider(
            this,
            ExerciseViewModelFactory(RemoteDataSourceImpl())
        ).get(ExerciseViewModel::class.java)
    }

    private val gridAdapter: FilterExerciseGridViewAdapter = FilterExerciseGridViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gvExercise.adapter = gridAdapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            gridAdapter.setItems(it)
        })

        viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { if (it) showLoading() else hideLoading() }
            .addTo(compositeDisposable)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddressCity()
    }

    override fun init() {
    }

    fun getExercise(): String {
        return gridAdapter.getSelectItemsId()
    }

    fun adapterClear() {
        (binding.gvExercise.adapter as? FilterExerciseGridViewAdapter)?.clearSelectItem()
    }

    companion object {
        fun newInstance() = ExerciseFragment()
    }
}