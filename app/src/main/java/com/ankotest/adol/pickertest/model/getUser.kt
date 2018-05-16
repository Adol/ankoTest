package com.ankotest.adol.pickertest.model

import com.ankotest.adol.pickertest.aacwithroom.SignUP
import com.ankotest.adol.pickertest.aacwithroom.SignUpTable

fun getUser(name: String, type: String, sex: String): SignUpTable {

    val tt = arrayOf(0, 0)
    val tt1 = arrayOf(1, 0)
    val total = SignUP(signUp = mutableMapOf("4/14上午" to tt1, "4/14午餐" to tt, "4/14下午" to tt1))
    total.signUp += arrayOf(Pair("4/14晚餐", tt), Pair("4/14晚上", tt1))
//    total.signUp += arrayOf(Pair("4/15早餐", tt), Pair("4/15上午", tt1), Pair("4/15午餐", tt), Pair("4/15下午", tt1))
    return SignUpTable(month = 4, Type = type, sex = sex, name = name, userData = total)
}