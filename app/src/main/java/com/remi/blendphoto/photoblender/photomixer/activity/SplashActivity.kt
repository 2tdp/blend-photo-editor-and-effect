package com.remi.blendphoto.photoblender.photomixer.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.remi.blendphoto.photoblender.photomixer.activity.base.BaseActivity
import com.remi.blendphoto.photoblender.photomixer.addview.ViewSplash

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var viewSplash: ViewSplash
    private var progressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewSplash = ViewSplash(this@SplashActivity)
        setContentView(viewSplash)

        Thread {
            while (progressStatus < 100) {
                progressStatus += 1
                Handler(Looper.getMainLooper()).post { viewSplash.process.progress = progressStatus }
                try {
                    Thread.sleep(25)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }.start()
    }
}