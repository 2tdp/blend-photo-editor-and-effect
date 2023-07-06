package com.remi.blendphoto.photoblender.photomixer.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.remi.blendphoto.photoblender.photomixer.activity.base.BaseActivity
import com.remi.blendphoto.photoblender.photomixer.activity.premium.PremiumActivity
import com.remi.blendphoto.photoblender.photomixer.addview.ViewSettings
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.data.DataSave
import com.remi.blendphoto.photoblender.photomixer.fragment.ImgSaveFragment
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.remi.blendphoto.photoblender.photomixer.sharepref.DataLocalManager
import com.remi.blendphoto.photoblender.photomixer.utils.ActionUtils
import com.remi.blendphoto.photoblender.photomixer.utils.Constant
import com.remi.blendphoto.photoblender.photomixer.utils.Utils

class SettingsActivity : BaseActivity() {

    private lateinit var viewSettings: ViewSettings
    private var imgSaveFragment: ImgSaveFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewSettings = ViewSettings(this@SettingsActivity)
        setContentView(viewSettings)

        onBackPressedDispatcher.addCallback(this@SettingsActivity, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (imgSaveFragment != null) {
                    supportFragmentManager.popBackStack("ImgSaveFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    imgSaveFragment = null
                } else finish()
            }
        })

        evenClick()
    }

    override fun onResume() {
        super.onResume()
        imgSaveFragment?.resetData()
    }

    private fun evenClick() {
        viewSettings.viewToolbar.ivBack.setOnClickListener { finish() }

        viewSettings.viewUpgrade.setOnClickListener {
            setIntent(PremiumActivity::class.java.name, false)
        }
        viewSettings.viewYourBlend.setOnClickListener { goToSave() }
        viewSettings.viewRateApp.setOnClickListener { ActionUtils.rateApp(this@SettingsActivity) }
        viewSettings.viewFeedBack.setOnClickListener { ActionUtils.sendFeedback(this@SettingsActivity) }
        viewSettings.viewMoreApp.setOnClickListener { ActionUtils.openOtherApps(this@SettingsActivity) }
        viewSettings.viewShareApp.setOnClickListener { ActionUtils.shareApp(this@SettingsActivity) }
        viewSettings.viewPP.setOnClickListener { ActionUtils.openPolicy(this@SettingsActivity) }
        viewSettings.viewFb.setOnClickListener { ActionUtils.openFacebook(this@SettingsActivity) }
        viewSettings.viewInsta.setOnClickListener { ActionUtils.openInstagram(this@SettingsActivity) }
    }

    private fun goToSave() {
        imgSaveFragment = ImgSaveFragment.newInstance(object : ICallBackItem {
            override fun callBack(ob: Any, position: Int) {
                DataLocalManager.setPicture(ob as PicModel, Constant.IMG_PREVIEW)
                Utils.setIntent(this@SettingsActivity, PreImgActivity::class.java.name)
            }
        })
        replaceFragment(supportFragmentManager, imgSaveFragment!!, true, true, true)
    }
}