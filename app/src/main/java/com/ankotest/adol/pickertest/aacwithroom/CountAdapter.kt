package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import org.jetbrains.anko.textView

class CountAdapter(private var CountInfo: List<String>) : RecyclerView.Adapter<CountAdapter.ViewHolder>() {
    private var itemSize: Int = 0
    //    private var hasChange :Boolean
    //原本資料 -> 取消於回復原狀用
    // Button Event ------------

    //Override -----------------------
    override fun getItemCount() = CountInfo.size

    override fun getItemViewType(position: Int): Int = position

    private var thisWidth: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //ankolayout 回傳 view
        thisWidth = parent.layoutParams.width
        //自訂View 建構方法
        val v = infomation(parent.context)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as infomation).apply {
            setItem(1)
        }
    }

    //View ---------------------

    //Anko 寫法
    inner class infomation(ctx: Context) : _ConstraintLayout(ctx) {
        private lateinit var showText: TextView
        private lateinit var showButton: ImageView

        private var itemNum = 0

        fun setItem(No: Int) {
//            itemNum = No
//            showText.text = data[itemNum].signType//keys[itemNum]
//            setImage(type)
        }

        init {
            constraintLayout {
                lparams(thisWidth, dip(55))

                showText = textView("body1") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }


                applyConstraintSet {
                    showText {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
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