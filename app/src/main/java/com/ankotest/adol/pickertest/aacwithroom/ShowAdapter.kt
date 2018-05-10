package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout

class ShowAdapter(context: Context) : RecyclerView.Adapter<ShowAdapter.ViewHolder>() {
    private var items: List<SignUpTable> = listOf()
    fun setIt(item: List<SignUpTable>) {
        items = item
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private val ankoContext = AnkoContext.createReusable(context, this)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SelectNameAdapterUI().createView(ankoContext))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.data = items[position].userData
        holder.nameTv.text = items[position].name
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: Int = 0
        lateinit var data: SignUP
        val nameTv: TextView = itemView.find(TextID)
    }

    companion object {
        const val TextID = 0
    }
}

class SelectNameAdapterUI : AnkoComponent<ShowAdapter> {
    override fun createView(ui: AnkoContext<ShowAdapter>) = with(ui) {
        constraintLayout {
            leftPadding = 90
            val title = textView("Button") {
                id = ShowAdapter.TextID
                textSize = 24f
                gravity = Gravity.CENTER
            }.lparams(wrapContent, wrapContent)
            val bgView = view {
                id = View.generateViewId()
            }.lparams(matchParent, height = dip(55))
            applyConstraintSet {
                title {
                    connect(
                            TOP to TOP of PARENT_ID,
                            START to START of PARENT_ID
//                            END to END of PARENT_ID
                    )
                }
                bgView {
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