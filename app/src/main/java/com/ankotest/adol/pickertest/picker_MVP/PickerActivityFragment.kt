package com.ankotest.adol.pickertest.picker_MVP

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ankotest.adol.pickertest.api.DeviceInfo
import org.jetbrains.anko.*

/**
 *
 * A placeholder fragment containing a simple view.
 */

class PickerActivityFragment : Fragment(), PickerConstract.View {
    override lateinit var presenter: PickerConstract.Presenter
    private lateinit var img: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                padding = 20
                orientation = LinearLayout.VERTICAL
                img = imageView {
//                    imageResource = R.drawable.bg_don
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }.lparams(width = DeviceInfo.data.mW, height = 300)

//                button("camera") {
//                    onClick {
//                        takeImageFromCameraWithIntent()
//                    }
//                }.lparams(width = DeviceInfo.data.mW *4/5)
//                button("album") {
//                    onClick {
//                        takeImageFromAlbumWithIntent()
//                    }
//                }
            }
        }.view
    }

    override fun onResume() {
        println("Fragment_Resume")
        super.onResume()
//        presenter.start()
    }


    //------------
    private fun takeImageFromCameraWithIntent() {
        //pln("take image from camera")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 2)
    }

    private fun takeImageFromAlbumWithIntent() {
        //pln("take image from album")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 3)
    }
    override fun setImage(Bit: Bitmap) {
        img.imageBitmap = Bit
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    setImage(data.extras.get("data") as Bitmap)
                }
            }

            3 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val resolver = act.contentResolver
                    val bitmap = MediaStore.Images.Media.getBitmap(resolver, data.data)
                    setImage(bitmap)
                }
            }
            else -> {
                println("no handler onActivityReenter")
            }
        }
    }
}
