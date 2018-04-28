package com.ankotest.adol.pickertest.recycler

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

const val fragID = 3

class RecyclerAct : AppCompatActivity() {

    private lateinit var ui: ActUI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActUI()
        ui.setContentView(this)
        ui.tt.text = "!@#$%^&*()"
//        val frag = FragUI()
//        fragmentManager.beginTransaction().add(fragID,frag).commit()
    }

    override fun onStart() {
        super.onStart()
        ui.setAdapter()
    }
}

class ActUI : AnkoComponent<Context> {
    private lateinit var rec: RecyclerView
    private lateinit var ctx: Context
    lateinit var tt: TextView

    override fun createView(ui: AnkoContext<Context>): View {
        ctx = ui.ctx
        return ui.apply {
            verticalLayout {
                //                lparams(width= matchParent,height = matchParent)//设置布局的宽高
                id = fragID
                tt = textView("Recycle") {
                    textSize = 24F
                    backgroundColor = Color.BLUE
                    gravity = Gravity.CENTER
                }.lparams(width = matchParent)

                rec = recyclerView {
                }//.lparams(width=matchParent, height = wrapContent)
            }
//            setAdapter()
        }.view
    }


    fun setAdapter() {
        val modelDate = listOf("123", "234", "asd")
        rec.adapter = RecAdapter(ctx, modelDate)
        rec.layoutManager = LinearLayoutManager(ctx)
    }

//    private fun remo(index: Int) {
//        modelDate.removeAt(index)
//        rec.adapter.notifyItemRemoved(index)
//    }
}
