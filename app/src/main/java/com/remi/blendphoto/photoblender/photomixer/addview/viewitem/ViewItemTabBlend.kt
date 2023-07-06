package com.remi.blendphoto.photoblender.photomixer.addview.viewitem

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom


@SuppressLint("ResourceType")
class ViewItemTabBlend(context: Context): RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val iv: ImageView
    val tv: TextView
    val v: View

    init {
        w = resources.displayMetrics.widthPixels / 100F

        v = View(context).apply {
            id = 1998
            background = Utils.createBackground(
                intArrayOf(ContextCompat.getColor(context, R.color.color_main)),
                (0.34f * w).toInt(),
                -1, -1
            )
        }
        addView(v, LayoutParams((4.44f * w).toInt(), (0.74f * w).toInt()).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
        })

        val cardView = CardView(context).apply {
            id = 1221
            elevation = 0.84f * w
            radius = 1.5f * w
        }
        iv = ImageView(context).apply {
            setImageResource(R.drawable.im_place_holder)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        cardView.addView(iv, -1, -1)
        addView(cardView, LayoutParams((13.24f * w).toInt(), (10.18f * w).toInt()).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            addRule(BELOW, v.id)
            topMargin = (1.48f * w).toInt()
        })

        tv = TextView(context).apply {
            textCustom(
                "",
                ContextCompat.getColor(context, R.color.black),
                3.7f * w,
                "alegreya_regular",
                context
            )
        }
        addView(tv, LayoutParams(-2, -2).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            addRule(BELOW, cardView.id)
            topMargin = (1.11f * w).toInt()
        })
    }
}