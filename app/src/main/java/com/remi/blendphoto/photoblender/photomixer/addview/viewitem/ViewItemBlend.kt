package com.remi.blendphoto.photoblender.photomixer.addview.viewitem

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom

class ViewItemBlend(context: Context) : RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val tv: TextView
    val iv: ImageView
    val vLine : View

    init {
        w = resources.displayMetrics.widthPixels / 100F

        tv = TextView(context).apply {
            textCustom(
                "",
                ContextCompat.getColor(context, R.color.black_main),
                4.44f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
        }
        addView(tv, -1, -1)

        iv = ImageView(context).apply { setImageResource(R.drawable.ic_tick_blend) }
        addView(iv, LayoutParams((4.44f * w).toInt(), (4.44f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            addRule(CENTER_VERTICAL, TRUE)
            rightMargin = (2.96f * w).toInt()
        })

        vLine = View(context).apply { setBackgroundColor(Color.parseColor("#DDDDDD")) }
        addView(vLine, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
        })
    }
}