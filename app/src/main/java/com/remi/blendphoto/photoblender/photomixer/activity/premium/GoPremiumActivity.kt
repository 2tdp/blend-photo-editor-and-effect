package com.remi.blendphoto.photoblender.photomixer.activity.premium

import android.os.Bundle
import com.remi.blendphoto.photoblender.photomixer.activity.base.BaseActivity
import com.remi.blendphoto.photoblender.photomixer.addview.premium.ViewGoPremium

class GoPremiumActivity: BaseActivity() {

    lateinit var viewGoPremium: ViewGoPremium

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewGoPremium = ViewGoPremium(this@GoPremiumActivity)
        setContentView(viewGoPremium)

        viewGoPremium.cardExperience.setOnClickListener { viewGoPremium.chooseOption(0) }
        viewGoPremium.cardStandard.setOnClickListener { viewGoPremium.chooseOption(1) }
        viewGoPremium.cardSavings.setOnClickListener { viewGoPremium.chooseOption(2) }
    }
}