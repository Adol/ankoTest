package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.constraint.ConstraintSet.VERTICAL
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ankotest.adol.pickertest.R
import com.ankotest.adol.pickertest.api.DeviceInfo
import com.ankotest.adol.pickertest.model.SignUpData
import com.ankotest.adol.pickertest.model.SignUpTable
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.guideline

class SelectSignUPAd(val context: Context) : RecyclerView.Adapter<SelectSignUPAd.ViewHolder>() {
    private var items: List<SignUpTable> = listOf()

    fun setIt(item: List<SignUpTable>) {
        items = item
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val ankoT = AnkoContext.createReusable(context, this)
//        return ViewHolder(Information2().createView(ankoT))
        //自訂View 建構方法
        return ViewHolder(Information(context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.data = items[position].userData
        holder.nameTv.text = items[position].name
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var data: List<SignUpData>
        val nameTv: TextView = itemView.find(R.id.TextID)
    }

    //Anko 寫法
    inner class Information(ctx: Context) : _ConstraintLayout(ctx) {
        init {
            constraintLayout {
                lparams(DeviceInfo.data.mW - 180)
                leftPadding = 90

                val guideline = guideline() {
                    id = View.generateViewId()
                }.lparams {
                    orientation = VERTICAL
                }

                val title = textView("Button") {
                    id = R.id.TextID
                    textSize = sp(12).toFloat()
                }

                val s1 = textView("上午") {
                    id = View.generateViewId()
                    gravity = Gravity.CENTER
                    textSize = sp(5).toFloat()
                    leftPadding = title.width
                }
                val s2 = textView("下午") {
                    id = View.generateViewId()
                    gravity = Gravity.CENTER
                    textSize = sp(5).toFloat()
                    leftPadding = title.width
                    visibility = View.GONE
                }
                val s3 = textView("晚上") {
                    id = View.generateViewId()
                    gravity = Gravity.CENTER
                    textSize = sp(5).toFloat()
                    leftPadding = title.width
                    visibility = View.GONE
                }

                applyConstraintSet {
                    guideline {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID margin 200
                        )
                    }
                    title {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of guideline,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }

                    s1 {
                        connect(
                                TOP to TOP of s3,
                                END to START of s2 margin dip(5)
//                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    s2 {
                        connect(
                                TOP to TOP of s3,
                                END to START of s3 margin dip(5)
//                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    s3 {
                        connect(
                                TOP to TOP of PARENT_ID,
                                END to END of PARENT_ID
//                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                }
            }
        }
    }

}

