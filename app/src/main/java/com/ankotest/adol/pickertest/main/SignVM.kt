package com.ankotest.adol.pickertest.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import com.ankotest.adol.pickertest.model.SignUpRepository
import com.ankotest.adol.pickertest.model.SignUpTable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.support.v4.act

class SignVM : ViewModel() {
    private lateinit var owner: Fragment
    private val db by lazy { SignUpRepository(owner.act) }

    private lateinit var dataUserSignUp: MutableLiveData<List<SignUpTable>>

    fun getData(owner: Fragment, identity: String, Fun: (List<SignUpTable>) -> Unit) {
        this.owner = owner
        bg {
            Flowable.just(db.getSignUp(identity = identity,mt=6))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{
                        dataUserSignUp = it
                        dataUserSignUp.observe(owner, Observer {
                            it!!.also(Fun)
                        })
                    }
        }
    }

    fun setClick(No: Int, Fun: (SignUpTable) -> Unit) {
        dataUserSignUp.value!![No].apply {
            also(Fun)
        }
    }

    fun upDate(No: Int) {
        bg {
            db.upDate(dataUserSignUp.value!![No])
            dataUserSignUp.postValue(dataUserSignUp.value)
        }
    }
}
