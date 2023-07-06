package com.remi.blendphoto.photoblender.photomixer.data

import android.content.Context
import androidx.core.net.ParseException
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.remi.blendphoto.photoblender.photomixer.utils.Constant
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object DataSave {

    fun getImgSave(context: Context, nameFolder: String): ArrayList<PicModel> {
        val lstImgSave = ArrayList<PicModel>()

        val filesApp = getTotalFile(context, nameFolder)
        if (filesApp != null && filesApp.isNotEmpty()) {
            var date = 0L
            for (f in filesApp) {
                if (!f.canRead()) continue

                val str = f.name.replace("imgSave", "").replace(".png", "")
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                try {
                    val time = format.parse(str)?.time
                    if (time != null) {
                        if (time > date) lstImgSave.add(0, PicModel(f.name, Constant.FROM_SAVE, 0F, f.path, false))
                        else lstImgSave.add(PicModel(f.name, Constant.FROM_SAVE, 0F, f.path, false))
                        date = time
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

            }
        }

        return lstImgSave
    }

    private fun getTotalFile(context: Context, nameFolder: String): Array<File>? {
        val directory = File(Utils.getStore(context) + "/" + nameFolder)
        return directory.listFiles()
    }
}