package com.remi.blendphoto.photoblender.photomixer

import android.app.Application
import com.remi.blendphoto.photoblender.photomixer.sharepref.DataLocalManager

class MyApp : Application() {
    override fun onCreate() {
        DataLocalManager.init(applicationContext)
        super.onCreate()
    }
}