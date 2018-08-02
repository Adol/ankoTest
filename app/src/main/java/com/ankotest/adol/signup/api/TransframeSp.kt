package com.ankotest.adol.signup.api

import android.view.View
import com.daimajia.slider.library.Transformers.DepthPageTransformer
import com.nineoldandroids.view.ViewHelper

/**
 **Created by adol on 2018/3/19.
 */
class TransFormSP {
    companion object {
        private var leftOrright = ""
        private var PB = false

        fun onTransform2(view: View, position: Float) {
            DepthPageTransformer().transformPage(view, position)
        }


        fun onTransform(view: View, position: Float) {
            if (position == 0f) {
                PB = false
            }

            if (!PB) {
                if (position > 0 && position < 1) {
                    PB = true
                    leftOrright = if (position < 0.5) "toRight" else "toLeft"
                }
            }

            if (-1 < position && position < 0) {
                ViewHelper.setTranslationX(view, (view.width * position * -0.7).toFloat())
                when (leftOrright) {
                    "toLeft" -> ViewHelper.setAlpha(view, (1 + position) * 2f)
                    "toRight" -> ViewHelper.setAlpha(view, 1 + position)
                }
            }

            if (0 < position && position < 1) {
                when (leftOrright) {
                    "toLeft" -> ViewHelper.setAlpha(view, 1f)
                    "toRight" -> ViewHelper.setAlpha(view, Math.max(0f, (0.8f - position) * 1.5f))
                }
            }
        }
    }
}