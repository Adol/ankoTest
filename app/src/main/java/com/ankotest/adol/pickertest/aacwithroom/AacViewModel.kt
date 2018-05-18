package com.ankotest.adol.pickertest.aacwithroom

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import com.ankotest.adol.pickertest.api.EventVar
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.support.v4.act

class AacViewModel : ViewModel() {
    private val db by lazy {
        SignUpRepository(owner.act)
    }

    private lateinit var owner: Fragment
    private lateinit var thisdata: MutableLiveData<List<SignUpTable>>

    fun getData(owner: Fragment) :List<SignUpTable>{
        this.owner = owner
        return db.getAll()
    }

    fun getData(owner: Fragment, type: String, Fun: (List<SignUpTable>) -> Unit) {
        this.owner = owner
        bg {
            Flowable.just(db.getSignUp(type))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        thisdata = it
                        thisdata.observe(owner, Observer {
                            it!!.also(Fun)
                            //it?.let { Fun(it) }
                        })
                    })
        }
    }

    fun setClick(No: Int, Fun: (SignUpTable) -> Unit) {
        EventVar.fragmentTrans = false
        thisdata.value!![No].apply {
            also(Fun)
        }
    }

    fun upDate(No: Int) {
        EventVar.fragmentTrans = true
        bg {
            db.upDate(thisdata.value!![No])
        }
    }
}
