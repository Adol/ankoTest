package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ankotest.adol.pickertest.R
import com.ankotest.adol.pickertest.ui.pln
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

class CheckAdapter(private var data: SignUpTable, private var callBack: (D: Int) -> Unit) : RecyclerView.Adapter<CheckAdapter.ViewHolder>() {
    private var keys: List<String> = listOf()
    private var values: List<Array<Int>> = listOf()
    private var thisWidth: Int = 0

    init {
        data.userData.signUp.forEach {
            keys += listOf(it.key)
            values += listOf(it.value)
        }
    }

    private fun getAll() {
//        values.forEach {
//            //            it[1] = 8
//        }
        data.userData.signUp.forEach {
            it.key.pln()
            it.value[1].pln()
        }
        values = listOf()
        callBack(1)
    }

    //Override -----------------------
    override fun getItemCount(): Int {
        if (values.isEmpty()) return 0 else return values.size + 2
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tempAnko = AnkoContext.createReusable(parent.context, this)
        thisWidth = parent.layoutParams.width
        when (viewType) {
            0 -> return ViewHolder(headOB(tempAnko))
            itemCount - 1 -> return ViewHolder(endOB(tempAnko))
        }
        val v = Body1(parent.context)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> holder.itemView.find<TextView>(TextID).text = getT(data.name.plus("  "), data.month.toString(), "月廣培報到")
            itemCount - 1 -> itemCount.pln()
            else -> {
                (holder.itemView as Body1).t1.text = keys[position - 1]
            }
        }
    }

    private fun getT(s1: String?, s2: String, s3: String): String {
        return s1 + s2 + s3
    }

    //View ---------------------
    private fun headOB(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent)
                gravity = Gravity.CENTER
                textView("Head") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }.lparams(wrapContent, wrapContent)
            }
        }.view
    }

    //Anko 寫法
    inner class Body1(ctx: Context) : _ConstraintLayout(ctx) {
        lateinit var t1: TextView

        init {
            constraintLayout {
                lparams(thisWidth)
                onClick {
                    //                    itemCount.pln()
                }

                val gap = view {
                    id = View.generateViewId()
                    visibility = View.INVISIBLE
                }.lparams(90, 1)

                t1 = textView("body1") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }

                val check = imageView(R.drawable.mealbutton_2) {
                    id = View.generateViewId()
                }.lparams(dip(36), dip(36))
                imageView(R.drawable.mealbutton_1) {
                    id = View.generateViewId()
                }.lparams(dip(36), dip(36))
                imageView(R.drawable.mealbutton_3) {
                    id = View.generateViewId()
                }.lparams(dip(36), dip(36))
                applyConstraintSet {
                    gap {
                        connect(
                                TOP to TOP of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID,
                                START to START of PARENT_ID
//                                END to END of PARENT_ID
                        )
                    }
                    check {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to END of gap,
//                                END to END of gap,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    t1 {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of gap,
                                END to END of PARENT_ID
                        )
                    }
                }

            }
        }
    }

    private fun endOB(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent)
                gravity = Gravity.CENTER

                textView("確認") {
                    id = TextID
                    onClick {
                        getAll()
                    }
                }
                textView(" / ")
                textView("取消")
                applyRecursively {
                    when (it) {
                        is TextView -> {
                            it.textSize = sp(9).toFloat()
                        }
                    }

                }

            }
        }.view
    }

    companion object {
        const val TextID = 0
    }

}

