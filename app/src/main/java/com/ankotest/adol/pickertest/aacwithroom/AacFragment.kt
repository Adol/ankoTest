package com.ankotest.adol.pickertest.aacwithroom

import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ankotest.adol.pickertest.R
import com.ankotest.adol.pickertest.getViewModel
import com.ankotest.adol.pickertest.pln
import com.ankotest.adol.pickertest.ui.DeviceInfo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.recyclerview.v7.coroutines.onChildAttachStateChangeListener
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.act

class AccFragment : Fragment() {
    lateinit var title: String

    private lateinit var appDB: SignUpRepository
    private lateinit var vm: AacViewModel
    private lateinit var showItme: RecyclerView
    private lateinit var checkbg: View
    private lateinit var checkItem: RecyclerView
    private lateinit var recAdapter: ShowAdapter
    private var selectNo: Int = 0

    private fun setVm() {
        appDB = SignUpRepository(act)
        vm = getViewModel(this)
        vm.db1 = appDB

        bg {
            Flowable.just(appDB.selectSignUp())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        vm.thisdata = it
                        vm.thisdata.observe(this, Observer {
                            it?.let(::setRecAdapter)// = it?.let { recAdapter.setIt(it) }
                        })
                    })
        }
    }

    private fun setRecAdapter(data: List<SignUpTable>) {
        recAdapter.setIt(data)
    }

    private fun setShow() {
        with(showItme) {
            recAdapter = ShowAdapter(act)
            adapter = recAdapter
            onChildAttachStateChangeListener {
                onChildViewAttachedToWindow {
                    it?.onClick {
                        selectNo = getChildAdapterPosition(it)
                        vm.thisdata.value?.get(selectNo).apply {
                            this?.let(::setCheck)
                        }
                    }
                }
            }
        }
    }

    private fun setCheck(data: SignUpTable) {
        "Check".pln()
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
                }.lparams(height = 0)

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
                }.lparams(width = DeviceInfo.data.mW, height = DeviceInfo.data.mH * 4/ 5){
                    topMargin = 90
                }

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
                setShow()
                setVm()
            }
        }.view
    }
}

class CheckAdapter(private var data: SignUpTable, private var callBack: (D: Int) -> Unit) : RecyclerView.Adapter<CheckAdapter.ViewHolder>() {
    private var keys: List<String> = listOf()
    private var values: List<Array<Int>> = listOf()

    init {
        data.userData.signUp.forEach {
            keys += listOf(it.key)
            values += listOf(it.value)
//            it.value[1].pln()
        }
    }

    fun getAll() {
        values.forEach {
            it[1] = 8
        }
        data.userData.signUp.forEach {
            it.key.pln()
            it.value[1].pln()
        }
        callBack(1)
    }

    //Override -----------------------
    override fun getItemCount(): Int {
        return values.size + 2
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tempAnko = AnkoContext.createReusable(parent.context, this)
        when (viewType) {
            0 -> return ViewHolder(headOB(tempAnko))
//            0 -> return ViewHolder(Hd(parent.context))
            itemCount - 1 -> return ViewHolder(endOB(tempAnko))
        }
//        when (keys[viewType-1].indexOf("餐")) {
//            -1 -> return ViewHolder(bady2(tempAnko))
//            else ->
                return ViewHolder(bady2(tempAnko))
//        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        position.pln()
        when (position) {
            0 -> holder.itemView.find<TextView>(TextID).text = getT(data.name.plus("    "), data.month.toString(), "月廣培報到")
            itemCount - 1 -> "End".pln()
            else -> {
                "Else".pln()
                holder.itemView.find<TextView>(TextID).text = keys[position - 1]
            }
        }
    }

    private fun getT(s1: String?, s2: String, s3: String): String {
        return s1 + s2 + s3
    }

    //Test --------
    inner class V1 : AnkoComponent<CheckAdapter> {
        lateinit var t1: TextView
        lateinit var v2: View
        override fun createView(ui: AnkoContext<CheckAdapter>): View {
            v2 = ui.apply {
                t1 = textView("EEEEEEEEEE")
            }.view
            return v2
        }
    }
    //View ---------------------

    private fun headOB(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent)
                gravity = Gravity.CENTER
                textView("Head") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }.lparams(wrapContent, wrapContent)
            }
        }.view
    }

    inner class Hd @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
        lateinit var t1: TextView

        init {
            AnkoContext.createDelegate(this).apply {
                constraintLayout {
                    //                    lparams(DeviceInfo.data.mW - 180)
//                    gravity = Gravity.CENTER
                    t1 = textView("Test") {
                        gravity = Gravity.CENTER
                        id = View.generateViewId()
                        textSize = sp(12).toFloat()
                    }
                    applyConstraintSet {
                        t1 {
                            connect(
                                    TOP to TOP of PARENT_ID,
                                    START to START of PARENT_ID,
                                    END to END of PARENT_ID
                            )
                        }
                    }
                }
            }
        }
    }

    private fun bady1(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            constraintLayout {
                lparams(matchParent)
                val gap = imageView(R.drawable.ic_arrow_downward_black_24dp) {
                    id = View.generateViewId()
                }
                val t1 = textView("body1") {
                    id = TextID
//                    textColor = Color.WHITE
                    textSize = sp(9).toFloat()
                }
                val check = imageView(R.drawable.ic_check_circle_black_24dp) {
                    id = View.generateViewId()
                }.lparams(100, 100)

                applyConstraintSet {
                    gap {
                        connect(
                                TOP to TOP of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of PARENT_ID
                        )
                    }
                    t1 {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of PARENT_ID,
                                END to END of gap
                        )
                    }
                    check {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of gap,
                                END to END of PARENT_ID
                        )
                    }
                }

            }
        }.view
    }

    private fun bady2(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            constraintLayout {
                lparams(matchParent)
                val gap = imageView(R.drawable.ic_arrow_downward_black_24dp) {
                    id = View.generateViewId()
                    leftPadding = 90
                    visibility = View.INVISIBLE
                }
                val t1 = textView("body1") {
                    id = TextID
                    textSize = sp(9).toFloat()
                }
                val check = imageView(R.drawable.ic_check_circle_black_24dp) {
                    id = View.generateViewId()
                }.lparams(100, 100)

                applyConstraintSet {
                    gap {
                        connect(
                                TOP to TOP of PARENT_ID,
                                BOTTOM to BOTTOM of PARENT_ID,
                                START to START of PARENT_ID
//                                END to END of PARENT_ID
                        )
                    }
                    check {
                        connect(
                                TOP to TOP of PARENT_ID,
//                                START to START of PARENT_ID,
                                END to END of gap
                        )
                    }
                    t1 {
                        connect(
                                TOP to TOP of PARENT_ID,
                                START to START of gap,
                                END to END of PARENT_ID
                        )
                    }
                }

            }
        }.view
    }

    private fun endOB(ui: AnkoContext<CheckAdapter>): View {
        return ui.apply {
            linearLayout {
                lparams(matchParent)
                gravity = Gravity.CENTER
//                topPadding = dip(30)
                textView("確認") {
                    id = TextID
                    onClick {
                        getAll()
                    }
                }
                textView(" / ")
                textView("取消")
                applyRecursively {
                    when (it) {
                        is TextView -> {
//                            it.textColor = Color.WHITE
                            it.textSize = 30f
                        }
                    }

                }

            }
        }.view
    }

    companion object {
        const val TextID = 0
    }

}