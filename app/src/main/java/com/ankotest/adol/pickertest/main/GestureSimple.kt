package com.ankotest.adol.pickertest.main

import android.view.GestureDetector
import android.view.MotionEvent

class UserCheckGestureSimple : GestureDetector.SimpleOnGestureListener() {
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return true
    }
}