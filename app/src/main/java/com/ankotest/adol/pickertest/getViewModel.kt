package com.ankotest.adol.pickertest

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.google.gson.Gson
import org.json.JSONObject

/**
 ** Created by adol on 2018/3/16.
 */
inline fun <reified T : ViewModel> getViewModel(fact:FragmentActivity): T {
    return ViewModelProviders.of(fact)[T::class.java]
}
inline fun <reified T : ViewModel> getViewModel(fact:Fragment): T {
    return ViewModelProviders.of(fact)[T::class.java]
}

fun <T> T?.pln() = println(this)

inline fun <reified T: Any> Gson.jsOB2class(json: JSONObject): T = this.fromJson(json.toString(), T::class.java)
