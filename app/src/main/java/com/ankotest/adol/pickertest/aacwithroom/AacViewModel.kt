package com.ankotest.adol.pickertest.aacwithroom

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.coroutines.experimental.bg

class AacViewModel : ViewModel() {
    lateinit var db1: SignUpRepository
    lateinit var thisdata: MutableLiveData<List<SignUpTable>>


    fun getSignUp(owner:Fragment, Fun:(List<SignUpTable>) ->Unit){
        bg {
//            db1.delall()
//            db1.insert(getUser("AD","幹部","man"))
//            db1.insert(getUser("AD1","幹部","man"))
//            db1.insert(getUser("AD2","幹部","man"))
//            db1.insert(getUser("ADA","幹部","woman"))
//            db1.insert(getUser("ADA1","幹部","woman"))
//            db1.insert(getUser("ADA2","幹部","woman"))
            Flowable.just(db1.selectSignUp())
            Flowable.just(db1.selectSignUp())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        thisdata = it
                        thisdata.observe(owner, Observer {
                            it!!.let(Fun)// = it?.let { Fun(it) }
                        })
                    })
        }
    }
}
