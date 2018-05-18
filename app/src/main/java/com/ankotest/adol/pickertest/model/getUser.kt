package com.ankotest.adol.pickertest.model

import com.ankotest.adol.pickertest.aacwithroom.SignUpData
import com.ankotest.adol.pickertest.aacwithroom.SignUpTable

fun getUser(name: String, type: String, sex: String): SignUpTable {

//    val e1 = arrayOf(0, 0)
    val e2 = arrayOf(1, 0)
    val e3 = arrayOf(2, 0)

    val s1 = arrayOf(3, 0)
    val s2 = arrayOf(4, 0)
    val s3 = arrayOf(5, 0)



    val E1= arrayListOf<Any>(1,1,1,1)

    val total = SignUpData(data = mutableMapOf("4/14上午" to s1, "4/14午餐" to e2, "4/14下午" to s2))
    total.data += arrayOf(Pair("4/14晚餐", e3), Pair("4/14晚上", s3))
//    total.data += arrayOf(Pair("4/15早餐", tt), Pair("4/15上午", tt1), Pair("4/15午餐", tt), Pair("4/15下午", tt1))
//    return SignUpTable(month = 4, Type = type, sex = sex, name = name, userData = total)
    return SignUpTable(month = 4, Type = type, sex = sex, name = name, userData = total,userSignUp = E1)
}