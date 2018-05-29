package com.ankotest.adol.pickertest.aacwithroom

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import com.ankotest.adol.pickertest.api.pln
import com.ankotest.adol.pickertest.model.SignUpRepository
import com.ankotest.adol.pickertest.model.SignUpTable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.support.v4.act

data class CountData(var old: MutableMap<String, Int>, var status: MutableList<IntArray>)

class CountVM : ViewModel() {
    private val db by lazy { SignUpRepository(owner.act) }

    private val tm1 by lazy { listOf("幹部", "man") }
    private val tm2 by lazy { listOf("幹部", "woman") }
    private val tm3 by lazy { listOf("學員", "man") }
    private val tm4 by lazy { listOf("學員", "woman") }

    private lateinit var owner: Fragment

    private lateinit var countData: CountData

    fun getCount(owner: Fragment, Fun: (CountData) -> Unit) {
        this.owner = owner
        countData = CountData(mutableMapOf(), MutableList(4, {
            IntArray(10, { 0 })
        }))

        bg {
            Flowable.just(db.getAll())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        it[0].userData.forEach {
                            countData.old.set(it.title, it.signUp[0])
                        }
                        countT(it)
                        val data = MutableLiveData<CountData>()
                        data.postValue(countData)
                        data.observe(owner, Observer {
                            "getCount".pln()
                            it!!.also(Fun)
                        })
                    })
        }
    }

    private fun countT(data: List<SignUpTable>) {
        var ti = 0
        data.forEach {
            when (listOf(it.Identity, it.sex)) {
                tm1 -> ti = 0
                tm2 -> ti = 1
                tm3 -> ti = 2
                tm4 -> ti = 3
            }

            it.userData.forEach {
                if (it.signUp[1] == 2) countData.status[ti][it.signUp[0]]++
            }
        }
    }
}