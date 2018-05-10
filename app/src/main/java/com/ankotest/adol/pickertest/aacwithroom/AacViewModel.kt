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

    fun getselectSignUp(owner:Fragment, Fun:(List<SignUpTable>) ->Unit){
        bg {
//            db1.delall()
//            db1.insert(getUser("AD"))
//            db1.insert(getUser("AD1"))
//            db1.insert(getUser("AD2"))
//            db1.insert(getUser("AD3"))
//            db1.insert(getUser("AD4"))
//            db1.insert(getUser("AD5"))
//            db1.insert(getUser("AD6"))
//            db1.insert(getUser("AD7"))
//            db1.insert(getUser("AD8"))
//            db1.insert(getUser("AD9"))
//            db1.insert(getUser("AD10"))
//            db1.insert(getUser("AD11"))
//            db1.insert(getUser("AD12"))
            Flowable.just(db1.selectSignUp())
            Flowable.just(db1.selectSignUp())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        thisdata = it
                        thisdata.observe(owner, Observer {
                            it?.let(Fun)// = it?.let { Fun(it) }
                        })
                    })
        }
    }
}
