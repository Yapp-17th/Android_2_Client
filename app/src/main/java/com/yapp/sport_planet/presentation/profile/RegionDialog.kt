package com.yapp.sport_planet.presentation.profile

import android.os.Bundle
import android.view.View
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.DialogRegionBinding
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.presentation.base.BaseDialogFragment

class RegionDialog : BaseDialogFragment<DialogRegionBinding>(R.layout.dialog_region) {

    private var mListener: SelectDialogListener? = null
    val item = mutableListOf<String>()

    interface SelectDialogListener {
        fun onAccept(item: String,id: Long)
    }

    fun setSelectDialogListener(listener: SelectDialogListener) {
        mListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getStringArrayList("dialogItemList")?.toMutableList()?.let { item.addAll(it) }
        binding.run {
            tvTitle.text = arguments?.getString("dialogTitleText")
            rvContent.adapter = RegionAdapter(::getItem).apply {
                setItem(item)
            }
        }

    }

    private fun getItem(item: String,id:Long) {
        mListener?.onAccept(item,id)
        dismiss()
    }

    companion object {
        fun newInstance(
            dialogTitleText: String,
            dialogHeightRatio: Float? = null,
            dialogItemList: List<RegionResponse.Data>
        ) = RegionDialog().apply {
            arguments = Bundle().apply {
                if (dialogHeightRatio != null) {
                    putFloat(DIALOG_HEIGHT_RATIO, dialogHeightRatio)
                }
                val regionArrayList :ArrayList<String> = ArrayList()
                dialogItemList.forEach {
                    regionArrayList.add(it.name)
                }
                putString("dialogTitleText", dialogTitleText)
                putStringArrayList("dialogItemList", regionArrayList)
            }
        }
    }
}