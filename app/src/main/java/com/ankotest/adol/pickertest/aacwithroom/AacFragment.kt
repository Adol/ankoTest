package com.ankotest.adol.pickertest.aacwithroom

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ankotest.adol.pickertest.ui.DeviceInfo
import com.ankotest.adol.pickertest.ui.getViewModel
import com.ankotest.adol.pickertest.ui.pln
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.recyclerview.v7.coroutines.onChildAttachStateChangeListener
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.act

class AccFragment : Fragment() {
    lateinit var title: String

    private lateinit var vm: AacViewModel
    private lateinit var showItme: RecyclerView
    private lateinit var checkbg: View
    private lateinit var checkItem: RecyclerView
    private lateinit var recAdapter: ShowAdapter

    private fun setShow() {

        with(showItme) {
            recAdapter = ShowAdapter(act)
            adapter = recAdapter
            //adapter 加入 recAdapter
            onChildAttachStateChangeListener {
                //顯示到視窗時
                onChildViewAttachedToWindow {
                    //it -> view?
                    it?.onClick {
                        // getChildAdapterPosition(it) -> view的編號
                        vm.thisdata.value?.get(getChildAdapterPosition(it)).let {
                            it?.let(::setCheck)
                        }
                    }
                }
            }
        }
    }

    private  fun setVm() {
        vm = getViewModel(this)
        vm.db1 = SignUpRepository(act)
        vm.getselectSignUp(this,::setRecAdapter)
    }

    private fun setRecAdapter(data: List<SignUpTable>) {
        recAdapter.setIt(data)
    }

    private fun setCheck(data: SignUpTable) {
//        "Check".pln()
        checkbg.visibility = View.VISIBLE
        checkItem.visibility = View.VISIBLE
        checkItem.adapter = CheckAdapter(data, ::removeItemRec)
    }

    private fun removeItemRec(i: Int) {
        i.pln()
        checkbg.visibility = View.INVISIBLE
        checkItem.visibility = View.INVISIBLE
        checkItem.removeAllViews()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        launch(UI) {
            delay(500)
            setShow()
            setVm()
        }
        return UI {
            constraintLayout {
                val titleTV = textView(title) {
                    id = View.generateViewId()
                    textSize = sp(14).toFloat()
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent)

                showItme = recyclerView {
                    id = View.generateViewId()
                    layoutManager = LinearLayoutManager(act)
                }.lparams(matchConstraint,matchConstraint)

                checkbg = view {
                    id = View.generateViewId()
                    backgroundColor = Color.BLACK
                    alpha = 0.7f
                    visibility = View.INVISIBLE
                }.lparams(0, 0)

                checkItem = recyclerView {
                    id = View.generateViewId()
                    setBackgroundColor(Color.parseColor("#ffffffff"))
                    layoutManager = LinearLayoutManager(act)
                    visibility = View.INVISIBLE
                }.lparams(width = DeviceInfo.data.mW, height = DeviceInfo.data.mH * 4 / 5)

                applyConstraintSet {
                    titleTV {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                    showItme {
                        connect(
                                TOP to BOTTOM of titleTV,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    checkbg {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    checkItem {
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