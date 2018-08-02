package com.ankotest.adol.signup.main

import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ankotest.adol.signup.api.DeviceInfo
import com.ankotest.adol.signup.api.EventVar.Companion.nowMonth
import com.ankotest.adol.signup.api.TransFormSP.Companion.onTransform
import com.ankotest.adol.signup.api.ViewClass
import com.ankotest.adol.signup.api.viewClass
import com.ankotest.adol.signup.model.SignUpRepository
import com.ankotest.adol.signup.model.SignUpTable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.ctx
import org.jetbrains.anko.setContentView

/**
 **Created by adol on 2018/3/19.
 */

class AacActivity : AppCompatActivity() {
    private var baseUi = AacUi(supportFragmentManager)
    private lateinit var db: SignUpRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceInfo.getInfo(this)
        db = SignUpRepository(ctx)
        baseUi.setContentView(this)
        nowMonth = 4
//        dete()
        bg {
            if (db.getAll(nowMonth).isEmpty()) {
                //read File
                val inputStream = assets.open("json2.txt")
                val inputString = inputStream.bufferedReader().use { it.readText() }
                //json to List
                val type = object : TypeToken<List<SignUpTable>>() {}.type
                val foJson = Gson().fromJson<List<SignUpTable>>(inputString, type)
                Flowable.just(foJson.forEach { db.insert(it) })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            baseUi.viewpagerFm.show()
                        }
            }
        }

    }
//    fun dete() {
//        bg {
//            db.deleteAll()
//        }
//    }
}

class AacUi(fragmentManager: FragmentManager) : AnkoComponent<AacActivity> {
    val viewpagerFm: ViewpagerFm = ViewpagerFm(fragmentManager)
    private lateinit var myViewpager: ViewClass

    override fun createView(ui: AnkoContext<AacActivity>): View {
        ui.apply {
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
        }
        myViewpager.setPageTransformer(false, ::onTransform)
        return ui.view
    }
}

class ViewpagerFm(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val fragments = linkedMapOf<String, Fragment>()

    init {
        fragments["幹部"] = SignUpFragment()
        fragments["學員"] = SignUpFragment()
        fragments["回報"] = CountFragment()
    }

    fun show() {
        (fragments["幹部"] as SignUpFragment).setVm()
        (fragments["學員"] as SignUpFragment).setVm()
    }

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

//        override fun getItemPosition(ob: Any): Int {
//        "getItemPosition".pln()
//        when (ob){
//            fm.fragments[2] ->return PagerAdapter.POSITION_NONE
////            fm.fragments[1] ->return PagerAdapter.POSITION_NONE
//        }
//        return POSITION_UNCHANGED
//    }
}