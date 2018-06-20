package com.ankotest.adol.pickertest.main

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.leftPadding
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView

class CountAdapter : RecyclerView.Adapter<CountAdapter.ViewHolder>() {
    private var itemSize: Int = 0
    private lateinit var countData: CountData
    //    private var hasChange :Boolean
    // Button Event ------------

    fun setIt(item: CountData) {
        countData = item
        itemSize = countData.older.size
//        itemSize.pln()
        notifyDataSetChanged()
    }

//    fun resert() {
//        itemSize = 0
//        notifyDataSetChanged()
//    }

    //Override -----------------------
    override fun getItemCount() = itemSize

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //ankolayout 回傳 view
//        thisWidth = parent.layoutParams.width
//        thisWidth.pln()
        //自訂View 建構方法
        val v = Information(parent.context)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as Information).apply {
            setItem(position, countData.older.values.elementAt(position))
        }
    }

    //View ---------------------
    //Anko 寫法
    inner class Information(ctx: Context) : _ConstraintLayout(ctx) {
        private lateinit var showText: TextView
        private lateinit var showText2: TextView
        fun setItem(position: Int, older: Int) {
            showText.text = countData.older.keys.elementAt(position)
            val total1 = countData.status[0][older] + countData.status[1][older]
            val total2 = countData.status[2][older] + countData.status[3][older]
            val status1 = "幹部  $total1 (${countData.status[0][older]}/${countData.status[1][older]})  "
            val status2 = "學員  $total2 (${countData.status[2][older]}/${countData.status[3][older]})"
            val t = status1 + status2
            showText2.text = t
        }

        init {
            constraintLayout {
                lparams(matchParent, dip(65))
                showText = textView("body1") {
                    id = View.generateViewId()
                    textSize = 24f
                }.lparams {
                    leftPadding = dip(40)
                }
                showText2 = textView("body1") {
                    id = View.generateViewId()
                    textSize = 22f
                }.lparams {
                    leftMargin = dip(30)
                }


                applyConstraintSet {
                    showText {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID
                        )
                    }
                    showText2 {
                        connect(
                                TOP to BOTTOM of showText,
                                START to START of PARENT_ID
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val TextID = 0
    }
}