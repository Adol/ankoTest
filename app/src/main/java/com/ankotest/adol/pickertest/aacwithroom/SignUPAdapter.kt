package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ankotest.adol.pickertest.R
import com.ankotest.adol.pickertest.api.DeviceInfo
import com.ankotest.adol.pickertest.api.setT
import com.ankotest.adol.pickertest.model.SignUpTable
import com.ankotest.adol.pickertest.model.courseSignUP
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.leftPadding
import org.jetbrains.anko.textView

class SelectSignUPAd : RecyclerView.Adapter<SelectSignUPAd.ViewHolder>() {
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
        return ViewHolder(Information(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTv.text = items[position].name
        (holder.itemView as? Information).let {
            it!!.setStatus(items[position].course)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView = itemView.find(R.id.TextID)
    }

    //Anko 寫法
    inner class Information(ctx: Context) : _ConstraintLayout(ctx) {
        fun setStatus(data: List<courseSignUP>) {
            data.forEach {
                when (it.status[1]) {
                    2 -> show(it.status[0])
                }
            }
        }

        private fun show(i: Int) {
            if (i > 4) {
                mt[i - 3].visibility = View.VISIBLE
                if (i < 8) mt[0].visibility = View.VISIBLE else mt[1].visibility = View.VISIBLE
//                please(interpolator = DecelerateInterpolator()) {
//                mt[i - 3].alpha = 0f
//                    animate(mt[i - 3]) toBe {
//                        alpha(1f)
//                    }
//                }.start()
            }
        }

        private var mt = mutableListOf<TextView>()

//        private fun setT(T: TextView, textS: Int = 16, visibInt: Int = View.GONE): TextView {
////            mt[0].setT()
//            return T.apply {
//                textSize = textS.toFloat()
//                visibility = visibInt
//                gravity = Gravity.CENTER
////                paint.isFakeBoldText = true
//            }
//        }

        init {
            constraintLayout {
                lparams(DeviceInfo.data.mW - 180, dip(60))

                leftPadding = 90

//                val title = textView("Button") { id = R.id.TextID }.let { it.setT(it, 36, 0) }
                val title = textView("Button") { id = R.id.TextID }.let { it.setT(it, 36, 0) }
                mt.add(textView("Day1 出席") { id = View.generateViewId() }.let { it.setT(it) })
                mt.add(textView("Day2 出席") { id = View.generateViewId() }.let { it.setT(it) })

                mt.add(textView("上") { id = View.generateViewId() }.let { it.setT(it) })
                mt.add(textView("下") { id = View.generateViewId() }.let { it.setT(it) })
                mt.add(textView("晚") { id = View.generateViewId() }.let { it.setT(it) })

                mt.add(textView("上") { id = View.generateViewId() }.let { it.setT(it) })
                mt.add(textView("下") { id = View.generateViewId() }.let { it.setT(it) })
                mt.add(textView("晚") { id = View.generateViewId() }.let { it.setT(it) })

                applyConstraintSet {
                    title {
                        connect(
                                TOP to TOP of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID,
                                START to START of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    mt[0]{
                        connect(
                                TOP to TOP of PARENT_ID margin dip(5),
                                END to START of mt[2] margin dip(3)
                        )
                    }
                    mt[2] {
                        connect(
                                TOP to TOP of PARENT_ID margin dip(5),
                                END to START of mt[3] margin dip(3)
                        )
                    }
                    mt[3] {
                        connect(
                                TOP to TOP of PARENT_ID margin dip(5),
                                END to START of mt[4] margin dip(3)
                        )
                    }
                    mt[4] {
                        connect(
                                TOP to TOP of PARENT_ID margin dip(5),
                                END to END of PARENT_ID
                        )
                    }
                    mt[1]{
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID margin dip(5),
                                END to START of mt[5] margin dip(3)
                        )
                    }
                    mt[5] {
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID margin dip(5),
                                END to START of mt[6] margin dip(3)
                        )
                    }
                    mt[6] {
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID margin dip(5),
                                END to START of mt[7] margin dip(3)
                        )
                    }
                    mt[7] {
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID margin dip(5),
                                END to END of PARENT_ID
                        )
                    }
                }
            }
        }
    }

}

