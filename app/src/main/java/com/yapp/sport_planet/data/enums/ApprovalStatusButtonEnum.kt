package com.yapp.sport_planet.data.enums

import com.yapp.sport_planet.R

enum class ApprovalStatusButtonEnum {
    HOST_NONE {
        override val buttonText: String
            get() = "NONE"
        override val textColorId: Int
            get() = R.color.black121212
        override val backgroundColorId: Int
            get() = R.color.white
        override val strokeColorId: Int
            get() = R.color.black121212
        override val isClickable: Boolean
            get() = false
    },
    HOST_APPROVE {
        override val buttonText: String
            get() = "승인하기"
        override val textColorId: Int
            get() = R.color.white
        override val backgroundColorId: Int
            get() = R.color.blue364ea0
        override val strokeColorId: Int
            get() = R.color.blue364ea0
        override val isClickable: Boolean
            get() = true
    },
    HOST_APPROVE_CANCLE {
        override val buttonText: String
            get() = "승인취소"
        override val textColorId: Int
            get() = R.color.blue364ea0
        override val backgroundColorId: Int
            get() = R.color.white
        override val strokeColorId: Int
            get() = R.color.blue364ea0
        override val isClickable: Boolean
            get() = true
    },
    GUEST_APPLY {
        override val buttonText: String
            get() = "신청하기"
        override val textColorId: Int
            get() = R.color.white
        override val backgroundColorId: Int
            get() = R.color.blue364ea0
        override val strokeColorId: Int
            get() = R.color.blue364ea0
        override val isClickable: Boolean
            get() = true
    },
    GUEST_APPROVE_AWAIT {
        override val buttonText: String
            get() = "승인대기중"
        override val textColorId: Int
            get() = R.color.gray9c9c9c
        override val backgroundColorId: Int
            get() = R.color.grayefefef
        override val strokeColorId: Int
            get() = R.color.gray9c9c9c
        override val isClickable: Boolean
            get() = false
    },
    GUEST_APPROVE_SUCCESS {
        override val buttonText: String
            get() = "승인완료"
        override val textColorId: Int
            get() = R.color.white
        override val backgroundColorId: Int
            get() = R.color.gray9c9c9c
        override val strokeColorId: Int
            get() = R.color.gray9c9c9c
        override val isClickable: Boolean
            get() = false
    };
    abstract val buttonText: String
    abstract val textColorId: Int
    abstract val backgroundColorId: Int
    abstract val strokeColorId: Int
    abstract val isClickable: Boolean
}