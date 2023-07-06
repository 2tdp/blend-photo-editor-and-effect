package com.remi.blendphoto.photoblender.photomixer.viewcustom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.addtext.customview.OnSeekbarResult

class CustomSeekbar(context: Context) : View(context) {

    companion object {
        var w = 0
    }

    private var paint: Paint
    private var paintProgress: Paint
    private var progress = 0
    private var max = 0
    private var sizeThumb = 0f
    private var sizeBg = 0f
    private var sizePos = 0f

    var onSeekbarResult: OnSeekbarResult? = null

    init {
        w = resources.displayMetrics.widthPixels
        sizeThumb = 5.92f * w / 100
        sizeBg = (w / 100).toFloat()
        sizePos = (w / 100).toFloat()

        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.FILL
        }
        paintProgress = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.apply {
            clearShadowLayer()
            color = ContextCompat.getColor(context, R.color.gray_light)
            strokeWidth = sizeBg
        }
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, paint)

        paintProgress.apply {
            color = ContextCompat.getColor(context, R.color.color_main)
            strokeWidth = sizePos
        }
        val p = (width - sizeThumb) * progress / max + sizeThumb / 2
        canvas.drawLine(0F, height / 2f, p, height / 2f, paintProgress)

        paint.apply {
            color = ContextCompat.getColor(context, R.color.color_main)
            setShadowLayer(sizeThumb / 8, 0f, 0f, Color.WHITE)
        }
        canvas.drawCircle(p, height / 2f, sizeThumb / 2, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> onSeekbarResult?.let { it.onDown(this) }
            MotionEvent.ACTION_MOVE -> {
                progress = ((x - sizeThumb / 2) * max / (width - sizeThumb)).toInt()

                if (progress < 0) progress = 0
                else if (progress > max) progress = max
                invalidate()

                onSeekbarResult?.let { it.onMove(this, progress) }
            }
            MotionEvent.ACTION_UP -> onSeekbarResult?.let { it.onUp(this, progress)
            }
        }
        return true
    }



    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    fun getProgress(): Int {
        return progress
    }

    fun setMax(max: Int) {
        this.max = max
        invalidate()
    }
}