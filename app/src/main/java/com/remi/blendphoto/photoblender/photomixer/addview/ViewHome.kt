package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.viewcustom.CustomBeat
import com.remi.blendphoto.photoblender.photomixer.viewcustom.ViewAnim

@SuppressLint("ResourceType")
class ViewHome(context: Context) : RelativeLayout(context) {

    companion object{
        var w = 0F
    }

    val viewPager: ViewPager2
    val ivSetting: ImageView
    val ivGallery: ImageView
    val ivCamera: ImageView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main))

        viewPager = ViewPager2(context)
        addView(viewPager, -1, (152.98f * w).toInt())

        ivSetting = ImageView(context).apply { setImageResource(R.drawable.ic_setting) }
        addView(ivSetting, LayoutParams((7.407f * w).toInt(), (7.407f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            topMargin = (16.11f * w).toInt()
            rightMargin = (4.44f * w).toInt()
        })

        val ll = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL}
        ivGallery = ImageView(context).apply {
            setImageResource(R.drawable.im_choose_gallery)
        }
        ll.addView(ivGallery, LinearLayout.LayoutParams((36.48f * w).toInt(), -1, 1F))
        ivCamera = ImageView(context).apply {
            setImageResource(R.drawable.im_choose_camera)
        }
        ll.addView(ivCamera, LinearLayout.LayoutParams((36.48f * w).toInt(), -1, 1F))
        addView(ll, LayoutParams(-1, (48.33f * w).toInt()).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            leftMargin = (11.11f * w).toInt()
            rightMargin = (11.11f * w).toInt()
            bottomMargin = (22.22f * w).toInt()
        })
    }
}