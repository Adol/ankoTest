package com.ankotest.adol.pickertest.aacwithroom

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import com.ankotest.adol.pickertest.api.pln
import com.ankotest.adol.pickertest.model.SignUpRepository
import com.google.gson.Gson
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.support.v4.act

class CountVM : ViewModel() {
    private val db by lazy { SignUpRepository(owner.act) }
//    private var conut = listOf()

    private val tm1 by lazy { listOf("幹部", "man") }
    private val tm2 by lazy { listOf("幹部", "woman") }
    private val tm3 by lazy { listOf("學員", "man") }
    private val tm4 by lazy { listOf("學員", "woman") }

    private val eatStatus by lazy {
        MutableList(4, {
            IntArray(10, { 0 })
        })
    }

    private lateinit var owner: Fragment

    fun getCount(owner: Fragment): MutableList<IntArray> {
        this.owner = owner
        var ti = 0

        bg {
            db.getAll().let {
                it.forEach {
                    it.Type.pln()
//                    it.userData[0].pln()
                    when (listOf(it.Type, it.sex)) {
                        tm1 -> ti = 0
                        tm2 -> ti = 1
                        tm3 -> ti = 2
                        tm4 -> ti = 3
                    }

                    it.userData.forEach {
                        if (it.signUp[1] == 2) eatStatus[ti][it.signUp[0]]++
                    }
                }
            }
            Gson().toJson(eatStatus).pln()
        }
        return eatStatus
    }
}
