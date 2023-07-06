package com.remi.blendphoto.photoblender.photomixer.addview.viewitem

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom
import com.makeramen.roundedimageview.RoundedImageView

@SuppressLint("ResourceType")
class ViewItemBucket(context: Context) : RelativeLayout(context) {

    companion object{
        var w = 0F
    }

    var ivThumb: RoundedImageView
    var ivRight: ImageView
    var viewNamePicture: ViewNamePicture

    init {
        w = resources.displayMetrics.widthPixels / 100f
        isFocusable = true
        isClickable = true

        ivThumb = RoundedImageView(context).apply {
            id = 1332
            cornerRadius = 2.5f * w
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        addView(ivThumb, LayoutParams((20.556f * w).toInt(), -1))

        viewNamePicture = ViewNamePicture(context)
        val paramsViewName = LayoutParams(-2, -2).apply {
            setMargins((5.56f * w).toInt(), 0, 0, 0)
            addRule(CENTER_VERTICAL, TRUE)
            addRule(RIGHT_OF, ivThumb.id)
        }
        addView(viewNamePicture, paramsViewName)

        ivRight = ImageView(context).apply { setImageResource(R.drawable.ic_right) }
        val paramsRight = LayoutParams((5.556f * w).toInt(), (5.556f * w).toInt()).apply {
            addRule(CENTER_VERTICAL, TRUE)
            addRule(ALIGN_PARENT_END, TRUE)
        }
        addView(ivRight, paramsRight)
    }

    class ViewNamePicture(context: Context) : LinearLayout(context) {

        var tvName: TextView
        var tvQuantity: TextView

        init {
            orientation = VERTICAL
            tvName = TextView(context).apply {
                textCustom(
                    "",
                    ContextCompat.getColor(context, R.color.black_main),
                    3.889f * w,
                    "alegreya_bold", context
                )
            }

            addView(tvName, LayoutParams(-2, -2))
            tvQuantity = TextView(context).apply {
                textCustom(
                    "",
                    ContextCompat.getColor(context, R.color.gray_text_2),
                    3.33f * w,
                    "alegreya_regular",
                    context
                )
            }
            addView(tvQuantity, LayoutParams(-2, -2).apply {
                topMargin = (2.22f * w).toInt()
            })
        }
    }
}