package com.remi.blendphoto.photoblender.photomixer.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.widget.TextView

object UtilsView {

    fun TextView.textCustom(content: String, color: Int, textSize: Float, font: String, context: Context) {
        text = content
        setTextColor(color)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        typeface = Utils.getTypeFace(font.substring(0, 8), "$font.ttf", context)
    }

    fun TextView.textBackground(colorArr: IntArray, border: Int, stroke: Int, colorStroke: Int) {
        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = border.toFloat()
            if (stroke != -1) setStroke(stroke, colorStroke)

            if (colorArr.size >= 2) {
                colors = colorArr
                gradientType = GradientDrawable.LINEAR_GRADIENT
            } else setColor(colorArr[0])
        }
    }
}