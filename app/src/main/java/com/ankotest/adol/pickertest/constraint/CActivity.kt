package com.ankotest.adol.pickertest.constraint

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.view.View
import android.widget.Button
import org.jetbrains.anko.*

import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout

class CActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DoneUI().setContentView(this)
    }
}

class DoneUI : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        constraintLayout {
            val header = textView("123") {
                id = View.generateViewId()
                backgroundColor = Color.BLACK
            }.lparams(matchParent)
            val header2 = button("123") {
                id = View.generateViewId()
                backgroundColor = Color.BLUE
            }.lparams(matchParent)
            val header3 = button("123") {
                id = View.generateViewId()
                backgroundColor = Color.BLUE
            }.lparams(matchParent)

            applyConstraintSet {
                header {
                    connect(
                            TOP to TOP of PARENT_ID,
                            LEFT to LEFT of PARENT_ID,
//                            RIGHT to RIGHT of PARENT_ID,
                            BOTTOM to BOTTOM of PARENT_ID margin dip(16)
                    )
                    verticalBias = 0f
                }
                header2 {
                    connect(
                            TOP to BOTTOM of header,
                            LEFT to LEFT of PARENT_ID
                    )

//                    horizontalBias = 1.0f
//                    verticalBias = 1.0f
                }
                header3 {
                    connect(
                            TOP to BOTTOM of header2,
                            LEFT to LEFT of PARENT_ID
                    )

//                    horizontalBias = 1.0f
//                    verticalBias = 0.7f
                }
            }
        }.applyRecursively {
            when (it) {
              is Button -> it.textSize = 30f
            }

        }
    }
}
