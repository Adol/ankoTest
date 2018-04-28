package com.ankotest.adol.pickertest.model

import com.ankotest.adol.pickertest.aacwithroom.SignUP
import com.ankotest.adol.pickertest.aacwithroom.SignUpTable

fun getUser(name: String): SignUpTable {
    val tt = arrayOf<Int>(1, 1)
    val total = SignUP(signUp = mutableMapOf("4/14上午" to tt, "4/14午餐" to tt, "4/14下午" to tt))
    total.signUp += arrayOf(Pair("4/14晚餐", tt),Pair("4/14晚上", tt))
    total.signUp += arrayOf(Pair("4/15早餐", tt),Pair("4/15上午", tt),Pair("4/15午餐", tt),Pair("4/15下午", tt))
    return SignUpTable(month = 4, name = name, userData = total)
}