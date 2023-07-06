package com.remi.blendphoto.photoblender.photomixer.addview.viewitem

import android.content.Context
import android.widget.ImageView
import android.widget.RelativeLayout
import com.makeramen.roundedimageview.RoundedImageView
import com.remi.blendphoto.photoblender.photomixer.R

class ViewItemChooseBlend(context: Context) : RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    var ivBlend: RoundedImageView
    var iv: ImageView
    val ivChoose: RoundedImageView

    init {
        w = resources.displayMetrics.widthPixels / 100F

        ivBlend = RoundedImageView(context).apply {
            cornerRadius = 2.5f * w
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        addView(ivBlend, -1, -1)

        iv = ImageView(context)
        addView(iv, -1, -1)

        ivChoose = RoundedImageView(context).apply {
            setImageResource(R.drawable.ic_choose_blend)
            cornerRadius = 2.5f * w
            scaleType = ImageView.ScaleType.CENTER_CROP
            visibility = GONE
        }
        addView(ivChoose, -1, -1)
    }
}