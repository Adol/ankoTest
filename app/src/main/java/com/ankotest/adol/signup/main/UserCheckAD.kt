package com.ankotest.adol.signup.main

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.constraint.ConstraintSet.VERTICAL
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ankotest.adol.signup.R
import com.ankotest.adol.signup.api.EventVar
import com.ankotest.adol.signup.model.courseSignUP
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class UserCheckAD(private var userInfo: List<String>,
                  private var data: List<courseSignUP>,
                  private var callBack: () -> Unit) : RecyclerView.Adapter<UserCheckAD.ViewHolder>() {
    val mealButton = listOf(R.drawable.meal_button_1, R.drawable.meal_button_2, R.drawable.meal_button_3)
    val studyButton = listOf(R.drawable.study_button_1, R.drawable.study_button_2, R.drawable.study_button_3)

    private var itemSize: Int
    private var oldStatus = mutableListOf<Int>()

    init {
        EventVar.hasChange = false
        itemSize = data.size
        //取得原本資料
        data.forEach {
            oldStatus.add(it.status[1]) //+= arrayOf(it.status[1])
        }
    }

    // Button Event ------------
    private fun cancel() {
        //清VM data
        for (i in oldStatus.indices) {
            data[i].status[1] = oldStatus[i]
        }
        goBack()
    }

    private fun goBack() {
        //清 物件
        itemSize = 0
        //回傳
        callBack()
        EventVar.fragmentTrans = true
    }

    //Override -----------------------
    override fun getItemCount(): Int {
        if (itemSize != 0) return itemSize + 2 else return itemSize
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
            0 -> holder.itemView.find<TextView>(R.id.TextID).text = getTitle(userInfo[0].plus("  "), userInfo[1], "月廣培報到")
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
    private fun headOB(ui: AnkoContext<UserCheckAD>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent, dip(60))
                gravity = Gravity.CENTER
                textView("Head") {
                    id = R.id.TextID
                    textSize = 26f
                    gravity = Gravity.CENTER
                }
            }
        }.view
    }

    //Anko 寫法
    inner class ButtonBody(ctx: Context) : _ConstraintLayout(ctx) {
        private lateinit var showText: TextView
        private lateinit var showButton: ImageView

        private val type by lazy {
            data[itemID].status[0]
        }
        //setItemc會重新給定
        private var itemID = 0

        fun setItem(No: Int) {
            itemID = No
            showText.text = data[itemID].course//keys[itemID]
            setImage(type)
        }

        fun clickMove() {
            data[itemID].status[1] = 2
            setImage(type)
        }

        private fun setImage(I: Int) {
            when (I) {
                in 0..4 -> showButton.setImageResource(mealButton[data[itemID].status[1]])
                in 5..9 -> showButton.setImageResource(studyButton[data[itemID].status[1]])
            }
        }

        init {
            constraintLayout {
                lparams(thisWidth, dip(50))
                onClick {
                    when (data[itemID].status[1]) {
                        2 -> data[itemID].status[1] = 0
                        0 -> data[itemID].status[1] = 2
                    }
                    setImage(type)
                }

                val guide = guideline {
                    id = View.generateViewId()
                }.lparams(width = 20, height = matchConstraint) {
                    orientation = VERTICAL
                    leftPadding = 200
                }

                showButton = imageView {
                    id = View.generateViewId()
                }.lparams(dip(36), dip(36))

                showText = textView("body1") {
                    id = View.generateViewId()
                    textSize = 26f
                }


                applyConstraintSet {
                    guide {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID
                        )
                    }
                    showButton {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to END of guide,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    showText {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to END of showButton margin dip(15),
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                }
            }
        }
    }

    private fun endOB(ui: AnkoContext<UserCheckAD>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent, dip(35))
                gravity = Gravity.CENTER

                textView("確認") {
                    id = View.generateViewId()
                    textSize = 26f
                    onClick {
                        EventVar.hasChange = true
                        goBack()
                    }
                }
                textView(" / ") {
                    textSize = 20f
                }
                textView("取消") {
                    textSize = 26f
                    onClick {
                        cancel()
                    }
                }
            }
        }.view
    }
}