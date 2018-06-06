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
import com.ankotest.adol.pickertest.api.DeviceInfo
import com.ankotest.adol.pickertest.api.Easing
import com.ankotest.adol.pickertest.api.EventVar
import com.ankotest.adol.pickertest.api.getViewModel
import com.ankotest.adol.pickertest.model.SignUpTable
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

class SignUpFragment : Fragment() {
    lateinit var title: String
    private lateinit var vm: SignVM
    private lateinit var showItme: RecyclerView
    private var itemNum = 0

    private lateinit var detailItem: RecyclerView

    private fun setVm() {
        //VM取得db資料
        vm = getViewModel(this)
        vm.getData(this, type = title, Fun = {
            val selectItmeadapter = SelectSignUPAd()
            showItme.adapter = selectItmeadapter
            selectItmeadapter.setIt(it)
        })
    }

    private fun setItem() {
        showItme.apply {
            //adapter 加入畫面時
            onChildAttachStateChangeListener {
                //顯示到視窗時
                onChildViewAttachedToWindow {
                    //it -> view? ->!!.
                    it!!.onClick {
                        itemNum = getChildAdapterPosition(it)
                        // getChildAdapterPosition(it) -> view的編號
                        vm.setClick(itemNum, ::showSelectUI)
                    }
                }
            }
        }
    }

    private fun showSelectUI(data: SignUpTable) {
        EventVar.fragmentTrans = false
        please {
            animate(detailItem) toBe {
                scale(1f, 0.3f)
            }
        }.now()

        please(interpolator = Easing.Type(Easing.easeInOutSine)) {
            //            animate(checkbg) toBe {
//                alpha(0.7f)
//            }
            animate(detailItem) toBe {
                alpha(1f)
                textColor(Color.BLUE)
                scale(1f, 1f)
            }
        }.start()
        launch(UI) {
            delay(100)
            detailItem.adapter = UserCheckAdapter(listOf(data.name, data.name), data.userData, ::removeUI)
        }
    }

    private fun removeUI() {
        if (EventVar.hasChange) vm.upDate(itemNum)
        EventVar.fragmentTrans = true
        please(interpolator = DecelerateInterpolator()) {
            //            animate(checkbg) toBe {
//                alpha(0f)
//                invisible()
//            }
            animate(detailItem) toBe {
                //                sameAlphaAs(checkbg)
                alpha(0f)
                scale(1f, 0.3f)
                invisible()
                detailItem.removeAllViews()
            }
        }.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        launch(UI) {
            setVm()
            setItem()
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

                detailItem = recyclerView {
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
                    detailItem {
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

