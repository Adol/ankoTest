package com.ankotest.adol.signup.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import com.ankotest.adol.signup.model.SignUpRepository
import com.ankotest.adol.signup.model.SignUpTable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.support.v4.act

data class CountData(var older: MutableMap<String, Int>, var status: MutableList<IntArray>)

class CountVM : ViewModel() {
    private val db by lazy { SignUpRepository(owner.act) }

    private val tm1 by lazy { listOf("幹部", "man") }
    private val tm2 by lazy { listOf("幹部", "woman") }
    private val tm3 by lazy { listOf("學員", "man") }
    private val tm4 by lazy { listOf("學員", "woman") }

    private lateinit var owner: Fragment
    private lateinit var countData: CountData

    fun showCount(owner: Fragment, Fun: (CountData) -> Unit) {
        this.owner = owner
        countData = CountData(mutableMapOf(), MutableList(4, init = {
            IntArray(10, init = { 0 })
        }))

        bg {
            Flowable.just(db.getAll(4))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        it[0].course.forEach {
                            countData.older.set(it.course, it.status[0])
                        }
                        countT(it)
                        showUI(Fun)
                    }
        }
    }

    private fun showUI(Fun: (CountData) -> Unit) {
        val data = MutableLiveData<CountData>()
        data.postValue(countData)
        data.observe(owner, Observer {
            it!!.also(Fun)
        })
    }

    private fun countT(data: List<SignUpTable>) {
        var type = 0
        data.forEach {
            when (listOf(it.identity, it.sex)) {
                tm1 -> type = 0
                tm2 -> type = 1
                tm3 -> type = 2
                tm4 -> type = 3
            }

            it.course.forEach {
                if (it.status[1] == 2) countData.status[type][it.status[0]]++
            }
        }
    }
}
