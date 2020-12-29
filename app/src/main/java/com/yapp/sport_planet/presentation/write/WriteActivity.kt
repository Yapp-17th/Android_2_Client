package com.yapp.sport_planet.presentation.write

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.enums.SeparatorEnum
import com.yapp.sport_planet.data.enums.WriteFilterEnum
import com.yapp.sport_planet.data.model.*
import com.yapp.sport_planet.data.response.basic.toCommon
import com.yapp.sport_planet.databinding.FragmentWriteBinding
import com.yapp.sport_planet.presentation.base.BaseActivity
import com.yapp.sport_planet.presentation.home.HomeFragment.Companion.REFRESH
import com.yapp.sport_planet.presentation.write.date.DateDialogFragment
import com.yapp.sport_planet.presentation.write.date.DateListener
import com.yapp.sport_planet.presentation.write.select.SelectFragment
import com.yapp.sport_planet.presentation.write.select.SelectItemListener
import com.yapp.sport_planet.presentation.write.time.TimeDialogFragment
import com.yapp.sport_planet.presentation.write.time.TimeListener
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
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

    private val dateChangeListener: DataChangeListener = object : DataChangeListener {
        override fun onChange(result: Pair<WriteFilterEnum, CommonApiModel?>) {
            when (result.first) {
                WriteFilterEnum.CITY -> viewModel.city.value = result.second!!.toCity()
                WriteFilterEnum.EXERCISE -> viewModel.exercise.value = result.second!!.toExercise()
                WriteFilterEnum.USERTAG -> viewModel.userTag.value = result.second!!.toUserTag()
            }
        }
    }

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
        initTags()

        binding.tvDate.hint =
            SimpleDateFormat(getString(R.string.full_date_format)).format(System.currentTimeMillis())

        binding.toolbar.setBackButtonClick(View.OnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        })

        viewModel.showFinishView.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                setResult(Activity.RESULT_OK)
                finish()
            }
            .addTo(compositeDisposable)

        viewModel.showPostError.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showToast("업로드 실패 모든 항목을 체크하세요")
            }
            .addTo(compositeDisposable)

        viewModel.boardId.observe(this, Observer {
            if (it >= 0)
                viewModel.getBoardContent()
        })

        viewModel.showBoardContent
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                runOnUiThread {
                    viewModel.title.value = it.title
                    binding.edtTitle.setText(it.title)
                    viewModel.body.value = it.content
                    binding.edtBody.setText(it.content)
                    viewModel.place.value = it.place
                    binding.edtPlace.setText(it.place)
                    val index = it.startsAt.indexOf("T")
                    viewModel.time.value = it.startsAt.substring(0, index + 6)
                    binding.tvDate.text = viewModel.getDateToString()
                    viewModel.exercise.value = it.exercise
                    viewModel.city.value = it.city
                    viewModel.userTag.value = it.userTag
                    result[0] = Pair(WriteFilterEnum.EXERCISE, it.exercise.toCommon())
                    result[1] = Pair(WriteFilterEnum.CITY, it.city.toCommon())
                    result[2] = Pair(WriteFilterEnum.USERTAG, it.userTag.toCommon())
                    viewModel.count.value = it.recruitNumber
                    binding.spinnerCount.setSelection(it.recruitNumber - 1)
                    selectViewNotify()
                }
            }
            .addTo(compositeDisposable)

        viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { if (it) showLoading() else hideLoading() }
            .addTo(compositeDisposable)


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

        binding.tvDate.setOnClickListener {
            DateDialogFragment.newInstance().apply {
                setListener(dateListener)
            }.show(supportFragmentManager.beginTransaction(), DATE_DIALOG)
        }

        binding.btnPosting.setOnClickListener {
            if (viewModel.boardId.value!! > -1) viewModel.editBoard() else viewModel.postBoard()
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

        if (intent != null) {
            viewModel.boardId.value = intent.getLongExtra(BOARD_ID, -1)
            binding.btnPosting.text = if (viewModel.boardId.value!! > -1) "게시글 수정하기" else "게시글 올리기"
        }
    }

    private val dateListener: DateListener = object : DateListener {
        override fun confirm(date: String?) {
            TimeDialogFragment.newInstance(date ?: "").apply {
                setListener(timeListener)
            }.show(supportFragmentManager, TIME_DIALOG)
            (supportFragmentManager.findFragmentByTag(DATE_DIALOG) as? DateDialogFragment)?.dismiss()
        }

        override fun cancel() {

        }
    }

    private val timeListener: TimeListener = object : TimeListener {
        override fun confirm(time: String) {
            viewModel.time.value = time
            binding.tvDate.text = viewModel.getDateToString()
            (supportFragmentManager.findFragmentByTag(TIME_DIALOG) as? DateDialogFragment)?.dismiss()
        }

        override fun cancel() {

        }
    }

    private fun initTags() {
        for (position in 0..2) {
            clList[position].setOnClickListener {
                if (result[position].second != null) {
                    result[position] = Pair(items[position], null)
                } else {
                    val fragment = SelectFragment.newInstance(items[position])
                    fragment.setListener(object : SelectItemListener {
                        override fun confirm(model: CommonApiModel) {
                            result[position] = Pair(items[position], model)
                            dateChangeListener.onChange(result = result[position])
                            selectViewNotify()
                        }
                    })
                    fragment.show(supportFragmentManager, "SELECT")
                }
            }
        }
    }

    private fun selectViewNotify() {
        runOnUiThread {
            for (position in 0..2) {
                val isChecked = result[position].second != null
                tvList[position].text =
                    if (isChecked) result[position].second!!.name else items[position].title
                clList[position].setBackgroundResource(if (isChecked) R.drawable.shape_round_corner_into_dark_blue_opacity else R.drawable.shape_round_corner)
                ivList[position].setImageResource(if (isChecked) R.drawable.icons_18_px_x else R.drawable.ic_toggle_off)
            }
        }
    }

    companion object {
        const val DATE_DIALOG = "DATE_DIALOG"
        const val TIME_DIALOG = "TIME_DIALOG"
        const val INTENT_DATE = "INTENT_DATE"
        const val INTENT_TIME = "INTENT_TIME"
        const val BOARD_ID = "BOARD_ID"

        fun createInstance(activity: Activity) {
            val intent = Intent(activity, WriteActivity::class.java)
            activity.startActivityForResult(intent, REFRESH)
        }

        fun createInstance(activity: Activity, boardId: Long) {
            val intent = Intent(activity, WriteActivity::class.java)
            intent.putExtra(BOARD_ID, boardId)
            activity.startActivityForResult(intent, REFRESH)
        }
    }
}

interface DataChangeListener {
    fun onChange(result: Pair<WriteFilterEnum, CommonApiModel?>)
}