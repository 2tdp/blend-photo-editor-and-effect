package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.Constant
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom

@SuppressLint("ResourceType")
class ViewPreImg(context: Context): RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val viewToolbar: ViewToolbar

    val rl1: RelativeLayout
    val ivMess: ImageView
    val ivFb: ImageView
    val ivIg: ImageView
    val ivMore: ImageView

    val rl2: RelativeLayout
    val tvShare: TextView
    val tvDel: TextView

    val rlMain: RelativeLayout
    val ivMain: ImageView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main))
        isClickable = true
        isFocusable = true

        viewToolbar = ViewToolbar(context).apply {
            id = 1221
            tvTitle.text = resources.getString(R.string.preview)
        }
        addView(viewToolbar, LayoutParams(-1, (23f * w).toInt()))

        rl1 = RelativeLayout(context).apply { id = 1224 }
        val ll = LinearLayout(context).apply {
            id = 1222
            orientation = LinearLayout.HORIZONTAL
        }
        ivMess = ImageView(context).apply { setImageResource(R.drawable.ic_share_mess) }
        ll.addView(ivMess, LinearLayout.LayoutParams((11.11f * w).toInt(), (11.11f * w).toInt(), 1F))
        ivFb = ImageView(context).apply { setImageResource(R.drawable.ic_share_fb) }
        ll.addView(ivFb, LinearLayout.LayoutParams((11.11f * w).toInt(), (11.11f * w).toInt(), 1F))
        ivIg = ImageView(context).apply { setImageResource(R.drawable.ic_share_ig) }
        ll.addView(ivIg, LinearLayout.LayoutParams((11.11f * w).toInt(), (11.11f * w).toInt(), 1F))
        ivMore = ImageView(context).apply { setImageResource(R.drawable.ic_share_more) }
        ll.addView(ivMore, LinearLayout.LayoutParams((11.11f * w).toInt(), (11.11f * w).toInt(), 1F))
        rl1.addView(ll, LayoutParams(-1, (11.11f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            leftMargin = (16.667f * w).toInt()
            rightMargin = (16.667f * w).toInt()
            bottomMargin = (31.94f * w).toInt()
        })

        val tv = TextView(context).apply {
            id = 1223
            textCustom(
                resources.getString(R.string.share_now),
                ContextCompat.getColor(context, R.color.black_main),
                4.44f * w,
                "alegreya_regular",
                context
            )
        }
        rl1.addView(tv, LayoutParams(-2, -2).apply {
            addRule(ABOVE, ll.id)
            addRule(CENTER_HORIZONTAL, TRUE)
            bottomMargin = (4.44f * w).toInt()
        })

        val v = View(context).apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.black_main))
        }
        rl1.addView(v, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(ABOVE, tv.id)
            leftMargin = (23.889f * w).toInt()
            rightMargin = (23.889f * w).toInt()
            bottomMargin = (4.44f * w).toInt()
        })

        addView(rl1, LayoutParams(-1, (57.96f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
        })

        rl2 = RelativeLayout(context)
        val cardDel = CardView(context).apply {
            id = 1444
            preventCornerOverlap = false
            radius = 2.5f * w
        }
        tvDel = TextView(context).apply {
            textCustom(
                resources.getString(R.string.del),
                ContextCompat.getColor(context, R.color.red),
                4.07f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
        }
        cardDel.addView(tvDel, -1, -1)
        rl2.addView(cardDel, LayoutParams(-1, (10.74f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            leftMargin = (11.11f * w).toInt()
            rightMargin = (11.11f * w).toInt()
            bottomMargin = (8.51f * w).toInt()
        })
        val cardShare = CardView(context).apply {
            preventCornerOverlap = false
            radius = 2.5f * w
        }
        tvShare = TextView(context).apply {
            textCustom(
                resources.getString(R.string.share),
                ContextCompat.getColor(context, R.color.black),
                4.07f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
        }
        cardShare.addView(tvShare, -1, -1)
        rl2.addView(cardShare, LayoutParams(-1, (10.74f * w).toInt()).apply {
            addRule(ABOVE, cardDel.id)
            leftMargin = (11.11f * w).toInt()
            rightMargin = (11.11f * w).toInt()
            bottomMargin = (4.44f * w).toInt()
        })
        addView(rl2, LayoutParams(-1, (57.96f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
        })

        rlMain = RelativeLayout(context)
        ivMain = ImageView(context).apply { setImageResource(R.drawable.im_place_holder) }

        rlMain.addView(ivMain, LayoutParams(-1, (109.25f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })

        addView(rlMain, LayoutParams(-1, -1).apply {
            addRule(BELOW, viewToolbar.id)
            addRule(ABOVE, rl1.id)
        })
    }

    fun changeType(type: String) {
        when(type) {
            Constant.FROM_FILTER -> {
                if (rl2.isVisible) rl2.visibility = GONE

                if (!rl1.isVisible) rl1.visibility = VISIBLE
            }
            Constant.FROM_SAVE -> {
                if (!rl2.isVisible) rl2.visibility = VISIBLE

                if (rl1.isVisible) rl1.visibility = INVISIBLE
            }
        }
    }
}