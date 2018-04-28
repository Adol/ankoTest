package com.ankotest.adol.pickertest.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class SetBG {
    val bg = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 20f
        setStroke(3, Color.WHITE)
    }
}


class RichView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr)
//    private lateinit var image: ImageView
//    private lateinit var text: TextView
//
//    private fun init() = AnkoContext.createDelegate(this).apply {
//        gravity = Gravity.CENTER
//        padding = dip(24)

//        image = imageView(imageResource = R.drawable.kotlin) {
//            onClick { startAnimation() }
//
//            padding = dip(8)
//            layoutParams = LinearLayout.LayoutParams(dip(48), dip(48))
//        }

//

//    private fun startAnimation() {
//        if (image.animation != null) return
//        text = textView("Anko Rich view") {
//            textSize = 19f
//        }
////        startAnimation()
//    }
//        image.startAnimation(ScaleAnimation(1f, 1.3f, 1f, 1.3f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
//        ).apply {
//            repeatCount = 1
//            repeatMode = Animation.REVERSE
//            duration = 1000
//        })
//    }
//}

//@Suppress("NOTHING_TO_INLINE")
//inline fun ViewManager.myRichView(theme: Int = 0) = myRichView(theme) {}
//inline fun ViewManager.myRichView(theme: Int = 0, init: RichView.() -> Unit) = ankoView(::RichView, theme, init)

//class ClickUI constructor(val ctx: Context,private val type: Int = 1, private val size: Int = 120):View {
//class ClickUI : View {
//    private var sourceImage: Int = R.drawable.check_box_1
//    var type = 2
//    var size = 120
//
//    init {
//        when (type) {
//            0 -> sourceImage = R.drawable.check_close
//            1 -> sourceImage = R.drawable.check_box_1
//            2 -> sourceImage = R.drawable.check_box_2
//        }
//    }
//
//    var click = false
//    lateinit var img: ImageView
//
//    constructor(ctx: Context) : super(ctx) {
//        "constructor".pln()
//    }
//    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
//    fun getUI(VM: ViewManager): ViewManager {
//        return VM.apply {
//            linearLayout {
//                lparams(width = 300)
//                gravity = Gravity.CENTER
//                background = SetBG().bg
//                img = imageView {
////                    gravity = Gravity.CENTER
//                    id = View.generateViewId()
//                    scaleType = ImageView.ScaleType.FIT_CENTER
//
//                    imageResource = sourceImage
//                    onClick {
//                        if (type != 0) {
//                            click = !click
//                            img.imageResource = if (click) R.drawable.check_ok else sourceImage
//                        }
//                    }
//                }.lparams(width = size, height = size)
//            }
//        }
//    }
//}

class BarChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

}