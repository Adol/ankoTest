package com.ankotest.adol.pickertest.recycler

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ankotest.adol.pickertest.ui.ViewID
import org.jetbrains.anko.*

class RecAdapter(private val context: Context,private val items: List<String>, private val id_1: Int = 1) : RecyclerView.Adapter<RecAdapter.ViewHolder>() {
    private var headVierNum = 0
    private var footVierNum = 1
    private val ankoContext = AnkoContext.createReusable(context, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val reusableUI = AnkoContext.createReusable(parent.context, parent, false)
//        pln("viewType = $viewType")
        when (viewType) {
            0 -> return ViewHolder(View(parent.context))
            1 -> return ViewHolder(normalUI(ankoContext))
            else ->
                return ViewHolder(footUI(ankoContext))
        }
    }

//    fun setfootView() {
//
//    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            in 0..(headVierNum - 1) -> return (if (headVierNum != 0) 0 else 1)
            in headVierNum..(headVierNum + items.size - 1) -> return 1
            else ->
                return 2
        }
    }

    override fun getItemCount(): Int {
        return headVierNum + items.size + footVierNum
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("$$$$$$$$$$$$$$")
//        pln(holder.itemViewType)
        when (holder.itemViewType) {
            1 -> {
                val t = holder.itemView.find<TextView>(id_1)
                t.text = items[position - headVierNum]
            }
        }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    private fun normalUI(reusableUI: AnkoContext<RecAdapter>): View {
        return reusableUI.apply {
            relativeLayout {
                lparams(matchParent, wrapContent)
                gravity = Gravity.CENTER
                backgroundColor = Color.BLACK
//                imageView {
//                    id = ViewID.picker
//                    imageResource = R.drawable.img_1
//                    scaleType = ImageView.ScaleType.CENTER_CROP
//                }.lparams(width = matchParent, height = wrapContent) {
//                    alignParentTop()
//                }
                textView("123") {
                    id = id_1
                    gravity = Gravity.CENTER
                    backgroundColor = Color.BLUE
                    textSize = 24f
                }.lparams(width = wrapContent, height = wrapContent) {
                    below(ViewID.picker)
                }
            }
        }.view
    }

    private fun footUI(reusableUI: AnkoContext<RecAdapter>): View {
        return reusableUI.apply {
            linearLayout {
                lparams(matchParent, wrapContent)
                gravity = Gravity.CENTER
                textView("TT1") {
                    id = id_1
                    gravity = Gravity.CENTER
                    textSize = 34f
                }
                textView("/") {
                    gravity = Gravity.CENTER
                    textSize = 20f
                }
                textView("  TT2") {
                    gravity = Gravity.CENTER
                    backgroundColor = Color.WHITE
                    textSize = 24f
                }
            }
        }.view
    }
}
class Nomal_UI:AnkoComponent<RecAdapter>{
    override fun createView(ui: AnkoContext<RecAdapter>): View {
        return ui.apply {
            relativeLayout {
                lparams(matchParent, wrapContent)
                gravity = Gravity.CENTER
                backgroundColor = Color.BLACK
                textView("123") {
                    //                    id = id_1
                    gravity = Gravity.CENTER
                    backgroundColor = Color.BLUE
                    textSize = 24f
                }.lparams(width = wrapContent, height = wrapContent) {
                    below(ViewID.picker)
                }
            }
        }.view
    }
}