package com.ankotest.adol.pickertest.picker_MVP

import android.graphics.Bitmap
import com.ankotest.adol.pickertest.BasePresenter
import com.ankotest.adol.pickertest.BaseView

class PickerConstract {
    interface Presenter:BasePresenter

    interface View:BaseView<Presenter>{
        fun setImage(Bit:Bitmap)
    }
}