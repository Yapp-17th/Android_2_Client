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
import kotlinx.android.synthetic.main.dialog_common.view.*

class CustomDialog : DialogFragment() {
    private lateinit var content: String
    private lateinit var okText: String
    private lateinit var listener: CustomDialogOKClickedListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_common, container, false)
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
        view?.apply {
            dialog?.setCancelable(false)
            dialog_common_content.text = Html.fromHtml(content)
            dialog_common_ok.text = okText
            dialog_common_cancle.setOnClickListener {
                dismiss()
            }
            dialog_common_ok.setOnClickListener {
                listener.onOKClicked()
                dismiss()
            }
        }
    }

    class CustomDialogBuilder {
        private val dialog = CustomDialog()

        fun setContent(content: String): CustomDialogBuilder{
            dialog.content = content
            return this
        }

        fun setOKText(text: String):CustomDialogBuilder{
            dialog.okText = text
            return this
        }

        fun setOnOkClickedListener(listener: () -> Unit): CustomDialogBuilder{
            dialog.listener = object : CustomDialogOKClickedListener{
                override fun onOKClicked() {
                    listener()
                }
            }
            return this
        }

        fun create() = dialog
    }

    interface CustomDialogOKClickedListener {
        fun onOKClicked()
    }

}