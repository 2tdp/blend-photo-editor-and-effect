package com.remi.blendphoto.photoblender.photomixer.viewcustom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CustomBeat(context: Context) : View(context) {

    private var paint: Paint? = null
    private var count = 0f
    private var plus = 0f
    private var radius = 0f
    private var radiuss = 0f
    private var radiusss = 0f
    private var radiussss = 0f

    var stroke = 1f
    private val paintSub = Paint(Paint.ANTI_ALIAS_FLAG)
    var r = 0
    private var sizeInner = 0

    var isCreate = true

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = Color.parseColor("#FECE36")
        paint!!.alpha = (9 * 255 / 100f).toInt()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isCreate) {
            isCreate = false
            count = width / 12f
            plus = 1f
            val f = width / 16f
            radius = count
            radiuss = count + f
            radiusss = count + 2 * f
            radiussss = count + 3 * f
            runBeat1()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint!!)
        canvas.drawCircle(width / 2f, height / 2f, radiuss, paint!!)
        canvas.drawCircle(width / 2f, height / 2f, radiusss, paint!!)
        canvas.drawCircle(width / 2f, height / 2f, radiussss, paint!!)

        r = width / 2
        paintSub.apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawCircle(width / 2f, height / 2f, r / 2f, paintSub);
        paintSub.apply {
            color = (Color.parseColor("#FECE36"))
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = width / 30f
            style = Paint.Style.STROKE
        }
        canvas.drawCircle(width / 2f, height / 2f, r / 2f, paintSub)
        sizeInner = r / 4
        canvas.drawLine(
            width / 2f - sizeInner,
            height / 2f,
            width / 2f + sizeInner,
            height / 2f,
            paintSub
        )
        canvas.drawLine(
            width / 2f,
            height / 2f - sizeInner,
            width / 2f,
            height / 2f + sizeInner,
            paintSub
        )
    }

    private fun runBeat1() {
        val valueAnimator = ValueAnimator.ofFloat(0f, 10f)
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.addUpdateListener {
            if (radius > width * 5 / 12f) radius = count
            radius += plus
            if (radiuss > width * 5 / 12f) radiuss = count
            radiuss += plus
            if (radiusss > width * 5 / 12f) radiusss = count
            radiusss += plus
            if (radiussss > width * 5 / 12f) radiussss = count
            radiussss += plus

            invalidate()
        }
        valueAnimator.start()
    }
}