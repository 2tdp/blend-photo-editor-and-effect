package com.remi.blendphoto.photoblender.photomixer.data

import android.graphics.Bitmap
import com.remi.textonphoto.writeonphoto.addtext.model.FilterModel
import org.wysaid.nativePort.CGENativeLibrary
import java.util.ArrayList

object DataFilter {

    fun getDataFilter(bitmap: Bitmap): ArrayList<FilterModel> {
        val lstFilter = ArrayList<FilterModel>()
        var bm: Bitmap
        for (s in FILTER_NAMES) {
            bm = CGENativeLibrary.cgeFilterImage_MultipleEffects(bitmap, "@adjust lut $s", 0.8f)
            lstFilter.add(FilterModel(bm, "Effect ${FILTER_NAMES.indexOf(s) + 1}", s, false))
        }

        lstFilter.add(0, FilterModel(bitmap, "Normal", "", false))
        return lstFilter
    }

    private val FILTER_NAMES = arrayOf(
        "Autumn_1.png",
        "Autumn_2.png",
        "Autumn_3.png",
        "B&W.png",
        "edgy_amber.png",
        "filmstock.png",
        "late_sunset.png",
        "soft_warming.png",
        "camera_filter.png",
        "camera_filter_2.png",
        "camera_filter_3.png",
        "color_1.png",
        "color_2.png",
        "color_3.png",
        "color_4.png",
        "color_5.png",
        "fall.png",
        "film_1.png",
        "film_2.png",
        "film_3.png",
        "film_4.png",
        "light_1.png",
        "nature_1.png",
        "nature_2.png",
        "nature_3.png",
        "nature_4.png",
        "night_1.png",
        "night_2.png",
        "night_3.png",
        "night_4.png",
        "night_5.png",
        "travel_1.png",
        "travel_2.png",
        "travel_3.png",
        "warm_1.png",
        "warm_2.png"
    )
}