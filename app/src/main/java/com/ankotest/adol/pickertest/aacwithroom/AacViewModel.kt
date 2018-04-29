package com.ankotest.adol.pickertest.aacwithroom

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import com.ankotest.adol.pickertest.ui.pln
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.coroutines.experimental.bg

class AacViewModel : ViewModel() {
    lateinit var db1: SignUpRepository
    lateinit var thisdata: MutableLiveData<List<SignUpTable>>

    fun getselectSignUp(owner:Fragment, Fun:(List<SignUpTable>) ->Unit){
        bg {
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

    private fun setappDB(i: Int) {
        thisdata.value?.get(i).pln()
        bg {
            db1.upDate(thisdata.value?.get(i))
        }

    }

    fun V1(i: Int) {
        thisdata.value?.get(i).apply {
            this?.name = "TTTTT"
        }
        setappDB(i)
    }
}
