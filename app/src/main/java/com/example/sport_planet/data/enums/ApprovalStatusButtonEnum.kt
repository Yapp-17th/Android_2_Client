package com.example.sport_planet.data.enums

import com.example.sport_planet.R

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
    };
    abstract val buttonText: String
    abstract val textColorId: Int
    abstract val backgroundColorId: Int
    abstract val strokeColorId: Int
}