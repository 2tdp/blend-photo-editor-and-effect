package com.remi.blendphoto.photoblender.photomixer.addview

import android.content.Context
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom
import com.remi.blendphoto.photoblender.photomixer.viewcustom.CustomSeekbar

class ViewSeekbar(context: Context): RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val tvSeekBar: TextView
    val seekbar: CustomSeekbar
    val tvProgress: TextView

    init {
        w = resources.displayMetrics.widthPixels / 100F

        tvSeekBar = TextView(context).apply {
            textCustom(
                resources.getString(R.string.opacity),
                ContextCompat.getColor(context, R.color.black),
                3.7f * w,
                "alegreya_regular",
                context
            )
        }
        addView(tvSeekBar, -2, -2)

        tvProgress = TextView(context).apply {
            textCustom(
                "100",
                ContextCompat.getColor(context, R.color.black),
                3.7f * w,
                "alegreya_regular",
                context
            )
        }
        addView(tvProgress, LayoutParams(-2, -2).apply {
            addRule(ALIGN_PARENT_END, TRUE)
        })

        seekbar = CustomSeekbar(context).apply { setMax(100) }
        addView(seekbar, LayoutParams(-1, (5.9f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
        })
    }
}