package com.remi.blendphoto.photoblender.photomixer.viewcustom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.MotionEvent
import android.view.View
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsBitmap
import com.remi.blendphoto.photoblender.photomixer.viewcustom.MatrixGestureDetector.OnMatrixChangeListener

class CustomViewBlend(context: Context) : View(context), OnMatrixChangeListener {

    companion object {
        var w = 0F
    }

    private var bitmap: Bitmap? = null
    private var bitmapBlend: Bitmap? = null
    private var bitmapErase: Bitmap? = null
    private var isFirstBitmap = true
    var isMove = true
    var isErase = false


    private var paintBitmap: Paint
    private var paint: Paint
    private val clip = RectF()
    private val rect = Rect()
    private val rectErase = Rect()
    private val mMatrix = Matrix()
    private var maskMatrix = Matrix()
    private var detector = MatrixGestureDetector(maskMatrix, this)

    init {
        w = resources.displayMetrics.widthPixels / 100F

        paintBitmap = Paint(Paint.FILTER_BITMAP_FLAG)
        paint = Paint(Paint.FILTER_BITMAP_FLAG)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let {
            canvas.drawBitmap(it, null, rect, paint)
        }

        bitmapBlend?.let {
            if (isFirstBitmap) {
                isFirstBitmap = false
                maskMatrix.setScale(
                    width.toFloat() / it.width + 0.15f,
                    height.toFloat() / it.height + 0.15f
                )
                detector.mMatrix = maskMatrix
            }
            canvas.drawBitmap(it, maskMatrix, paintBitmap)
        }

        bitmapErase?.let {
            if (isErase) canvas.drawBitmap(it, null, rectErase, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        bitmapBlend?.let { detector.onTouchEvent(event, isMove) }

        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        bitmapBlend?.let {
            val src = RectF(0F, 0F, (it.width * 2).toFloat(), it.height.toFloat())
            val dst = RectF(0F, 0F, w.toFloat(), h.toFloat())
            mMatrix.apply {
                setRectToRect(src, dst, Matrix.ScaleToFit.CENTER)
                mapRect(dst, src)
            }
            maskMatrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER)
            clip[0f, 0f, width.toFloat()] = height.toFloat()
            maskMatrix.mapRect(clip)
        }
    }

    override fun onChange(matrix: Matrix?) {
        clip[0f, 0f, width.toFloat()] = height.toFloat()
        maskMatrix.mapRect(clip)

        invalidate()
    }

    fun setBitmap(bm: Bitmap) {
        bitmap = bm
        rect.set(0, 0, bm.width, bm.height)

        invalidate()
    }

    fun setBitmapBlend(bm: Bitmap, resetDraw: Boolean) {
        bitmapBlend = bm
        isFirstBitmap = resetDraw

        invalidate()
    }

    fun getBitmap(): Bitmap? {
        bitmapBlend?.let { bmBlend ->
            bitmap?.let { bm ->
                val saveBitmap = Bitmap.createBitmap(bm.width, bm.height, Bitmap.Config.ARGB_8888)
                val cv = Canvas(saveBitmap)

                cv.drawBitmap(bm, null, rect, paint)
                cv.drawBitmap(bmBlend, detector.mMatrix, paintBitmap)

                bitmapErase?.let { bmErase ->
                    cv.drawBitmap(bmErase, null, rectErase, paint)
                }

                return saveBitmap
            }
        }
        return null
    }

    fun setTypeBlend(mode: Any?) {
        mode?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                paintBitmap.blendMode = it as BlendMode
            else paintBitmap.xfermode = PorterDuffXfermode(it as PorterDuff.Mode)

            invalidate()
        }
    }

    fun flipErase(xFlip: Boolean, yFlip: Boolean) {
        isErase = true
        if (bitmapErase != null)
            bitmapErase = UtilsBitmap.createFlippedBitmap(bitmapErase!!, xFlip, yFlip)

        if (bitmap != null)
            bitmap = UtilsBitmap.createFlippedBitmap(bitmap!!, xFlip, yFlip)

        invalidate()
    }

    fun flipX() {
        detector.mMatrix.postScale(-1F, 1F, bitmapBlend!!.width / 2F, bitmapBlend!!.height / 2F)

        invalidate()
    }

    fun flipY() {
        detector.mMatrix.postScale(1F, -1F, bitmapBlend!!.width / 2F, bitmapBlend!!.height / 2F)

        invalidate()
    }

    fun setAlphaBlend(alpha: Int) {
        paintBitmap.alpha = alpha

        invalidate()
    }

    fun getAlphaPaintBlend(): Int {
        return paintBitmap.alpha
    }

    fun setBitmapErase(bm: Bitmap) {
        bitmapErase = bm
        rectErase.set(0, 0, bm.width, bm.height)
        isErase = true

        invalidate()
    }

    fun setAlpha(alpha: Int) {
        paint.alpha = alpha

        invalidate()
    }

    fun getAlphaPaint(): Int {
        return paintBitmap.alpha
    }
}