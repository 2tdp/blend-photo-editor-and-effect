package com.remi.blendphoto.photoblender.photomixer.addview.premium

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom

@SuppressLint("ResourceType")
class ViewPremium(context: Context) : RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val ivExit: ImageView
    val tvContinue : TextView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundColor(Color.WHITE)

        ivExit = ImageView(context).apply {
            id = 1221
            setImageResource(R.drawable.ic_exit)
            setPadding((w).toInt())
        }
        addView(ivExit, LayoutParams((7.889f * w).toInt(), (7.889f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            rightMargin = (4.44f * w).toInt()
            topMargin = (16.11f * w).toInt()
        })

        val ivPremium = ImageView(context).apply {
            id = 1222
            setImageResource(R.drawable.ic_premium)
        }
        addView(ivPremium, LayoutParams((15.37f * w).toInt(), (15.37f * w).toInt()).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            addRule(BELOW, ivExit.id)
            topMargin = (9.07f * w).toInt()
        })

        val tv = TextView(context).apply {
            id = 1223
            textCustom(
                "Start Like A Pro",
                ContextCompat.getColor(context, R.color.black_main),
                8.14f * w,
                "alegreya_medium",
                context
            )
        }
        addView(tv, LayoutParams(-2, -2).apply {
            addRule(BELOW, ivPremium.id)
            addRule(CENTER_HORIZONTAL, TRUE)
            topMargin = (3.425f * w).toInt()
        })

        tvContinue = TextView(context).apply {
            id = 1224
            textCustom(
                "Continue",
                ContextCompat.getColor(context, R.color.black_main),
                5.92f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
            background = Utils.createBackground(
                intArrayOf(ContextCompat.getColor(context, R.color.color_main)),
                (2.5f * w).toInt(),
                -1, -1
            )
        }
        addView(tvContinue, LayoutParams(-1, (16.94f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            leftMargin = (4.44f * w).toInt()
            rightMargin = (4.44f * w).toInt()
            bottomMargin = (10.185f * w).toInt()
        })

        val iv = ImageView(context).apply { setImageResource(R.drawable.im_bg_premium) }
        addView(iv, LayoutParams(-1, (82.87f * w).toInt()).apply {
            addRule(ABOVE, tvContinue.id)
            addRule(BELOW, tv.id)
            leftMargin = (5f * w).toInt()
            rightMargin = (5f * w).toInt()
            topMargin = (15.278f * w).toInt()
            bottomMargin = (22.59f * w).toInt()
        })
    }
}