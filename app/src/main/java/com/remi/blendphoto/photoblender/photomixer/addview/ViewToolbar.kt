package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.RelativeLayout.*
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom

@SuppressLint("ResourceType")
class ViewToolbar(context: Context) : CardView(context) {

    companion object {
        var w = 0F
    }

    val ivBack: ImageView
    val tvTitle: TextView
    val ivRight: ImageView
    val tvRight: TextView
    val ll: LinearLayout
    val ivFlipX: ImageView
    val ivFlipY: ImageView
    val ivErase: ImageView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        elevation = 2 * w
        preventCornerOverlap = false
        setCardBackgroundColor(Color.WHITE)

        val rl = RelativeLayout(context)

        ivBack = ImageView(context).apply {
            id = 1221
            setImageResource(R.drawable.ic_back)
        }
        rl.addView(ivBack, RelativeLayout.LayoutParams((7.407f * w).toInt(), (7.407f * w).toInt()).apply {
            leftMargin = (4.44f * w).toInt()
            addRule(CENTER_VERTICAL, TRUE)
        })

        ivRight = ImageView(context).apply {
            id = 1222
            visibility = GONE
            setImageResource(R.drawable.ic_next)
        }
        rl.addView(ivRight, RelativeLayout.LayoutParams((7.407f * w).toInt(), (7.407f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            rightMargin = (4.44f * w).toInt()
            addRule(CENTER_VERTICAL, TRUE)
        })

        tvRight = TextView(context).apply {
            textCustom(
                resources.getString(R.string.save),
                ContextCompat.getColor(context, R.color.color_main),
                5.18f * w,
                "alegreya_bold",
                context
            )
            visibility = GONE
        }
        rl.addView(tvRight, RelativeLayout.LayoutParams(-2, -2).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            rightMargin = (4.44f * w).toInt()
            addRule(CENTER_VERTICAL, TRUE)
        })

        tvTitle = TextView(context).apply {
            textCustom(
                "",
                ContextCompat.getColor(context, R.color.black),
                5.556f * w,
                "alegreya_regular",
                context
            )
        }
        rl.addView(tvTitle, RelativeLayout.LayoutParams(-2, -2).apply { addRule(CENTER_IN_PARENT, TRUE) })

        ll = LinearLayout(context).apply {
            visibility = GONE
            orientation = LinearLayout.HORIZONTAL
            gravity = CENTER_IN_PARENT
        }
        ivFlipX = ImageView(context).apply { setImageResource(R.drawable.ic_flip_x) }
        ll.addView(ivFlipX, LinearLayout.LayoutParams(-1, -1, 1F))

        ivFlipY = ImageView(context).apply { setImageResource(R.drawable.ic_flip_y) }
        ll.addView(ivFlipY, LinearLayout.LayoutParams(-1, -1, 1F))

        ivErase = ImageView(context).apply { setImageResource(R.drawable.ic_erase) }
        ll.addView(ivErase, LinearLayout.LayoutParams(-1, -1, 1F))

        rl.addView(ll, RelativeLayout.LayoutParams(-1, (6.667f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
            addRule(RIGHT_OF, ivBack.id)
            addRule(LEFT_OF, ivRight.id)
            leftMargin = (2.22f * w).toInt()
            rightMargin = (2.22f * w).toInt()
        })

        addView(rl, RelativeLayout.LayoutParams(-1, (11.85f * w).toInt()).apply {
            topMargin = (11.667f * w).toInt()
        })
    }
}