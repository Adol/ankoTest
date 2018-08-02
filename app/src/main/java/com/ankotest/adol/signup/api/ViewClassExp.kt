package com.ankotest.adol.signup.api

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import org.jetbrains.anko.support.v4._ViewPager

class ViewClass(ctx: Context) : _ViewPager(ctx) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (EventVar.fragmentTrans) {
            false -> return false
        }
        return super.onInterceptTouchEvent(ev)
    }
}

class SetBG {
    val bg = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 20f
        setStroke(3, Color.WHITE)
    }
}