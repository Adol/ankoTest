package com.ankotest.adol.pickertest.api

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.view.ViewManager
import com.ankotest.adol.pickertest.aacwithroom.ViewClass
import org.jetbrains.anko.custom.ankoView

/**
 ** Created by adol on 2018/3/16.
 */
//inline fun <reified T : ViewModel> getViewModel(fact: FragmentActivity): T {
//    return ViewModelProviders.of(fact)[T::class.java]
//}

inline fun <reified T : ViewModel> getViewModel(fact: Fragment): T {
    return ViewModelProviders.of(fact)[T::class.java]
}

//@Suppress("NOTHING_TO_INLINE")
//inline fun ViewManager.viewClass(theme: Int = 0) = viewClass(theme) {}
inline fun ViewManager.viewClass(theme: Int = 0, init: ViewClass.() -> Unit) = ankoView({ ViewClass(it) }, theme, init)
