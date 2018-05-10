package com.ankotest.adol.pickertest

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.ankotest.adol.pickertest.api.DeviceInfo
import com.ankotest.adol.pickertest.api.ViewID.Companion.Aac
import com.ankotest.adol.pickertest.api.ViewID.Companion.ID_Constraint
import com.ankotest.adol.pickertest.api.ViewID.Companion.Recycler
import com.ankotest.adol.pickertest.api.ViewID.Companion.picker
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class StaticActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceInfo.getInfo(this)
        ActivityUI().setContentView(this)
    }
}

//------------------
class ActivityUI : AnkoComponent<Context> {
    private lateinit var changeAct: ChangeAct

    override fun createView(ui: AnkoContext<Context>): View {
        changeAct = ChangeAct(ui)

        return ui.apply {
            verticalLayout {
                textView("FrameLayout")
                verticalLayout {
                    backgroundColor = Color.BLUE
                    lparams(width = matchParent, height = matchParent) {
                        padding = dip(10)
                    }
                    textView("01 Picker") {
                        id = picker
                        gravity = Gravity.START
                    }.lparams(width = wrapContent) {
                        gravity = Gravity.START
                    }
                    textView("02 AAC") {
                        id = Aac
                    }
                    textView("03 Recycler") {
                        id = Recycler
                    }
                    textView("04 constraint") {
                        id = ID_Constraint
                    }
                }.applyRecursively {
                    when (it) {
                        is Button -> it.onClick {
                            changeAct.goActivity(it?.id)
                        }
                        is TextView -> {
                            it.textSize = 40f
                            it.onClick {
                                changeAct.goActivity(it?.id)
                            }
                        }
                    }
                }
            }.applyRecursively {
                when (it) {
                    is FrameLayout -> it.backgroundColor = Color.BLUE
                }
            }
        }.view
    }
}

class ChangeAct(private var tempContext: AnkoContext<Context>) {
    fun goActivity(i: Int?) {

//        when (i) {
//            picker -> tempContext.startActivity<PickerActivity>()
//            Aac -> tempContext.startActivity<AacActivity>()
//            Recycler -> tempContext.startActivity<RecyclerAct>()
//            ID_Constraint -> tempContext.startActivity<CActivity>()
//        }
    }
}
