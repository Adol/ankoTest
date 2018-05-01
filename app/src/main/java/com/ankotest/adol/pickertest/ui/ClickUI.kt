package com.ankotest.adol.pickertest.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

class SetBG {
    val bg = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 20f
        setStroke(3, Color.WHITE)
    }
}

