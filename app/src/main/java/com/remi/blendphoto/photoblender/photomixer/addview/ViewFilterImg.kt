package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.remi.blendphoto.photoblender.photomixer.R

@SuppressLint("ResourceType")
class ViewFilterImg(context: Context): RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val viewToolbar: ViewToolbar

    val cardViewFilter : CardView
    val rcvFilter: RecyclerView

    val rlMain: RelativeLayout
    val ivMain: ImageView

    val viewLoading: LottieAnimationView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main))
        isClickable = true
        isFocusable = true

        viewToolbar = ViewToolbar(context).apply {
            id = 1221
            tvTitle.text = resources.getString(R.string.effect)
            tvRight.visibility = View.VISIBLE
        }
        addView(viewToolbar, LayoutParams(-1, (23f * w).toInt()))

        cardViewFilter = CardView(context).apply {
            id = 1222
            radius = 5.56f * w
            elevation = 2 * w
            preventCornerOverlap = false
            setCardBackgroundColor(Color.WHITE)
        }
        val rl = RelativeLayout(context)
        rcvFilter = RecyclerView(context).apply { isHorizontalFadingEdgeEnabled = false }
        rl.addView(rcvFilter, LayoutParams(-1, -1).apply {
            topMargin = (5.37f * w).toInt()
        })
        cardViewFilter.addView(rl, -1, -1)
        addView(cardViewFilter, LayoutParams(-1, (46.29f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            leftMargin = (4.44f * w).toInt()
            rightMargin = (4.44f * w).toInt()
            bottomMargin = -(6.08f * w).toInt()
        })

        rlMain = RelativeLayout(context).apply { id = 1997 }
        ivMain = ImageView(context).apply { setImageResource(R.drawable.im_place_holder) }

        rlMain.addView(ivMain, LayoutParams(-1, (109.25f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })

        addView(rlMain, LayoutParams(-1, -1).apply {
            addRule(BELOW, viewToolbar.id)
            addRule(ABOVE, cardViewFilter.id)
        })

        viewLoading = LottieAnimationView(context).apply {
            visibility = GONE
            setAnimation(R.raw.iv_loading)
            repeatCount = LottieDrawable.INFINITE
        }
        viewLoading.playAnimation()
        addView(viewLoading, LayoutParams(-1, (34.34f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
            leftMargin = (5.556f * w).toInt()
            rightMargin = (5.556f * w).toInt()
        })
    }
}