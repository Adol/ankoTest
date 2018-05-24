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
import com.ankotest.adol.pickertest.api.EventVar
import com.ankotest.adol.pickertest.api.pln
import com.ankotest.adol.pickertest.model.SignUpData
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.sdk25.coroutines.onClick

class UserCheckAdapter(private var userInfo: List<String>, private var data: List<SignUpData>, private var callBack: () -> Unit) : RecyclerView.Adapter<UserCheckAdapter.ViewHolder>() {
    val mealButton = listOf(R.drawable.meal_button_1, R.drawable.meal_button_2, R.drawable.meal_button_3)
    val studyButton = listOf(R.drawable.study_button_1, R.drawable.study_button_2, R.drawable.study_button_3)

    private var itemSize: Int
    //    private var hasChange :Boolean
    //原本資料 -> 取消於回復原狀用
    private var oldStatus: Array<Int> = arrayOf()

    init {
        "init".pln()
        EventVar.hasChange = true
        itemSize = data.size
        //取得原本資料
        data.forEach {
            oldStatus += arrayOf(it.signUp[1])
        }
    }

    // Button Event ------------
    private fun cancel() {
        bg {
            EventVar.hasChange = false
            for (i in oldStatus.indices) {
                data[i].signUp[1] = oldStatus[i]
            }
        }
        goBack()
    }

    private fun goBack() {
        bg {
            if (EventVar.hasChange) {
                for (i in data.indices) {
                    if (data[i].signUp[1] != oldStatus[i]) {
                        data[i].title.pln()
                    }
                }
            }
        }
        //清 物件
        itemSize = 0
        //回傳
        callBack()
    }

    //Override -----------------------
    override fun getItemCount(): Int {
        when (itemSize == 0) {
            false -> itemSize + 2
        }
        return itemSize
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
        val v = ButtonBody(parent.context)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> holder.itemView.find<TextView>(TextID).text = getTitle(userInfo[0].plus("  "), userInfo[1], "月廣培報到")
            itemCount - 1 -> {
            }
            else -> (holder.itemView as ButtonBody).apply {
                setItem(position - 1)
            }
        }
    }

    private fun getTitle(s1: String?, s2: String, s3: String): String {
        return s1 + s2 + s3
    }

    //View ---------------------
    private fun headOB(ui: AnkoContext<UserCheckAdapter>): View {
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
    inner class ButtonBody(ctx: Context) : _ConstraintLayout(ctx) {
        private lateinit var showText: TextView
        private lateinit var showButton: ImageView

        private var itemNum = 0
        private val type by lazy {
            data[itemNum].signUp[0]
        }

        fun setItem(No: Int) {
            itemNum = No
            showText.text = data[itemNum].title//keys[itemNum]
            setImage(type)
        }

        private fun setImage(I: Int) {
            when (I) {
                in 0..4 -> showButton.setImageResource(mealButton[data[itemNum].signUp[1]])
                in 5..9 -> showButton.setImageResource(studyButton[data[itemNum].signUp[1]])
            }
        }

        init {
            constraintLayout {
                lparams(thisWidth, dip(55))
                onClick {
                    when (data[itemNum].signUp[2]) {
                        2 -> data[itemNum].signUp[1] = 0
                        0 -> data[itemNum].signUp[1] = 2
                    }
                    setImage(type)
                }

                val gap = view {
                    id = View.generateViewId()
                    visibility = View.INVISIBLE
                }.lparams(90, 1)

                showText = textView("body1") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }

                showButton = imageView {
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
                    showButton {
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

    private fun endOB(ui: AnkoContext<UserCheckAdapter>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent, dip(55))
                gravity = Gravity.CENTER

                textView("確認") {
                    id = TextID
                    textSize = sp(9).toFloat()
                    onClick {
                        goBack()
                    }
                }
                textView(" / ") {
                    textSize = sp(7).toFloat()
                }
                textView("取消") {
                    textSize = sp(9).toFloat()
                    onClick {
                        cancel()
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