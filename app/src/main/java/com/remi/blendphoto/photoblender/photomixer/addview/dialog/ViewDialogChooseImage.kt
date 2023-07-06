package com.remi.blendphoto.photoblender.photomixer.addview.dialog

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.Utils

class ViewDialogChooseImage(context: Context) : RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val ivGallery : ImageView
    val ivCamera : ImageView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundResource(R.drawable.boder_dialog)

        val ll = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }

        ivGallery = ImageView(context).apply {
            setImageResource(R.drawable.im_choose_gallery)
            scaleType = ImageView.ScaleType.FIT_XY
        }
        ll.addView(ivGallery, LinearLayout.LayoutParams(-1, -1, 1F).apply {
            rightMargin = (4.72f * w).toInt()
        })

        ivCamera = ImageView(context).apply {
            setImageResource(R.drawable.im_choose_camera)
            scaleType = ImageView.ScaleType.FIT_XY
        }
        ll.addView(ivCamera, LinearLayout.LayoutParams(-1, -1, 1F).apply {
            leftMargin = (4.72f * w).toInt()
        })

        addView(ll, LayoutParams(-1, (39.259f * w).toInt()).apply {
            leftMargin = (9.72f * w).toInt()
            rightMargin = (9.72f * w).toInt()
            addRule(CENTER_IN_PARENT, TRUE)
        })
    }

    fun chooseOption(pos: Int) {
        when(pos){
            0 -> {
                ivGallery.background = Utils.createBackground(
                    intArrayOf(ContextCompat.getColor(context, R.color.trans)),
                    (2.5f * w).toInt(),
                    (0.34f * w).toInt(),
                    ContextCompat.getColor(context, R.color.color_main)
                )

                ivCamera.background = Utils.createBackground(
                    intArrayOf(ContextCompat.getColor(context, R.color.trans)),
                    (2.5f * w).toInt(),
                    (0.34f * w).toInt(),
                    ContextCompat.getColor(context, R.color.trans)
                )
            }
            1 -> {
                ivGallery.background = Utils.createBackground(
                    intArrayOf(ContextCompat.getColor(context, R.color.trans)),
                    (2.5f * w).toInt(),
                    (0.34f * w).toInt(),
                    ContextCompat.getColor(context, R.color.trans)
                )

                ivCamera.background = Utils.createBackground(
                    intArrayOf(ContextCompat.getColor(context, R.color.trans)),
                    (2.5f * w).toInt(),
                    (0.34f * w).toInt(),
                    ContextCompat.getColor(context, R.color.color_main)
                )
            }
        }
    }
}