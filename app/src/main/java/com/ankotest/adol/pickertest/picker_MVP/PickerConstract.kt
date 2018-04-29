package com.ankotest.adol.pickertest.picker_MVP

import android.graphics.Bitmap

class PickerConstract {
    interface Presenter: BasePresenter

    interface View: BaseView<Presenter> {
        fun setImage(Bit:Bitmap)
    }
}