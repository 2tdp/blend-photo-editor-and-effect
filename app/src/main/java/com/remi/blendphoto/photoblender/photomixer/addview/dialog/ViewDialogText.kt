package com.remi.blendphoto.photoblender.photomixer.addview.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom

@SuppressLint("ResourceType")
class ViewDialogText(context: Context): RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val ivExit: ImageView

    val tvTitle: TextView
    val tvContext: TextView

    val tvNo: TextView
    val tvYes: TextView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundResource(R.drawable.boder_dialog)

        ivExit = ImageView(context).apply { setImageResource(R.drawable.ic_exit) }
        addView(ivExit, LayoutParams((4.44f * w).toInt(), (4.44f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            topMargin = (2.59f * w).toInt()
            rightMargin = (2.59f * w).toInt()
        })

        tvTitle = TextView(context).apply {
            id = 1221
            textCustom(
                "",
                ContextCompat.getColor(context, R.color.black),
                4.44f * w,
                "alegreya_medium",
                context
            )
        }
        addView(tvTitle, LayoutParams(-2, -2).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            topMargin = (6.29f * w).toInt()
        })

        tvContext = TextView(context).apply {
            textCustom(
                "",
                ContextCompat.getColor(context, R.color.black),
                3.7f * w,
                "alegreya_regular",
                context
            )
        }
        addView(tvContext, LayoutParams(-2, -2).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            addRule(BELOW, tvTitle.id)
            topMargin = (3.4f * w).toInt()
        })

        val ll = LinearLayout(context).apply {
            id = 1222
            orientation = LinearLayout.HORIZONTAL
        }
        tvNo = TextView(context).apply {
            textCustom(
                "",
                ContextCompat.getColor(context, R.color.red),
                3.7f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
        }
        ll.addView(tvNo, LinearLayout.LayoutParams(-1, -1, 1F))

        val v = View(context).apply { setBackgroundColor(Color.parseColor("#D6D6D6")) }
        ll.addView(v, (0.34f * w).toInt(), -1)

        tvYes = TextView(context).apply {
            textCustom(
                "",
                ContextCompat.getColor(context, R.color.black),
                3.7f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
        }
        ll.addView(tvYes, LinearLayout.LayoutParams(-1, -1, 1F))

        addView(ll, LayoutParams(-1, (11.67f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
        })

        val v2 = View(context).apply { setBackgroundColor(Color.parseColor("#D6D6D6")) }
        addView(v2, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(ABOVE, ll.id)
        })
    }
}