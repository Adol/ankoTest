package com.ankotest.adol.pickertest

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.coroutines.onChildAttachStateChangeListener
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx


class ViewID {
    companion object {
        const val IMAGEVIEW_ID = 1//图片的id
        const val TITLE_ID = 2//页面中标题的id
        const val RECYCLERVIEW_ID = 3//页面中滚动视图的id
        const val CONTENT_LAYOUT = 4//页面中容器的id
        const val rootID: Int = 0
        const val RECID: Int = 1
        const val ViewpageID: Int = 6
    }
}

class testMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TTActivityUI().setContentView(this)

//        val view = MovieListFragment()
//        supportFragmentManager.beginTransaction().add(ViewID.CONTENT_LAYOUT, view).commit()

    }
}

class TTActivityUI : AnkoComponent<testMainActivity> {
//    private lateinit var recyclerView: RecyclerView

    override fun createView(ui: AnkoContext<testMainActivity>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            id = ViewID.CONTENT_LAYOUT//设置id，方便添加Fragment

            recyclerView {
                //设置滚动视图RecyclerView
//                    backgroundColor = Color.BLUE
                id = ViewID.RECYCLERVIEW_ID//控件的id
                adapter = MovieListAdapter(ctx, listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
                layoutManager = LinearLayoutManager(ctx)
                onChildAttachStateChangeListener {
                    onChildViewAttachedToWindow {
                        it?.onClick {
                            println("DDDDDD")
                        }
                    }
                }
            }.lparams(width = matchParent, height = wrapContent)//控件的宽高
//            recyclerView = rootView.find(ViewID.RECYCLERVIEW_ID)  // 根据id获取指定控件
        }
    }
}

class MovieListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var rootView: View
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        recyclerView.layoutManager = LinearLayoutManager(ctx)
//        recyclerView.onChildAttachStateChangeListener {
//            onChildViewAttachedToWindow {
//                print("@@@@@@@@@@@@")
//                it?.onClick {
//                    println("DDDDDD")
//                }
//            }
//        }
        recyclerView.adapter = MovieListAdapter(ctx, listOf(1, 1, 1))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = UI {
            verticalLayout {
                //                lparams(width= matchParent,height = matchParent)//设置布局的宽高
                textView("!@#$") {
                    //设置tile
                    textSize = 18f//字体大小
                    paint.isFakeBoldText = true//设置粗体
                    gravity = Gravity.CENTER_HORIZONTAL//控件中字体水平居中
                    verticalPadding = dip(10) //控件的上下Padding值
                    textColor = Color.WHITE//字体颜色
                    backgroundResource = R.color.colorPrimaryDark//控件的背景
                }.lparams(width = matchParent, height = wrapContent)//控件的宽高

                recyclerView = recyclerView {
                    //设置滚动视图RecyclerView
//                    backgroundColor = Color.BLUE
                    id = ViewID.RECYCLERVIEW_ID//控件的id
//                    adapter = MovieListAdapter(ctx, listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
//                    layoutManager = LinearLayoutManager(act)
                    onChildAttachStateChangeListener {
                        onChildViewAttachedToWindow {
                            "##############".pln()
                            it?.onClick {
                                println("DDDDDD")
                            }
                        }
                    }
                }.lparams(width = matchParent, height = wrapContent)//控件的宽高
            }
        }.view
//        recyclerView = rootView.find(ViewID.RECYCLERVIEW_ID)  // 根据id获取指定控件
        return rootView
    }

    companion object {
        fun instance(): MovieListFragment {
            return MovieListFragment()
        }

//        val TAG = MovieListFragment::class.java.simpleName
        //        val instance = MovieListFragment()
//        val TAG = MovieListFragment::class.java.simpleName
    }
}

class MovieListAdapter(private val context: Context, private var list: List<Int>) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private val ankoContext = AnkoContext.createReusable(context, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MovieListAdapterUI().createView(ankoContext))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        println(position)
//        holder.titleTv.text=list[position].title
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val imageView = rootView.findViewById(ViewID.IMAGEVIEW_ID) as ImageView
        val titleTv: TextView = rootView.find(ViewID.TITLE_ID)
    }
}

/**
 * 使用AnkoComponent来构建item的UI
 */
class MovieListAdapterUI : AnkoComponent<MovieListAdapter> {
    override fun createView(ui: AnkoContext<MovieListAdapter>) = with(ui) {
        linearLayout {
            //            lparams(width = matchParent,height = wrapContent)
//            backgroundColor = Color.BLACK
            padding = dip(10)//设置padding
            imageView {
                id = ViewID.IMAGEVIEW_ID//设置id
                scaleType = ImageView.ScaleType.CENTER_CROP//图片中心裁剪
            }.lparams(width = dip(60), height = dip(60))//设置图片的宽高
            textView("RRRRR") {
                id = ViewID.TITLE_ID//设置id
                textSize = 14f//字体大小
                paint.isFakeBoldText = true//粗体
            }.lparams {
                leftMargin = dip(10)//左边偏移量
                gravity = Gravity.CENTER_VERTICAL//竖直居中
            }
        }
    }
}


