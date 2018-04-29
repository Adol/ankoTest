package com.ankotest.adol.pickertest.aacwithroom

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ankotest.adol.pickertest.ui.pln
import org.jetbrains.anko.coroutines.experimental.bg

class AacViewModel : ViewModel() {
    lateinit var thisdata: MutableLiveData<List<SignUpTable>>
    //    var tempI:Int = 0
    lateinit var db1: SignUpRepository


    private fun setappDB(i: Int) {
        thisdata.value?.get(i).pln()
        bg {
            db1.upDate(thisdata.value?.get(i))
        }
//        db1.insert(getUser("AD##"))

    }

    fun V1(i: Int) {
        thisdata.value?.get(i).apply {
            this?.name = "TTTTT"
        }
        setappDB(i)
    }
}
