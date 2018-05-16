package com.ankotest.adol.pickertest.aacwithroom

import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ankotest.adol.pickertest.api.getViewModel
import com.ankotest.adol.pickertest.api.pln
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sp
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.textView
import org.jetbrains.anko.wrapContent

class CountFragment : Fragment() {
    lateinit var title: String
    private lateinit var vm: AacViewModel

//    init {
//        vm = getViewModel(this)
//    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        isVisibleToUser.pln()
        if (isVisibleToUser) setVm()
        super.setUserVisibleHint(isVisibleToUser)
    }

    private lateinit var totalDa: Array<Int>
    private fun setVm() {
        bg {
            vm.getData(this).forEach {
                it.name.pln()
            }
        }
        "Ｄ@@@@@@@@@@@@@@@@@@@@@".pln()
//        vm.getData(this, "All", {
//            "DB Change ".pln()
//            totalDa = arrayOf(0,0,0,0,0,0)
//            it.forEach {
//                it.userData.data.forEach {
//
////                    "${it.key}".pln()
////                    it.value.forEach {
////                        it.pln()
////                    }
//                    when (it.value[1]) {
//                        2 -> totalDa[it.value[0]] ++
//                    }
//                }
//            }
//            totalDa.forEach {
//                it.pln()
//            }
//        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm = getViewModel(this)
        return UI {
            constraintLayout {
                val titleTV = textView(title) {
                    id = View.generateViewId()
                    textSize = sp(14).toFloat()
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent)

                applyConstraintSet {
                    titleTV {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                }
            }
        }.view
    }
}

