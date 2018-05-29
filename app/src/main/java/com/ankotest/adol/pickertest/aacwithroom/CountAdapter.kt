package com.ankotest.adol.pickertest.aacwithroom

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
import org.jetbrains.anko.sp
import org.jetbrains.anko.textView

class CountAdapter : RecyclerView.Adapter<CountAdapter.ViewHolder>() {
    var itemSize: Int = 0
    private lateinit var countData: CountData
    //    private var hasChange :Boolean
    // Button Event ------------
    private var thisWidth: Int = 0

    fun setIt(item: CountData) {
        countData = item
        itemSize = countData.old.size
//        "setIt".pln()
//        notifyDataSetChanged()
    }

    //Override -----------------------
    override fun getItemCount() = itemSize

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //ankolayout 回傳 view
        thisWidth = parent.layoutParams.width
        //自訂View 建構方法
        val v = Information(parent.context)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as Information).apply {
//            position.pln()
            val i = countData.old.values.elementAt(position)

            setItem(countData.old.keys.elementAt(position),countData.status[0][i].toString() )
        }
    }

    //View ---------------------
    //Anko 寫法
    inner class Information(ctx: Context) : _ConstraintLayout(ctx) {
        private lateinit var showText: TextView
        private lateinit var showText2: TextView
        fun setItem(s1:String,s2:String) {
            showText.text = s1
            showText2.text = s2
        }

        init {
            constraintLayout {
                lparams(thisWidth, dip(65))
                showText = textView("body1") {
                    id = View.generateViewId()
                    textSize = sp(9).toFloat()
                }.lparams{
                    leftPadding = dip(40)
                }
                showText2 = textView("body1") {
                    id = View.generateViewId()
                    textSize = sp(9).toFloat()
                }.lparams{
                    leftToLeft = dip(80)
                }


                applyConstraintSet {
                    showText {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID
//                                END to END of PARENT_ID
                        )
                    }
                    showText2 {
                        connect(
                                TOP to BOTTOM of showText,
                                START to START of PARENT_ID
//                                END to END of PARENT_ID
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