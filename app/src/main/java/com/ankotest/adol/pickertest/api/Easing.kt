package com.ankotest.adol.pickertest.api

import android.graphics.Path
import android.support.v4.view.animation.PathInterpolatorCompat
import android.view.animation.Interpolator

class Easing {
    companion object {
        fun Type(type:Path):Interpolator{
            return PathInterpolatorCompat.create(type)
        }

        val easeInSine by lazy {
            Path().apply {
                quadTo(0.47f, 0f, 0.745f, 0.715f)
                lineTo(1f, 1f)
            }
        }
        val easeOutSine by lazy {
            Path().apply {
                quadTo(0.39f, 0.575f, 0.565f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInOutSine by lazy {
            Path().apply {
                quadTo(0.445f, 0.05f, 0.55f, 0.95f)
                lineTo(1f, 1f)
            }
        }
        val easeInQuad by lazy {
            Path().apply {
                quadTo(0.55f, 0.085f,0.68f, 0.53f)
                lineTo(1f, 1f)
            }
        }
        val easeOutQuad by lazy {
            Path().apply {
                quadTo(0.25f, 0.46f, 0.45f, 0.94f)
                lineTo(1f, 1f)
            }
        }
        val easeInOutQuad by lazy {
            Path().apply {
                quadTo(0.455f, 0.03f, 0.515f, 0.955f)
                lineTo(1f, 1f)
            }
        }
        val easeInCubic by lazy {
            Path().apply {
                quadTo(0.55f, 0.055f, 0.675f, 0.19f)
                lineTo(1f, 1f)
            }
        }
        val easeOutCubic by lazy {
            Path().apply {
                quadTo(0.215f, 0.61f, 0.355f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInOutCubic by lazy {
            Path().apply {
                quadTo(0.645f, 0.045f, 0.355f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInQuart by lazy {
            Path().apply {
                quadTo(0.895f, 0.03f, 0.685f, 0.22f)
                lineTo(1f, 1f)
            }
        }
        val easeOutQuart by lazy {
            Path().apply {
                quadTo(0.165f, 0.84f, 0.44f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInOutQuart by lazy {
            Path().apply {
                quadTo(0.77f, 0f, 0.175f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInQuint by lazy {
            Path().apply {
                quadTo(0.39f, 0.575f, 0.565f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeOutQuint by lazy {
            Path().apply {
                quadTo(0.39f, 0.575f, 0.565f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInOutQuint by lazy {
            Path().apply {
                quadTo(0.755f, 0.05f, 0.855f, 0.06f)
                lineTo(1f, 1f)
            }
        }
        val easeInExpo by lazy {
            Path().apply {
                quadTo(0.95f, 0.05f, 0.795f, 0.035f)
                lineTo(1f, 1f)
            }
        }
        val easeOutExpo by lazy {
            Path().apply {
                quadTo(0.19f, 1f, 0.22f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInOutExpo by lazy {
            Path().apply {
                quadTo(1f, 0f, 0f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInCirc by lazy {
            Path().apply {
                quadTo(0.6f, 0.04f, 0.98f, 0.335f)
                lineTo(1f, 1f)
            }
        }
        val easeOutCirc by lazy {
            Path().apply {
                quadTo(0.075f, 0.82f, 0.165f, 1f)
                lineTo(1f, 1f)
            }
        }
        val easeInOutCirc by lazy {
            Path().apply {
                quadTo(0.785f, 0.135f, 0.15f, 0.86f)
                lineTo(1f, 1f)
            }
        }
        val easeInBack by lazy {
            Path().apply {
                quadTo(0.6f, -0.28f, 0.735f, 0.045f)
                lineTo(1f, 1f)
            }
        }
        val easeOutBack by lazy {
            Path().apply {
                quadTo(0.175f, 0.885f, 0.32f, 1.275f)
                lineTo(1f, 1f)
            }
        }
        val easeInOutBack by lazy {
            Path().apply {
                quadTo(0.68f, -0.55f, 0.265f, 1.55f)
                lineTo(1f, 1f)
            }
        }
    }
}