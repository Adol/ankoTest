package com.ankotest.adol.pickertest.api

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 ** Created by adol on 2018/3/16.
 */
inline fun <reified T : ViewModel> getViewModel(fact:FragmentActivity): T {
    return ViewModelProviders.of(fact)[T::class.java]
}
inline fun <reified T : ViewModel> getViewModel(fact:Fragment): T {
    return ViewModelProviders.of(fact)[T::class.java]
}

