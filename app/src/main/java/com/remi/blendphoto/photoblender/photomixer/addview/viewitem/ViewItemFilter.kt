package com.remi.blendphoto.photoblender.photomixer.addview.viewitem

import android.content.Context
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.makeramen.roundedimageview.RoundedImageView
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom

class ViewItemFilter(context: Context): RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val ivFilter: RoundedImageView
    val tv: TextView

    init {
        w = resources.displayMetrics.widthPixels / 100F

        ivFilter = RoundedImageView(context).apply {
            cornerRadius = 2.5f * w
            elevation = 2 * w
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        addView(ivFilter, LayoutParams((17.59f * w).toInt(), (20.92f * w).toInt()))

        tv = TextView(context).apply {
            textCustom(
                "",
                ContextCompat.getColor(context, R.color.black_main),
                3.7f * w,
                "alegreya_regular",
                context
            )
        }
        addView(tv, LayoutParams(-2, -2).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            addRule(CENTER_HORIZONTAL, TRUE)
        })
    }
}