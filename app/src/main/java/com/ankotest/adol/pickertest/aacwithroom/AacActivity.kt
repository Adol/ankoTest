package com.ankotest.adol.pickertest.aacwithroom

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.ankotest.adol.pickertest.api.DeviceInfo
import com.ankotest.adol.pickertest.api.EventVar
import com.ankotest.adol.pickertest.api.viewClass
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4._ViewPager

/**
 **Created by adol on 2018/3/19.
 */
class AacActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceInfo.getInfo(this)
        AacUi(supportFragmentManager).setContentView(this)
    }
}

class AacUi(fragmentManager: FragmentManager) : AnkoComponent<AacActivity> {

    private val viewpagerFm: ViewpagerFm = ViewpagerFm(fragmentManager)
    private lateinit var myViewpager: ViewClass

    override fun createView(ui: AnkoContext<AacActivity>): View {
        launch(UI) {
            delay(100)
        }
        return ui.apply {
            constraintLayout {
                myViewpager = viewClass {
                    id = View.generateViewId()
                    adapter = viewpagerFm
                }.lparams(0, 0)

                applyConstraintSet {
                    myViewpager {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                }
            }
        }.view
    }
}

class ViewClass(ctx: Context) : _ViewPager(ctx) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        EventVar.fragmentTrans.pln()
        when (EventVar.fragmentTrans){
            false -> return false
        }
        return super.onInterceptTouchEvent(ev)
    }
}

class ViewpagerFm(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private lateinit var tempFrag: AccFragment
    private val fragments = linkedMapOf<String, Fragment>()

    init {
        fragments["幹部"] = AccFragment()
//        fragments["學員"] = AccFragment()
//        fragments["統計"] = AccFragment()

    }

//    override fun getItemPosition(ob: Any): Int {
//        when (ob){
//            fm.fragments[0] ->return PagerAdapter.POSITION_NONE
//            fm.fragments[1] ->return PagerAdapter.POSITION_NONE
//        }
//        return POSITION_UNCHANGED
//    }

    override fun getItem(position: Int): Fragment {
        tempFrag = fragments.values.elementAt(position) as AccFragment
        tempFrag.title = fragments.keys.elementAt(position)
        return fragments.values.elementAt(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }
}



