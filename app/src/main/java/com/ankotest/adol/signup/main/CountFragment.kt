package com.ankotest.adol.signup.main

import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ankotest.adol.signup.api.getViewModel
import com.ankotest.adol.signup.api.pln
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.textView
import org.jetbrains.anko.wrapContent

class CountFragment : Fragment() {
    lateinit var title: String
//    private var notNull = false

    private lateinit var vm: CountVM
    private lateinit var dataRecycle: RecyclerView

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        "setUserVisibleHint".pln()
        if (isVisibleToUser) {
            val act = this
            vm.showCount(act, Fun = {
                val countAdapter = CountAdapter()
                dataRecycle.adapter = countAdapter
                countAdapter.setIt(it)
            })
        }
//        else {
//            if (notNull) {
//                dataRecycle.removeAllViews()
//                (dataRecycle.adapter as CountAdapter).resert()
//            }
//        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm = getViewModel(this)
//        notNull = true
        return UI {
            constraintLayout {
                val titleTV = textView(title) {
                    id = View.generateViewId()
                    textSize = 42f
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent)

                dataRecycle = recyclerView {
                    id = View.generateViewId()
                    layoutManager = LinearLayoutManager(act)
                    //必須 BOTTOM to BOTTOM of PARENT_ID
                }.lparams(matchConstraint, matchConstraint)

                applyConstraintSet {
                    titleTV {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                    dataRecycle {
                        connect(
                                TOP to BOTTOM of titleTV,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                }
            }
//            setUserVisibleHint(true)
        }.view
    }
}

