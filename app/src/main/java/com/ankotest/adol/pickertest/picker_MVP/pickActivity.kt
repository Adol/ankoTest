package com.ankotest.adol.pickertest.picker_MVP

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import com.ankotest.adol.pickertest.api.EventVar
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.verticalLayout

class PickerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PickerActivityUI().setContentView(this)

        val frag = PickerActivityFragment()
        PickerPresenter(frag)
        fragmentManager.beginTransaction().add(EventVar.picker, frag, "Pick").commit()
    }
}

class PickerActivityUI : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>): View {
        return ui.apply {
            verticalLayout {
                id = EventVar.picker//设置id，方便添加Fragment
            }
        }.view
    }
}
















