package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ankotest.adol.pickertest.R
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

class CheckAdapter(private var data: SignUpTable, private var callBack: (D: Int) -> Unit) : RecyclerView.Adapter<CheckAdapter.ViewHolder>() {
    val mealButton = listOf(R.drawable.meal_button_1, R.drawable.meal_button_2, R.drawable.meal_button_3)
    val studyButton = listOf(R.drawable.study_button_1, R.drawable.study_button_2, R.drawable.study_button_3)

    private var keys: List<String> = listOf()
    private var values: List<Array<Int>> = listOf()
    private var oldStatus: Array<Int> = arrayOf()


    init {
        var i = 0
        data.userData.signUp.forEach {
            keys += listOf(it.key)
            values += listOf(it.value)
            oldStatus += arrayOf(values[i][1])
            i++
        }
    }

    // Button Event ------------
    private fun chechOK() {
        values = listOf()
        callBack(1)
    }

    private fun cancle() {
        var i = 0
//        oldStatus.size.pln()
        oldStatus.forEach {
            values[i][1] = it
            i++
        }
        chechOK()
    }

    //Override -----------------------
    override fun getItemCount(): Int {
        when (values.isEmpty()) {
            true -> return 0
        }
        return values.size + 2
    }

    override fun getItemViewType(position: Int): Int = position

    private var thisWidth: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //ankolayout 回傳 view
        val tempAnko = AnkoContext.createReusable(parent.context, this)
        when (viewType) {
            0 -> return ViewHolder(headOB(tempAnko))
            itemCount - 1 -> return ViewHolder(endOB(tempAnko))
        }

        thisWidth = parent.layoutParams.width
        //自訂View 建構方法
        val v = Body1(parent.context)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> holder.itemView.find<TextView>(TextID).text = getTitle(data.name.plus("  "), data.month.toString(), "月廣培報到")
            itemCount - 1 -> {
            }
            else -> (holder.itemView as Body1).apply {
                this.setItem(position - 1)
            }
        }
    }

    private fun getTitle(s1: String?, s2: String, s3: String): String {
        return s1 + s2 + s3
    }

    //View ---------------------
    private fun headOB(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent, dip(55))
                gravity = Gravity.CENTER
                textView("Head") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }.lparams(wrapContent)
            }
        }.view
    }

    //Anko 寫法
    inner class Body1(ctx: Context) : _ConstraintLayout(ctx) {
        private lateinit var showText: TextView
        private lateinit var showStuate: ImageView

        private var itemNo = 0
        private val type by lazy {
            values[itemNo][0]
        }

        fun setItem(No: Int) {
            itemNo = No
            showText.text = keys[itemNo]
            setImage()
        }

        private fun setImage() {
            when (type) {
                0 -> showStuate.setImageResource(mealButton[values[itemNo][1]])
                1 -> showStuate.setImageResource(studyButton[values[itemNo][1]])
            }
        }

        init {
            constraintLayout {
                lparams(thisWidth, dip(55))
                onClick {
                    when (values[itemNo][1]) {
                        2 -> values[itemNo][1] = 0
                        0 -> values[itemNo][1] = 2
                    }
                    setImage()
                }

                val gap = view {
                    id = View.generateViewId()
                    visibility = View.INVISIBLE
                }.lparams(90, 1)

                showText = textView("body1") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }

                showStuate = imageView {
                    id = View.generateViewId()
                }.lparams(dip(36), dip(36))

                applyConstraintSet {
                    gap {
                        connect(
                                TOP to TOP of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID,
                                START to START of PARENT_ID
                        )
                    }
                    showStuate {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to END of gap,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    showText {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of gap,
                                END to END of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                }
            }
        }
    }

    private fun endOB(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent, dip(55))
                gravity = Gravity.CENTER

                textView("確認") {
                    id = TextID
                    textSize = sp(9).toFloat()
                    onClick {
                        chechOK()
                    }
                }
                textView(" / ") {
                    textSize = sp(7).toFloat()
                }
                textView("取消") {
                    textSize = sp(9).toFloat()
                    onClick {
                        cancle()
                    }
                }
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

