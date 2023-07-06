package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.remi.blendphoto.photoblender.photomixer.R

@SuppressLint("ResourceType")
class ViewPickPicture(context: Context) : RelativeLayout(context) {

    companion object {
        var w = 0F
    }

    val viewToolbar: ViewToolbar
    val rcvBucket: RecyclerView
    val rcvPicture: RecyclerView
    val viewLoading: LottieAnimationView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundColor(Color.WHITE)
        isClickable = true
        isFocusable = true

        viewToolbar = ViewToolbar(context).apply {
            id = 1221
            tvTitle.text = resources.getString(R.string.my_gallery)
        }
        addView(viewToolbar, LayoutParams(-1, (23f * w).toInt()))

        val paramsRcv = LayoutParams(-1, -1).apply {
            setMargins(
                (2.22f * w).toInt(),
                (2.22f * w).toInt(),
                (2.22f * w).toInt(),
                (2.22f * w).toInt()
            )
            addRule(BELOW, viewToolbar.id)
        }

        rcvBucket = RecyclerView(context)
        addView(rcvBucket, paramsRcv)

        rcvPicture = RecyclerView(context)
        addView(rcvPicture, paramsRcv)

        viewLoading = LottieAnimationView(context).apply {
            setAnimation(R.raw.iv_loading)
            repeatCount = LottieDrawable.INFINITE
        }
        viewLoading.playAnimation()
        addView(viewLoading, LayoutParams(-1, (55.55f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
            leftMargin = (5.556f * w).toInt()
            rightMargin = (5.556f * w).toInt()
        })
    }
}