package com.example.sport_planet.presentation.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemCustomApprovalButtonBinding
import com.example.sport_planet.model.enums.ApprovalStatusButtonEnum

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
                    this.text = item.buttonText
                    this.setTextColor(context.getColor(item.textColorId))
                    this.background.setTint(context.getColor(item.backgroundColorId))
                    this.strokeColor = ColorStateList.valueOf(context.getColor(item.strokeColorId))
                }
            }
        }
    }
}

