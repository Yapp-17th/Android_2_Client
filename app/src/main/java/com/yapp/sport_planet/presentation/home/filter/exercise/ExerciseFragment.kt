package com.yapp.sport_planet.presentation.home.filter.exercise

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.databinding.FragmentExerciseBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.home.adapter.FilterExerciseGridViewAdapter
import com.yapp.sport_planet.presentation.home.filter.FilterActivity
import com.yapp.sport_planet.presentation.home.filter.FilterActivity.Companion.INTENT_EXERCISE
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
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

    private val defaultClick = listOf(ExerciseResponse.Data(0, "전체"))

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
            arguments?.getParcelableArrayList(INTENT_EXERCISE) ?: defaultClick
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddressCity()
    }

    override fun init() {
    }

    fun getExercise(): List<ExerciseResponse.Data> {
        return viewModel.items.value ?: defaultClick
    }

    fun clearExercise() {
        viewModel.selectedItems.value = defaultClick
    }

    companion object {
        fun newInstance(exercise: List<ExerciseResponse.Data>): ExerciseFragment {
            val args = Bundle()
            args.putParcelableArrayList(
                INTENT_EXERCISE,
                ArrayList(
                    if (exercise.isNotEmpty()) {
                        exercise
                    } else {
                        listOf(
                            ExerciseResponse.Data(
                                id = 0,
                                name = "전체"
                            )
                        )
                    }
                )
            )

            val fragment = ExerciseFragment()
            fragment.arguments = args
            return fragment
        }
    }
}