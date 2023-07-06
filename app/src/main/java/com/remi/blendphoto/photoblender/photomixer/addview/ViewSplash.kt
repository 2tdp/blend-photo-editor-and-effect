package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom
import com.remi.blendphoto.photoblender.photomixer.R

@SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
class ViewSplash(context: Context) : RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    var process: ProgressBar

    init {
        w = resources.displayMetrics.widthPixels / 100F

        val ivBackground = ImageView(context).apply {
            setBackgroundColor(Color.WHITE)
            scaleType = ImageView.ScaleType.FIT_XY
        }
        addView(ivBackground, -1, -1)


        val ll = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
        }
        val iv = ImageView(context).apply { setImageResource(R.drawable.im_logo) }
        ll.addView(iv, LayoutParams((40.74f * w).toInt(), (40.74f * w).toInt()))
        val tv = TextView(context).apply {
            id = 1221
            textCustom(
                resources.getString(R.string.app_name),
                ContextCompat.getColor(context, R.color.black_main),
                5.925f * w,
                "alegreya_regular",
                context
            )
        }
        ll.addView(tv, LayoutParams(-2, -2).apply { topMargin = (8.518f * w).toInt() })
        addView(ll, LayoutParams(-1, -2).apply { addRule(CENTER_IN_PARENT, TRUE) })

        process = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        process.progressDrawable = resources.getDrawable(R.drawable.custom_progress, null)
        val paramsProgress = LayoutParams(-1, (1.4f * w).toInt())
        paramsProgress.addRule(ALIGN_PARENT_BOTTOM, TRUE)
        paramsProgress.addRule(CENTER_HORIZONTAL, TRUE)
        paramsProgress.setMargins(
            (11.667f * w).toInt(),
            0,
            (11.667f * w).toInt(),
            (33.981f * w).toInt()
        )
        addView(process, paramsProgress)
    }
}