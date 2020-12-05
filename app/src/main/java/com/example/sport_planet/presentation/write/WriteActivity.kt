package com.example.sport_planet.presentation.write

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.data.enums.WriteFilterEnum
import com.example.sport_planet.data.model.CommonApiModel
import com.example.sport_planet.data.model.toCity
import com.example.sport_planet.data.model.toExercise
import com.example.sport_planet.data.model.toUserTag
import com.example.sport_planet.databinding.FragmentWriteBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.write.adapter.WriteGridViewAdapter
import com.example.sport_planet.presentation.write.date.DateDialogFragment
import com.example.sport_planet.presentation.write.date.DateListener
import com.example.sport_planet.presentation.write.select.SelectFragment
import com.example.sport_planet.presentation.write.select.SelectItemListener
import com.example.sport_planet.presentation.write.time.TimeDialogFragment
import com.example.sport_planet.presentation.write.time.TimeListener
import com.example.sport_planet.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import java.text.SimpleDateFormat

class WriteActivity : BaseActivity<FragmentWriteBinding>(R.layout.fragment_write) {

    private val viewModel: WriteViewModel by lazy {
        ViewModelProvider(
            this,
            WriteViewModelFactory(RemoteDataSourceImpl())
        ).get(WriteViewModel::class.java)
    }

    private val dateDialog: DateDialogFragment by lazy {
        DateDialogFragment.newInstance().apply {
            setListener(dateListener)
        }
    }

    private val timeDialog: TimeDialogFragment by lazy {
        TimeDialogFragment.newInstance().apply {
            setListener(timeListener)
        }
    }

    private val dateChangeListener: DataChangeListener = object : DataChangeListener {
        override fun onChange(result: Pair<WriteFilterEnum, CommonApiModel?>) {
            when (result.first) {
                WriteFilterEnum.CITY -> viewModel.city.value = result.second!!.toCity()
                WriteFilterEnum.EXERCISE -> viewModel.exercise.value = result.second!!.toExercise()
                WriteFilterEnum.USERTAG -> viewModel.userTag.value = result.second!!.toUserTag()
            }
        }
    }

    private lateinit var gridAdapter: WriteGridViewAdapter

    private val items: List<WriteFilterEnum> =
        listOf(WriteFilterEnum.EXERCISE, WriteFilterEnum.CITY, WriteFilterEnum.USERTAG)
    private var result: ArrayList<Pair<WriteFilterEnum, CommonApiModel?>> = arrayListOf(
        Pair(WriteFilterEnum.EXERCISE, null),
        Pair(WriteFilterEnum.CITY, null),
        Pair(WriteFilterEnum.USERTAG, null)
    )
    private lateinit var clList: List<ConstraintLayout>
    private lateinit var tvList: List<TextView>
    private lateinit var ivList: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clList = listOf(binding.clExercise, binding.clCity, binding.clTag)
        tvList = listOf(binding.tvExerciseTitle, binding.tvCityTitle, binding.tvTagTitle)
        ivList = listOf(binding.ivExercise, binding.ivCity, binding.ivTag)

        for (i in 0..2) {
            inflatePopUp(i)
        }

        binding.toolbar.setBackButtonClick(View.OnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        })

        viewModel.showFinishView.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showToast("업로드 성공")
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
            .addTo(compositeDisposable)

        viewModel.showPostError.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showToast("업로드 실패 모든 항목을 체크하세요")
            }
            .addTo(compositeDisposable)

        gridAdapter = WriteGridViewAdapter(supportFragmentManager, dateChangeListener)

        binding.toolbar.run {
            setSeparator(SeparatorEnum.NONE)
        }

        binding.spinnerCount.adapter =
            ArrayAdapter.createFromResource(
                applicationContext,
                R.array.count_list,
                R.layout.item_member_count
            )

        binding.spinnerCount.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

        binding.tvDate.hint =
            SimpleDateFormat(getString(R.string.write_date_format)).format(System.currentTimeMillis()) + " " + SimpleDateFormat(
                getString(R.string.write_time_format)
            ).format(System.currentTimeMillis())

        binding.tvDate.setOnClickListener {
            dateDialog.show(supportFragmentManager.beginTransaction(), DATE_DIALOG)
        }

        binding.btnPosting.setOnClickListener {
            viewModel.postBoard()
        }

        binding.spinnerCount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.count.value = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.edtTitle.addTextChangedListener {
            viewModel.title.value = it.toString()
        }

        binding.edtBody.addTextChangedListener {
            viewModel.body.value = it.toString()
        }

        binding.edtPlace.addTextChangedListener {
            viewModel.place.value = it.toString()
        }
    }

    private val dateListener: DateListener = object : DateListener {
        override fun confirm(date: String?) {
            viewModel.date.value = date
            timeDialog.show(supportFragmentManager.beginTransaction(), TIME_DIALOG)
            dateDialog.dismiss()
        }

        override fun cancel() {
            viewModel.clearDateAndTime()
        }
    }

    private val timeListener: TimeListener = object : TimeListener {
        override fun confirm(time: String) {
            viewModel.time.value = "${time.substring(0, 2)}:${time.substring(2, 4)}"
            binding.tvDate.text = viewModel.getDate()
            timeDialog.dismiss()
            // api call
        }

        override fun cancel() {
            viewModel.clearDateAndTime()
        }
    }

    private fun inflatePopUp(position: Int) {

        clList[position].setOnClickListener {
            if (result[position].second != null) {
                result[position] = Pair(items[position], null)
            } else {
                val fragment = SelectFragment.newInstance(items[position])
                fragment.setListener(object : SelectItemListener {
                    override fun confirm(model: CommonApiModel) {
                        result[position] = Pair(items[position], model)
                        dateChangeListener.onChange(result = result[position])
                        runOnUiThread {
                            val isChecked = result[position].second != null
                            tvList[position].text =
                                if (isChecked) result[position].second!!.name else items[position].title
                            clList[position].setBackgroundResource(if (isChecked) R.drawable.shape_round_corner_into_dark_blue_opacity else R.drawable.shape_round_corner)
                            ivList[position].setImageResource(if (isChecked) R.drawable.icons_18_px_x else R.drawable.ic_toggle_off)
                        }
                    }
                })
                fragment.show(supportFragmentManager, "SELECT")
            }
        }
    }

    companion object {
        const val DATE_DIALOG = "DATE_DIALOG"
        const val TIME_DIALOG = "TIME_DIALOG"
    }
}

interface DataChangeListener {
    fun onChange(result: Pair<WriteFilterEnum, CommonApiModel?>)
}