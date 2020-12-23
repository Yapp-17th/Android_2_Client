package com.example.sport_planet.presentation.home.filter.exercise

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.response.basic.ExerciseResponse
import com.example.sport_planet.databinding.FragmentExerciseBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.home.adapter.FilterExerciseGridViewAdapter
import com.example.sport_planet.presentation.home.filter.FilterActivity
import com.example.sport_planet.presentation.home.filter.FilterActivity.Companion.INTENT_EXERCISE
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

    private lateinit var gridAdapter: FilterExerciseGridViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridAdapter = FilterExerciseGridViewAdapter {
            viewModel.clickItems(it)
        }
        binding.gvExercise.adapter = gridAdapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            gridAdapter.setItems(it)
        })

        viewModel.selectedItems.observe(viewLifecycleOwner, Observer {
            gridAdapter.setSelectedItems(it)
            (activity as? FilterActivity)?.getExercise(it)
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
            arguments?.getParcelableArrayList(INTENT_EXERCISE) ?: emptyList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddressCity()
    }

    override fun init() {
    }

    fun getExercise(): List<ExerciseResponse.Data> {
        return viewModel.items.value ?: emptyList()
    }

    fun adapterClear() {
        viewModel.selectedItems.value = emptyList()
    }

    companion object {
        fun newInstance(exercise: List<ExerciseResponse.Data>): ExerciseFragment {
            val args = Bundle()
            args.putParcelableArrayList(INTENT_EXERCISE, ArrayList(exercise))

            val fragment = ExerciseFragment()
            fragment.arguments = args
            return fragment
        }
    }
}