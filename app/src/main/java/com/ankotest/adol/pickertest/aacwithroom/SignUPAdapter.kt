package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
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
        holder.nameTv.text = items[position].name
//        holder.data = items[position].userData
        (holder.itemView as? Information).let {
            it!!.setStatus(items[position].userData)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        lateinit var data: List<SignUpData>
        val nameTv: TextView = itemView.find(R.id.TextID)
    }

    //
    //Anko 寫法
    inner class Information(ctx: Context) : _ConstraintLayout(ctx) {
        fun setStatus(data: List<SignUpData>) {
            data.forEach {
                val temp = it.signUp[1]
                when (it.signUp[0]) {
                    in 5..9 -> {
                        if (temp == 2) show(it.signUp[0])
                    }
//                when (listOf(it.signUp[0],it.signUp[0])) {
//                    listOf(5, 2) -> mt[2].visibility = View.VISIBLE
//                    listOf(6, 2) -> mt[3].visibility = View.VISIBLE
//                    listOf(7, 2) -> mt[4].visibility = View.VISIBLE
//                    listOf(8, 2) -> mt[5].visibility = View.VISIBLE
//                    listOf(9, 2) -> mt[6].visibility = View.VISIBLE
                }
            }
        }

        fun show(i: Int) {
            mt[i-3].visibility = View.VISIBLE
            if (i < 8) mt[0].visibility = View.VISIBLE else mt[1].visibility = View.VISIBLE
        }

        private var mt = mutableListOf<TextView>()

        private fun setT(T: TextView, textS: Int = 5, visibInt: Int = View.GONE): TextView {
            return T.apply {
                textSize = sp(textS).toFloat()
                visibility = visibInt

                gravity = Gravity.CENTER
                paint.isFakeBoldText = true
            }
        }


        init {
            constraintLayout {
                lparams(DeviceInfo.data.mW - 180)
                leftPadding = 90

                val title = textView("Button") { id = R.id.TextID }.let { setT(it, 12, 0) }
                mt.add(textView("D1出席") { id = View.generateViewId() }.let { setT(it) })
                mt.add(textView("D2出席") { id = View.generateViewId() }.let { setT(it) })

                mt.add(textView("上午") { id = View.generateViewId() }.let { setT(it) })
                mt.add(textView("下午") { id = View.generateViewId() }.let { setT(it) })
                mt.add(textView("晚上") { id = View.generateViewId() }.let { setT(it) })

                mt.add(textView("上午") { id = View.generateViewId() }.let { setT(it) })
                mt.add(textView("下午") { id = View.generateViewId() }.let { setT(it) })
                mt.add(textView("晚上") { id = View.generateViewId() }.let { setT(it) })

                applyConstraintSet {
                    title {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    mt[0]{
                        connect(
                                TOP to TOP of PARENT_ID,
                                END to START of mt[2] margin dip(5)
                        )
                    }
                    mt[2] {
                        connect(
                                TOP to TOP of PARENT_ID,
                                END to START of mt[3] margin dip(5)
                        )
                    }
                    mt[3] {
                        connect(
                                TOP to TOP of PARENT_ID,
                                END to START of mt[4] margin dip(5)
                        )
                    }
                    mt[4] {
                        connect(
                                TOP to TOP of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                    mt[1]{
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID,
                                END to START of mt[5] margin dip(5)
                        )
                    }
                    mt[5] {
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID,
                                END to START of mt[6] margin dip(5)
                        )
                    }
                    mt[6] {
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID,
                                END to START of mt[7] margin dip(5)
                        )
                    }
                    mt[7] {
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                }
            }
        }
    }

}

