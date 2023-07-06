package com.remi.blendphoto.photoblender.photomixer.activity.premium

import android.os.Bundle
import com.remi.blendphoto.photoblender.photomixer.activity.base.BaseActivity
import com.remi.blendphoto.photoblender.photomixer.addview.premium.ViewPremium

class PremiumActivity : BaseActivity() {

    lateinit var viewPremium: ViewPremium

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPremium = ViewPremium(this@PremiumActivity)
        setContentView(viewPremium)

        viewPremium.ivExit.setOnClickListener { finish() }
        viewPremium.tvContinue.setOnClickListener {
            setIntent(GoPremiumActivity::class.java.name, false)
        }
    }
}