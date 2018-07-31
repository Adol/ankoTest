package com.ankotest.adol.pickertest.api

import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import org.json.JSONObject

@Suppress("NOTHING_TO_INLINE")
inline fun <T> T?.pln() = println(this)

inline fun <reified T : Any> Gson.jsOB2class(json: JSONObject): T = this.fromJson(json.toString(), T::class.java)

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.setT(T: TextView, textS: Int = 16, visibInt: Int = View.GONE): TextView {
    T.apply {
        textSize = textS.toFloat()
        visibility = visibInt
        gravity = Gravity.CENTER
    }
    return T
}