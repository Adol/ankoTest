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
import com.ankotest.adol.pickertest.model.getUser
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.ctx
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4._ViewPager

/**
 **Created by adol on 2018/3/19.
 */
class AacActivity : AppCompatActivity() {
    lateinit var db: SignUpRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceInfo.getInfo(this)
        getJSON();
        AacUi(supportFragmentManager).setContentView(this)
    }

    private fun getJSON() {
        db = SignUpRepository(ctx)
        bg {
            db.delall()
            db.insert(getUser("AD", "幹部", "man"))
            db.insert(getUser("AD1", "幹部", "man"))
//            db.insert(getUser("AD2", "幹部", "man"))
            db.insert(getUser("ADA", "幹部", "woman"))
            db.insert(getUser("ADA1", "幹部", "woman"))
//            db.insert(getUser("ADA2", "幹部", "woman"))

            db.insert(getUser("ad", "學員", "man"))
            db.insert(getUser("ad1", "學員", "man"))
//            db.insert(getUser("ad2", "學員", "man"))
            db.insert(getUser("ada", "學員", "woman"))
            db.insert(getUser("ada1", "學員", "woman"))
//            db.insert(getUser("ada2", "學員", "woman"))
        }
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
        when (EventVar.fragmentTrans) {
            false -> return false
        }
        return super.onInterceptTouchEvent(ev)
    }
}

class ViewpagerFm(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    //    private lateinit var tempFrag: SignUpFragment
    private val fragments = linkedMapOf<String, Fragment>()

    init {
        fragments["幹部"] = SignUpFragment()
        fragments["學員"] = SignUpFragment()
        fragments["統計"] = CountFragment()

    }

//    override fun getItemPosition(ob: Any): Int {
//        when (ob){
//            fm.fragments[0] ->return PagerAdapter.POSITION_NONE
//            fm.fragments[1] ->return PagerAdapter.POSITION_NONE
//        }
//        return POSITION_UNCHANGED
//    }

    override fun getItem(position: Int): Fragment {
//        (fragments.values.elementAt(position) as? SignUpFragment).pln()
        val temp = fragments.values.elementAt(position)
        when (temp) {
            is SignUpFragment -> {
//                val tempFrag = fragments.values.elementAt(position) as SignUpFragment
                temp.title = fragments.keys.elementAt(position)
            }
            is CountFragment -> {
                temp.title = fragments.keys.elementAt(position)
            }
        }
        return fragments.values.elementAt(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }
}



