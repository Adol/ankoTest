package com.ankotest.adol.pickertest.main

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.animation.DecelerateInterpolator
import com.ankotest.adol.pickertest.api.Easing
import com.ankotest.adol.pickertest.api.EventVar
import com.ankotest.adol.pickertest.api.getViewModel
import com.ankotest.adol.pickertest.api.pln
import com.ankotest.adol.pickertest.model.SignUpTable
import com.github.florent37.kotlin.pleaseanimate.please
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.recyclerview.v7.coroutines.onChildAttachStateChangeListener
import org.jetbrains.anko.recyclerview.v7.coroutines.onItemTouchListener
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.act


class SignUpFragment : Fragment() {
    lateinit var title: String
    private lateinit var vm: SignVM
    private lateinit var showUser: RecyclerView
    private var childPosition = 0

    private lateinit var userCheckAD: RecyclerView
    private lateinit var checkbg: View

    fun setVm() {
        //VM取得db資料
        setItem()
        vm = getViewModel(this)
        vm.getData(this, identity  = title, Fun = {
            val selectItmeadapter = SelectSignUPAd()
            showUser.adapter = selectItmeadapter
            selectItmeadapter.setIt(it)
        })
    }

    private fun setItem() {
        showUser.apply {
            //adapter 加入畫面時
            onChildAttachStateChangeListener {
                //                顯示到視窗時
                onChildViewAttachedToWindow {
                    it!!.onClick {
                        childPosition = getChildAdapterPosition(it)
                        // getChildAdapterPosition(it) -> view的編號
                        vm.setClick(childPosition, ::showSelectUI)
                    }
                }
            }
        }
        userCheckAD.apply {
            val mGestureDetector = GestureDetector(act, UserCheckGestureSimple())
            onItemTouchListener {
                onInterceptTouchEvent { rec, me ->
                    if (mGestureDetector.onTouchEvent((me))) {
                        rec!!.findChildViewUnder(30f, me!!.y - 50).let {
                            (it as? UserCheckAD.ButtonBody)?.clickMove()
                        }
                    }
                    true
                }
            }
        }
    }

    private fun showSelectUI(data: SignUpTable) {
        EventVar.fragmentTrans = false
        please {
            animate(userCheckAD) toBe {
                scale(1f, 0.3f)
            }
        }.now()

        please(interpolator = Easing.Type(Easing.easeInOutSine)) {
            animate(checkbg) toBe {
                alpha(0.7f)
            }
            animate(userCheckAD) toBe {
                alpha(1f)
                textColor(Color.BLUE)
                scale(1f, 1f)
            }
        }.start()
        launch(UI) {
            delay(100)
            userCheckAD.adapter = UserCheckAD(listOf(data.name, data.month.toString()), data.course, ::showsignUpUi)
        }
    }

    private fun showsignUpUi() {
        if (EventVar.hasChange) vm.upDate(childPosition)

        please(interpolator = DecelerateInterpolator()) {
            animate(checkbg) toBe {
                alpha(0f)
                invisible()
            }
            animate(userCheckAD) toBe {
                //                sameAlphaAs(checkbg)
                alpha(0f)
                scale(1f, 0.3f)
                invisible()
                userCheckAD.removeAllViews()
            }
        }.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        launch(UI) {
            delay(1000)
            setVm()
        }
        return UI {
            constraintLayout {
                val titleTV = textView(title) {
                    id = View.generateViewId()
                    textSize = 36f
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent)

                showUser = recyclerView {
                    id = View.generateViewId()
                    layoutManager = LinearLayoutManager(act)
                }.lparams(0, 0)

                checkbg = view {
                    id = View.generateViewId()
                    backgroundColor = Color.BLACK
                    visibility = View.INVISIBLE
                    alpha = 0f
                }.lparams(0, 0)

                userCheckAD = recyclerView {
                    id = View.generateViewId()
                    setBackgroundColor(Color.parseColor("#ffffffff"))
                    layoutManager = LinearLayoutManager(act)
                    visibility = View.INVISIBLE
                    alpha = 0f
                }.lparams(0, 0)

                applyConstraintSet {
                    titleTV {
                        connect(
                                TOP to TOP of PARENT_ID margin dip(5),
                                START to START of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                    showUser {
                        connect(
                                TOP to TOP of PARENT_ID margin sp(40),
                                BOTTOM to BOTTOM of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                    checkbg {
                        connect(
                                TOP to TOP of PARENT_ID,
//                                TOP to BOTTOM of titleTV,
//                                START to START of PARENT_ID,
                                END to END of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }
                    userCheckAD {
                        connect(
                                TOP to TOP of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                }
                titleTV.height.pln()
            }
        }.view
    }
}