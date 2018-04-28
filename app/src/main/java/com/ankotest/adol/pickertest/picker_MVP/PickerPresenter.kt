package com.ankotest.adol.pickertest.picker_MVP

class PickerPresenter(var view: PickerConstract.View) : PickerConstract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        println("MVP Start")
    }

    override fun click(i: Int) {

    }
}