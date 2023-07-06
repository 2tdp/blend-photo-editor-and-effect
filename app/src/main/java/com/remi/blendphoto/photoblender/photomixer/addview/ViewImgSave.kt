package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.remi.blendphoto.photoblender.photomixer.R

@SuppressLint("ResourceType")
class ViewImgSave(context: Context): RelativeLayout(context) {

    companion object{
        var w = 0F
    }

    val viewToolbar: ViewToolbar
    val rcvSave : RecyclerView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main))
        isClickable = true
        isFocusable = true

        viewToolbar = ViewToolbar(context).apply {
            id = 1221
            tvTitle.text = resources.getString(R.string.your_blend_photos)
        }
        addView(viewToolbar, LayoutParams(-1, (23f * w).toInt()))

        rcvSave = RecyclerView(context).apply { isVerticalScrollBarEnabled = false }
        addView(rcvSave, LayoutParams(-1, -1).apply {
            addRule(BELOW, viewToolbar.id)
        })
    }
}