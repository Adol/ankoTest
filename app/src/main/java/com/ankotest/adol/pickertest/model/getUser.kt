package com.ankotest.adol.pickertest.model

fun getUser(name: String, Identity: String, sex: String): SignUpTable {

    val e1 = SignUpData("4/14午餐", mutableListOf(0, 0, 0))
    val e2 = SignUpData("4/14晚餐", mutableListOf(1, 0, 0))
    val e3 = SignUpData("4/15早餐", mutableListOf(2, 0, 0))

    val s1 = SignUpData("4/14上午", mutableListOf(5, 0, 0))
    val s2 = SignUpData("4/14下午", mutableListOf(6, 0))
    val s3 = SignUpData("4/14晚上", mutableListOf(7, 0, 0))
    val s4 = SignUpData("4/15上午", mutableListOf(8, 0, 0))
//    val s5 = SignUpData.Data("4/15下午", listOf(0,0,0))

    val total = listOf(s1,e1,s2,e2,s3,e3,s4)
    return SignUpTable(month = 4, Identity = Identity, sex = sex, name = name, userData = total)
}