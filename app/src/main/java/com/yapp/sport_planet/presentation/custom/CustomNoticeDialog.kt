package com.yapp.sport_planet.presentation.custom

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.yapp.sport_planet.R
import kotlinx.android.synthetic.main.dialog_notice.view.*

class CustomNoticeDialog : DialogFragment() {
    private lateinit var listener: CustomNoticeDialogOKClickedListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_notice, container, false)
        return view.rootView
    }

    override fun onResume() {
        super.onResume()

        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.setCancelable(false)
        view?.apply {
            dialog_notice_content1.text = Html.fromHtml(getString(R.string.dialog_notice_content1))
            dialog_notice_ok.setOnClickListener {
                listener.onOKClicked()
                dismiss()
            }
        }
    }

    class CustomNoticeDialogBuilder {
        private val dialog = CustomNoticeDialog()

        fun setOnOkClickedListener(listener: () -> Unit): CustomNoticeDialogBuilder {
            dialog.listener = object : CustomNoticeDialogOKClickedListener {
                override fun onOKClicked() {
                    listener()
                }
            }
            return this
        }

        fun create() = dialog
    }

    interface CustomNoticeDialogOKClickedListener {
        fun onOKClicked()
    }

}