package com.ankotest.adol.pickertest.ui

import com.google.gson.Gson
import org.json.JSONObject
@Suppress("NOTHING_TO_INLINE")
inline fun <T> T?.pln() = println(this)
inline fun <reified T: Any> Gson.jsOB2class(json: JSONObject): T = this.fromJson(json.toString(), T::class.java)