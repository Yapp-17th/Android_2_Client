package com.yapp.sport_planet.presentation.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ItemCustomApprovalButtonBinding
import com.yapp.sport_planet.data.enums.ApprovalStatusButtonEnum

class CustomApprovalStatusButton: ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private val binding: ItemCustomApprovalButtonBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.item_custom_approval_button,
        this,
        true
    )

    fun setApprovalStatusButton(item: ApprovalStatusButtonEnum) {
        when(item){
            ApprovalStatusButtonEnum.HOST_NONE -> binding.btCustomApprovalButton.visibility = View.GONE
            else -> {
                binding.btCustomApprovalButton.run {
                    if(binding.btCustomApprovalButton.visibility == View.GONE)
                        binding.btCustomApprovalButton.visibility = View.VISIBLE
                    this.text = item.buttonText
                    this.setTextColor(context.getColor(item.textColorId))
                    this.strokeColor = ColorStateList.valueOf(context.getColor(item.strokeColorId))
                    this.setBackgroundColor(context.getColor(item.backgroundColorId))
                    this.isClickable = item.isClickable
                }
            }
        }
    }
}

