package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ankotest.adol.pickertest.R
import com.ankotest.adol.pickertest.ui.pln
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

class CheckAdapter(private var data: SignUpTable, private var callBack: (D: Int) -> Unit) : RecyclerView.Adapter<CheckAdapter.ViewHolder>() {
    private var keys: List<String> = listOf()
    private var values: List<Array<Int>> = listOf()

    init {
        data.userData.signUp.forEach {
            keys += listOf(it.key)
            values += listOf(it.value)
        }
    }

    fun getAll() {
        values.forEach {
            it[1] = 8
        }
        data.userData.signUp.forEach {
            it.key.pln()
            it.value[1].pln()
        }
        callBack(1)
    }

    //Override -----------------------
    override fun getItemCount(): Int {
        return values.size + 2
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tempAnko = AnkoContext.createReusable(parent.context, this)
        when (viewType) {
            0 -> return ViewHolder(headOB(tempAnko))
//            0 -> return ViewHolder(Hd(parent.context))
            itemCount - 1 -> return ViewHolder(endOB(tempAnko))
        }
        return ViewHolder(bady2(tempAnko))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        position.pln()
        when (position) {
            0 -> holder.itemView.find<TextView>(TextID).text = getT(data.name.plus("  "), data.month.toString(), "月廣培報到")
            itemCount - 1 -> "End".pln()
            else -> {
                "Else".pln()
                holder.itemView.find<TextView>(TextID).text = keys[position - 1]
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

    inner class Hd @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
        lateinit var t1: TextView

        init {
            AnkoContext.createDelegate(this).let {
                constraintLayout {
                    //                    lparams(DeviceInfo.data.mW - 180)
//                    gravity = Gravity.CENTER
                    t1 = textView("Test") {
                        gravity = Gravity.CENTER
                        id = View.generateViewId()
                        textSize = sp(12).toFloat()
                    }
                    applyConstraintSet {
                        t1 {
                            connect(
                                    TOP to TOP of PARENT_ID,
                                    START to START of PARENT_ID,
                                    END to END of PARENT_ID
                            )
                        }
                    }
                }
            }
        }
    }

    private fun bady2(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            constraintLayout {
                lparams(matchParent)
                val gap = imageView(R.drawable.ic_arrow_downward_black_24dp) {
                    id = View.generateViewId()
                    leftPadding = 90
                    visibility = View.INVISIBLE
                }
                val t1 = textView("body1") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }
                val check = imageView(R.drawable.ic_check_circle_black_24dp) {
                    id = View.generateViewId()
                }.lparams(100, 100)

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
//                                START to START of PARENT_ID,
                                END to END of gap
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
        }.view
    }

    private fun endOB(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent)
                gravity = Gravity.CENTER
//                topPadding = dip(30)
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