package com.yapp.sport_planet.presentation.board

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.DialogBoardReportBinding
import com.yapp.sport_planet.presentation.base.BaseDialogFragment

class BoardReportDialog : BaseDialogFragment<DialogBoardReportBinding>(R.layout.dialog_board_report) {
    private var mListener: BoardReportDialogListener? = null
    private val textViewList: MutableList<TextView> by lazy {
        binding.run {
            mutableListOf(
                tv1,
                tv2,
                tv3,
                tv4
            )
        }
    }
    private var index = 0L

    interface BoardReportDialogListener {
        fun onAccept(index: Long, content: String? = null)
    }

    fun setBoardReportDialogListener(listener: BoardReportDialogListener) {
        mListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            tv1.setOnClickListener {
                setAllTextColorAndBackground(1L, tv1)
            }
            tv2.setOnClickListener {
                setAllTextColorAndBackground(2L, tv2)
            }
            tv3.setOnClickListener {
                setAllTextColorAndBackground(3L, tv3)
            }
            tv4.setOnClickListener {
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
        ) = BoardReportDialog().apply {
            arguments = Bundle().apply {
                if (dialogHeightRatio != null) {
                    putFloat(DIALOG_HEIGHT_RATIO, dialogHeightRatio)
                }
            }
        }
    }

}