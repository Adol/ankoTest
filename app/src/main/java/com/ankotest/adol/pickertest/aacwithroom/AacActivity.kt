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
import com.ankotest.adol.pickertest.model.SignUpRepository
import com.ankotest.adol.pickertest.model.SignUpTable
import com.daimajia.slider.library.Transformers.DepthPageTransformer
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.nineoldandroids.view.ViewHelper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
    private lateinit var db: SignUpRepository
    private var baseUi = AacUi(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        db = SignUpRepository(ctx)
        super.onCreate(savedInstanceState)
        DeviceInfo.getInfo(this)
        baseUi.setContentView(this)
        bg {
            db.deleteAll()
            if (db.getAll().size < 1) {
                //read File
                val inputStream = applicationContext.assets.open("json2.txt")
                val inputString = inputStream.bufferedReader().use { it.readText() }
                //json to List
                val type = object : TypeToken<List<SignUpTable>>() {}.type
                val foJson = Gson().fromJson<List<SignUpTable>>(inputString, type)
                bg {
                    Flowable.just(foJson.forEach { db.insert(it) })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                baseUi.viewpagerFm.show()
                            })
                }
            }
        }
    }
}

class AacUi(fragmentManager: FragmentManager) : AnkoComponent<AacActivity> {
    val viewpagerFm: ViewpagerFm = ViewpagerFm(fragmentManager)
    private lateinit var myViewpager: ViewClass

    override fun createView(ui: AnkoContext<AacActivity>): View {
        launch(UI) {
            delay(100)
            myViewpager.setPageTransformer(false, ::onTransform)
//            myViewpager.setPageTransformer(false, { v, p ->
//                onTransform(v, p)
//            })
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
    private val fragments = linkedMapOf<String, Fragment>()

    fun show() {
        (fragments["幹部"] as SignUpFragment).setVm()
        (fragments["學員"] as SignUpFragment).setVm()
    }

    init {
        fragments["幹部"] = SignUpFragment()
        fragments["學員"] = SignUpFragment()
        fragments["回報"] = CountFragment()
    }

//    override fun getItemPosition(ob: Any): Int {
//        "getItemPosition".pln()
//        when (ob){
//            fm.fragments[2] ->return PagerAdapter.POSITION_NONE
////            fm.fragments[1] ->return PagerAdapter.POSITION_NONE
//        }
//        return POSITION_UNCHANGED
//    }

    override fun getItem(position: Int): Fragment {
        val temp = fragments.values.elementAt(position)
        when (temp) {
            is SignUpFragment -> {
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

fun onTransform2(view: View, position: Float) {
    DepthPageTransformer().transformPage(view, position)
}

var leftorright = ""
var PB = false
fun onTransform(view: View, position: Float) {
//    position.pln()
    if (position == 0f) {
        PB = false
    }

    if (!PB) {
        if (position > 0 && position < 1) {
            PB = true
            leftorright = if (position < 0.5) "toRight" else "toLeft"
//            leftorright.pln()
        }
    }

    if (-1 < position && position < 0) {
        ViewHelper.setTranslationX(view, (view.width * position * -0.7).toFloat())
        when (leftorright) {
            "toLeft" -> ViewHelper.setAlpha(view, (1 + position) * 2f)
            "toRight" -> ViewHelper.setAlpha(view, 1 + position)
        }
    }

    if (0 < position && position < 1) {
        when (leftorright) {
            "toLeft" -> ViewHelper.setAlpha(view, 1f)
            "toRight" -> ViewHelper.setAlpha(view, Math.max(0f, (0.8f - position) * 1.5f))
        }
    }
}