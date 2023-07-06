package com.remi.textonphoto.writeonphoto.addtext.model

import android.graphics.Bitmap
import java.io.Serializable

class FilterModel(
    var bitmap: Bitmap?,
    var nameFilter: String,
    var parameterFilter: String,
    var isCheck: Boolean
) : Serializable