package com.ankotest.adol.pickertest.api

import android.content.Context

/**
 *
 * Created by adol on 2018/3/16.
 */

class AppInfo(val mW: Int, val mH: Int, val mDen: Float, val mD: Int)

class DeviceInfo {
    companion object {
        lateinit var data: AppInfo

        fun getInfo(ui: Context) {
            data = AppInfo(
                    ui.resources.displayMetrics.widthPixels,
                    ui.resources.displayMetrics.heightPixels,
                    ui.resources.displayMetrics.density,
                    ui.resources.displayMetrics.densityDpi)

        }
    }
}
