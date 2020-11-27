package com.example.sport_planet.presentation.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.sport_planet.R
import kotlinx.android.synthetic.main.item_custom_dialog.*

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
        val view = inflater.inflate(R.layout.item_custom_dialog, container, false)
        return view.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.apply {
            item_custom_dialog_content.text = content
            item_custom_dialog_ok.text = okText
            item_custom_dialog_cancle.setOnClickListener {
                dismiss()
            }
            item_custom_dialog_ok.setOnClickListener {
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