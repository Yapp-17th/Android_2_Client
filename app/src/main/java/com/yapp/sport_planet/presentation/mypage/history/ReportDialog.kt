package com.yapp.sport_planet.presentation.mypage.history

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.DialogReportBinding
import com.yapp.sport_planet.presentation.base.BaseDialogFragment

class ReportDialog : BaseDialogFragment<DialogReportBinding>(R.layout.dialog_report) {
    private var mListener: ReportDialogListener? = null
    private val textViewList: MutableList<TextView> by lazy {
        binding.run {
            mutableListOf(
                tv1,
                tv2,
                tv3,
                tv4,
                tv5,
                tv6,
                tv7,
                tv8
            )
        }
    }
    private var index = 0L

    interface ReportDialogListener {
        fun onAccept(index: Long, content: String? = null)
    }

    fun setReportDialogListener(listener: ReportDialogListener) {
        mListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            tv1.setOnClickListener {
                setAllTextColorAndBackground(4L, tv1)
            }
            tv2.setOnClickListener {
                setAllTextColorAndBackground(5L, tv2)
            }
            tv3.setOnClickListener {
                setAllTextColorAndBackground(6L, tv3)
            }
            tv4.setOnClickListener {
                setAllTextColorAndBackground(7L, tv4)
            }
            tv5.setOnClickListener {
                setAllTextColorAndBackground(8L, tv5)
            }
            tv6.setOnClickListener {
                setAllTextColorAndBackground(9L, tv6)
            }
            tv7.setOnClickListener {
                setAllTextColorAndBackground(10L, tv7)
            }
            tv8.setOnClickListener {
                (tvCancel.layoutParams as? ConstraintLayout.LayoutParams)?.matchConstraintPercentHeight = 0.3f
                (tvReport.layoutParams as? ConstraintLayout.LayoutParams)?.matchConstraintPercentHeight = 0.3f
                textViewList.forEach {
                    it.visibility = View.GONE
                }
                tvTitle.text = "기타 :"
                etEtc.visibility = View.VISIBLE
                index = 0L
            }
            tvCancel.setOnClickListener {
                dismiss()
            }
            tvReport.setOnClickListener {
                if (index == 0L) {
                    if (binding.etEtc.text.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "내용을 입력하세요.", Toast.LENGTH_LONG).show()
                    } else {
                        mListener?.onAccept(index, binding.etEtc.text.toString())
                    }
                } else {
                    mListener?.onAccept(index)
                }
                dismiss()
            }
        }

    }

    private fun setAllTextColorAndBackground(indexNum: Long, textView: TextView) {
        index = indexNum
        setTextColorAndBackground(textView, true)
        textViewList.filter {
            textView.id != it.id
        }.forEach {
            setTextColorAndBackground(it, false)
        }
    }

    private fun setTextColorAndBackground(textView: TextView, boolean: Boolean) {
        textView.run {
            if (boolean) {
                setBackgroundResource(R.drawable.shape_round_corner_darkblue_opacity_no_stoke)
                setTextColor(resources.getColor(R.color.dark_blue, null))
            } else {
                setBackgroundResource(R.drawable.shape_round_corner_white_gray)
                setTextColor(resources.getColor(R.color.gray, null))
            }
        }

    }

    companion object {
        fun newInstance(
            dialogHeightRatio: Float? = null
        ) = ReportDialog().apply {
            arguments = Bundle().apply {
                if (dialogHeightRatio != null) {
                    putFloat(DIALOG_HEIGHT_RATIO, dialogHeightRatio)
                }
            }
        }
    }

}