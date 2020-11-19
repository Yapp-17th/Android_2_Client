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
import com.example.sport_planet.data.enums.ApprovalStatusButtonEnum
import com.example.sport_planet.presentation.chatting.ChattingConstant

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
                    this.background.setTint(context.getColor(item.backgroundColorId))
                    this.strokeColor = ColorStateList.valueOf(context.getColor(item.strokeColorId))
                }
            }
        }
    }

    fun approvalStatus(isHost: Boolean, status: String): ApprovalStatusButtonEnum {
        return when(isHost){
            false -> when(status){
                ChattingConstant.PENDING_STATUS -> ApprovalStatusButtonEnum.GUEST_APPLY
                ChattingConstant.APPLIED_STATUS -> ApprovalStatusButtonEnum.GUEST_APPROVE_AWAIT
                ChattingConstant.APPROVED_STATUS -> ApprovalStatusButtonEnum.GUEST_APPROVE_SUCCESS
                ChattingConstant.DISAPPROVED_STATUS -> ApprovalStatusButtonEnum.GUEST_APPROVE_AWAIT
                else -> throw IllegalArgumentException("적절하지 않은 Guest AppliedStatus")
            }
            true  -> when(status){
                ChattingConstant.PENDING_STATUS -> ApprovalStatusButtonEnum.HOST_NONE
                ChattingConstant.APPLIED_STATUS -> ApprovalStatusButtonEnum.HOST_APPROVE
                ChattingConstant.APPROVED_STATUS -> ApprovalStatusButtonEnum.HOST_APPROVE_CANCLE
                ChattingConstant.DISAPPROVED_STATUS -> ApprovalStatusButtonEnum.HOST_APPROVE
                else -> throw IllegalArgumentException("적절하지 않은 Host AppliedStatus")
            }
        }
    }
}

