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
import android.view.animation.DecelerateInterpolator
import com.ankotest.adol.pickertest.api.*
import com.github.florent37.kotlin.pleaseanimate.please
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.coroutines.onChildAttachStateChangeListener
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sp
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.textView
import org.jetbrains.anko.wrapContent

class CountFragment : Fragment() {
    lateinit var title: String

    private lateinit var vm: AacViewModel
    private lateinit var showItme: RecyclerView
//    lateinit var dbName: String
    //    private lateinit var checkbg: View
    private lateinit var checkItem: RecyclerView
    private lateinit var recAdapter: ShowAdapter
//    var Bool= true

    private fun setShow() {
        with(showItme) {
            recAdapter = ShowAdapter(act)
            adapter = recAdapter
            //adapter 加入 recAdapter
            onChildAttachStateChangeListener {
                //顯示到視窗時
                onChildViewAttachedToWindow {
                    //it -> view?
                    it!!.onClick {
                        // getChildAdapterPosition(it) -> view的編號
                        vm.thisdata.value!![(getChildAdapterPosition(it))].also {
                            it.also(::setCheck)
                        }
                    }
                }
            }
        }
    }


    private fun setVm() {
        //VM取得db資料
        vm = getViewModel(this)
//        dbName.pln()
        when (title) {
            "幹部" -> {
                vm.db1 = SignUpRepository(act)
                vm.getSignUp(this, ::setRecAdapter)
            }
        }
    }

    private fun setRecAdapter(data: List<SignUpTable>) {
        recAdapter.setIt(data)
    }

    private fun setCheck(data: SignUpTable) {
        EventVar.fragmentTrans = false
        please {
            animate(checkItem) toBe {
                scale(1f, 0.3f)
            }
        }.now()

        please(interpolator = Easing.Type(Easing.easeInOutSine)) {
            //            animate(checkbg) toBe {
//                alpha(0.7f)
//            }
            animate(checkItem) toBe {
                alpha(1f)
                textColor(Color.BLUE)
                scale(1f, 1f)
            }
        }.start()
        launch(UI) {
            delay(100)
            checkItem.adapter = CheckAdapter(data, ::removeItemRec)
        }
    }

    private fun removeItemRec(i: Int) {
        i.pln()//回傳值
        EventVar.fragmentTrans = true
        please(interpolator = DecelerateInterpolator()) {
            //            animate(checkbg) toBe {
//                alpha(0f)
//                invisible()
//            }
            animate(checkItem) toBe {
                //                sameAlphaAs(checkbg)
                alpha(0f)
                scale(1f, 0.3f)
                invisible()
                checkItem.removeAllViews()
            }
        }.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        launch(UI) {
            delay(200)
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
                }.lparams(matchConstraint, matchConstraint)

//                checkbg = view {
//                    id = View.generateViewId()
//                    backgroundColor = Color.BLACK
//                    visibility = View.INVISIBLE
//                    alpha = 0f
//                }.lparams(0, 0)

                checkItem = recyclerView {
                    id = View.generateViewId()
                    setBackgroundColor(Color.parseColor("#ffffffff"))
                    layoutManager = LinearLayoutManager(act)
                    visibility = View.INVISIBLE
                    alpha = 0f
                }.lparams(DeviceInfo.data.mW, DeviceInfo.data.mH)

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
//                    checkbg {
//                        connect(
//                                TOP to TOP of PARENT_ID,
//                                START to START of PARENT_ID,
//                                END to END of PARENT_ID,
//                                BOTTOM to BOTTOM of PARENT_ID
//                        )
//                    }
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

