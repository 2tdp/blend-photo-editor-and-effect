package com.remi.blendphoto.photoblender.photomixer.data

import android.content.Context
import android.graphics.BlendMode
import android.graphics.PorterDuff
import android.os.Build
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.model.BlendModel
import com.remi.blendphoto.photoblender.photomixer.model.ImgBlendModel
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.remi.blendphoto.photoblender.photomixer.sharepref.DataLocalManager
import com.remi.blendphoto.photoblender.photomixer.utils.Constant
import java.io.IOException
import kotlin.random.Random

object DataBlend {

    fun getDataBlend(): ArrayList<BlendModel> {

        val lstBlend = ArrayList<BlendModel>()
        var name = ""
        for (s in BLEND_NAMES) {
            if (s == "blend_darken.png") {
                name = "Darken"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    lstBlend.add(BlendModel(name, BlendMode.DARKEN, true))
                else lstBlend.add(BlendModel(name, PorterDuff.Mode.DARKEN, true))
            }
            if (s == "blend_multiply.png") {
                name = "Multiply"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    lstBlend.add(BlendModel(name, BlendMode.MULTIPLY, false))
                else lstBlend.add(BlendModel(name, PorterDuff.Mode.MULTIPLY, false))
            }
            if (s == "blend_color_burn.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name = "Color Burn"
                lstBlend.add(BlendModel(name, BlendMode.COLOR_BURN, false))
            }
            if (s == "blend_lighten.png") {
                name = "Lighten"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    lstBlend.add(BlendModel(name, BlendMode.LIGHTEN, false))
                else lstBlend.add(BlendModel(name, PorterDuff.Mode.LIGHTEN, false))
            }
            if (s == "blend_screen.png") {
                name = "Screen"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    lstBlend.add(BlendModel(name, BlendMode.SCREEN, false))
                else lstBlend.add(BlendModel(name, PorterDuff.Mode.SCREEN, false))
            }
            if (s == "blend_color_doge.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name = "Color Doge"
                lstBlend.add(BlendModel(name, BlendMode.COLOR_DODGE, false))
            }
            if (s == "blend_overlay.png") {
                name = "Overlay"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    lstBlend.add(BlendModel(name, BlendMode.OVERLAY, false))
                else lstBlend.add(BlendModel(name, PorterDuff.Mode.OVERLAY, false))
            }
            if (s == "blend_soft_light.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name = "Soft Light"
                lstBlend.add(BlendModel(name, BlendMode.SOFT_LIGHT, false))
            }
            if (s == "blend_hard_light.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name = "Hard Light"
                lstBlend.add(BlendModel(name, BlendMode.HARD_LIGHT, false))
            }
            if (s == "blend_difference.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name = "Difference"
                lstBlend.add(BlendModel(name, BlendMode.DIFFERENCE, false))
            }
            if (s == "blend_exclusion.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                name = "Exclusion"
                lstBlend.add(BlendModel(name, BlendMode.EXCLUSION, false))
            }
            if (s == "blend_hue.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                name = "Hue"
                lstBlend.add(BlendModel(name, BlendMode.HUE, false))
            }
            if (s == "blend_saturation.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name = "Saturation"
                lstBlend.add(BlendModel(name, BlendMode.SATURATION, false))
            }
            if (s == "blend_color.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name = "Color"
                lstBlend.add(BlendModel(name, BlendMode.COLOR, false))
            }
            if (s == "blend_luminosity.png" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                name = "Luminosity"
                lstBlend.add(BlendModel(name, BlendMode.LUMINOSITY, false))
            }
        }
        return lstBlend
    }

    private val BLEND_NAMES = arrayOf(
        "blend_darken.png",
        "blend_multiply.png",
        "blend_color_burn.png",
        "blend_lighten.png",
        "blend_screen.png",
        "blend_color_doge.png",
        "blend_overlay.png",
        "blend_soft_light.png",
        "blend_hard_light.png",
        "blend_difference.png",
        "blend_exclusion.png",
        "blend_hue.png",
        "blend_saturation.png",
        "blend_color.png",
        "blend_luminosity.png",
    )


    fun getDataImageBlend(context: Context, name: String): ArrayList<ImgBlendModel> {
        val lstImgBlendModel = ArrayList<ImgBlendModel>()
        var isPremium = false
        var isAds = false
        try {
            val f = context.assets.list("blend/$name")
            if (!DataLocalManager.getCheck("boolean-premium-$name")) {
                createPremium(f!!, name)
                DataLocalManager.setCheck("boolean-premium-$name", true)
            }
            if (!DataLocalManager.getCheck("boolean-ads-$name")) {
                createAds(f!!, name)
                DataLocalManager.setCheck("boolean-ads-$name", true)
            }

            for (s in f!!) {
                for (i in DataLocalManager.getListInt("premium-$name")) {
                    if (f.indexOf(s) == i) {
                        isPremium = true
                        break
                    } else isPremium = false
                }
                for (i in DataLocalManager.getListInt("ads-$name")) {
                    if (f.indexOf(s) == i) {
                        isAds = true
                        break
                    } else isAds = false
                }
                lstImgBlendModel.add(ImgBlendModel(name, s, isPremium, isAds, false))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        lstImgBlendModel.add(0, ImgBlendModel("", "", false, false, false))
        return lstImgBlendModel
    }

    private fun createPremium(f: Array<String>, name: String) {
        val lstPremium = ArrayList<Int>()
        for (i in 0..f.size / 2)
            getListRandom("premium", f[i], Random, f.size, lstPremium, object : ICallBackItem {
                override fun callBack(ob: Any, position: Int) {
                    lstPremium.add(position)
                }
            })
        DataLocalManager.setListInt(lstPremium, "premium-$name")
    }

    private fun createAds(f: Array<String>, name: String) {
        val lstAds = ArrayList<Int>()
        for (i in 0..f.size / 3)
            getListRandom("ads", f[i], Random, f.size / 2, lstAds, object : ICallBackItem {
                override fun callBack(ob: Any, position: Int) {
                    lstAds.add(position)
                }
            })
        DataLocalManager.setListInt(lstAds, "ads-$name")
    }

    private fun getListRandom(type: String, name: String, random: Random, total: Int, lst: ArrayList<Int>, callBack: ICallBackItem) {
        var check = false
        var isP = false
        val index = random.nextInt(0, total)
        when(type) {
            "premium" -> {
                for (j in lst) {
                    if (index == j) {
                        isP = true
                        break
                    } else isP = false
                }

                if (isP) getListRandom("premium", name, random, total, lst, callBack)
                else callBack.callBack(index, index)
            }
            "ads" -> {
                val intArr = DataLocalManager.getListInt("premium-$name")
                for (j in lst) {
                    if (index == j) {
                        isP = true
                        break
                    } else isP = false
                }

                if (isP) getListRandom("ads", name, random, total, lst, callBack)
                else {
                    for (j in intArr) {
                        if (j == index) {
                            check = true
                            break
                        } else check = false
                    }
                    if (!check) callBack.callBack(index, index)
                }
            }
        }
    }
}